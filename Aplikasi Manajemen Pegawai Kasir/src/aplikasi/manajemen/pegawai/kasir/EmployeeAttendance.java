package aplikasi.manajemen.pegawai.kasir;

import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.*;
import java.util.HashSet;
import java.util.Set;

public class EmployeeAttendance extends JFrame implements ActionListener {

    private Choice cEmployeeId;
    private JComboBox<String> cWorkDay;
    private JComboBox<String> cAttendanceStatus;
    private JDateChooser dateChooser;
    private JButton submit, back;
    private Set<String> nonWorkingDays;

    public EmployeeAttendance() {
        setTitle("Employee Attendance");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel heading = new JLabel("Absensi Pegawai");
        heading.setBounds(130, 10, 200, 30);
        heading.setFont(new Font("SAN_SERIF", Font.BOLD, 20));
        add(heading);

        JLabel lblEmpId = new JLabel("Nomor Pegawai:");
        lblEmpId.setBounds(50, 50, 100, 30);
        add(lblEmpId);

        cEmployeeId = new Choice();
        cEmployeeId.setBounds(200, 50, 150, 30);
        add(cEmployeeId);

        try {
            Connection conn = getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT NomorPegawai FROM PegawaiKasir");
            while (rs.next()) {
                cEmployeeId.add(rs.getString("NomorPegawai"));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JLabel lblWorkDay = new JLabel("Hari Kerja:");
        lblWorkDay.setBounds(50, 90, 100, 30);
        add(lblWorkDay);

        String[] days = {"SENIN", "SELASA", "RABU", "KAMIS", "JUMAT", "SABTU", "MINGGU"};
        cWorkDay = new JComboBox<>(days);
        cWorkDay.setBounds(200, 90, 150, 30);
        add(cWorkDay);

        JLabel lblDate = new JLabel("Tanggal:");
        lblDate.setBounds(50, 130, 100, 30);
        add(lblDate);

        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        dateChooser.setBounds(200, 130, 150, 30);
        add(dateChooser);

        JLabel lblAttendanceStatus = new JLabel("Keterangan Absensi:");
        lblAttendanceStatus.setBounds(50, 170, 150, 30);
        add(lblAttendanceStatus);

        String[] attendanceStatuses = {"Hadir", "Sakit", "Izin", "Tidak Hadir"};
        cAttendanceStatus = new JComboBox<>(attendanceStatuses);
        cAttendanceStatus.setBounds(200, 170, 150, 30);
        add(cAttendanceStatus);

        submit = new JButton("Submit");
        submit.setBounds(70, 250, 100, 30);
        submit.addActionListener(this);
        add(submit);

        back = new JButton("Kembali");
        back.setBounds(200, 250, 100, 30);
        back.addActionListener(this);
        add(back);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submit) {
            String nomorPegawai = cEmployeeId.getSelectedItem();
            String selectedDay = (String) cWorkDay.getSelectedItem();
            String attendanceStatus = (String) cAttendanceStatus.getSelectedItem();
            LocalDate selectedDate = LocalDate.ofInstant(dateChooser.getDate().toInstant(), ZoneId.systemDefault());

            if (isWorkingDay(nomorPegawai, selectedDay)) {
                recordAttendance(nomorPegawai, selectedDate, selectedDay, attendanceStatus);
                JOptionPane.showMessageDialog(this, "Absensi berhasil dicatat.");
                setVisible(false);
                new Home();
            } else {
                JOptionPane.showMessageDialog(this, "Anda tidak dijadwalkan bekerja pada hari " + selectedDay);
            }
        } else if (ae.getSource() == back) {
            setVisible(false);
            new Home();
        }
    }

    private boolean isWorkingDay(String nomorPegawai, String selectedDay) {
        String jadwalKerja = getJadwalKerjaFromDatabase(nomorPegawai);
        nonWorkingDays = new HashSet<>();
        if (jadwalKerja.equals("Semua Hari Kecuali Senin")) {
            nonWorkingDays.add("SENIN");
        } else if (jadwalKerja.equals("Semua Hari Kecuali Selasa")) {
            nonWorkingDays.add("SELASA");
        }
        return !nonWorkingDays.contains(selectedDay.toUpperCase());
    }

    private void recordAttendance(String nomorPegawai, LocalDate date, String day, String attendanceStatus) {
        try {
            Connection conn = getConnection();
            String query = "INSERT INTO AbsensiPegawai (NomorPegawai, Tanggal, Hari, KeteranganAbsensi) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, nomorPegawai);
            ps.setDate(2, Date.valueOf(date));
            ps.setString(3, day);
            ps.setString(4, attendanceStatus);
            ps.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getJadwalKerjaFromDatabase(String nomorPegawai) {
        String jadwalKerja = "";
        try {
            Connection conn = getConnection();
            String query = "SELECT HariKerja FROM JadwalKerja WHERE NomorPegawai = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, nomorPegawai);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                jadwalKerja = rs.getString("HariKerja");
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jadwalKerja;
    }

    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/karyawan";
        String user = "root";
        String password = "";
        return DriverManager.getConnection(url, user, password);
    }

    public static void main(String[] args) {
        new EmployeeAttendance();
    }
}
