package aplikasi.manajemen.pegawai.kasir;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddSchedule extends JFrame implements ActionListener {

    Choice cemployeeId;
    JComboBox<String> cWorkSchedule, cTrainingSchedule;
    JButton submit, back;

    AddSchedule() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel heading = new JLabel("Tambah Jadwal Pegawai");
        heading.setBounds(150, 20, 400, 30);
        heading.setFont(new Font("SAN_SERIF", Font.BOLD, 20));
        add(heading);

        JLabel lblEmpId = new JLabel("Nomor Pegawai");
        lblEmpId.setBounds(50, 80, 150, 30);
        add(lblEmpId);

        cemployeeId = new Choice();
        cemployeeId.setBounds(200, 80, 150, 30);
        add(cemployeeId);

        try {
            Connection conn = getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT NomorPegawai FROM PegawaiKasir");
            while (rs.next()) {
                cemployeeId.add(rs.getString("NomorPegawai"));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JLabel lblWorkSchedule = new JLabel("Jadwal Kerja");
        lblWorkSchedule.setBounds(50, 130, 150, 30);
        add(lblWorkSchedule);

        String[] workDaysOptions = { "Semua Hari Kecuali Senin", "Semua Hari Kecuali Selasa" };
        cWorkSchedule = new JComboBox<>(workDaysOptions);
        cWorkSchedule.setBounds(200, 130, 200, 30);
        add(cWorkSchedule);

        JLabel lblTrainingSchedule = new JLabel("Jadwal Pelatihan");
        lblTrainingSchedule.setBounds(50, 180, 150, 30);
        add(lblTrainingSchedule);

        String[] trainingDaysOptions = { "Rabu Minggu Ke 4", "Kamis Minggu Ke 4" };
        cTrainingSchedule = new JComboBox<>(trainingDaysOptions);
        cTrainingSchedule.setBounds(200, 180, 200, 30);
        add(cTrainingSchedule);

        submit = new JButton("Submit");
        submit.setBounds(100, 250, 100, 30);
        submit.addActionListener(this);
        add(submit);

        back = new JButton("Kembali");
        back.setBounds(250, 250, 100, 30);
        back.addActionListener(this);
        add(back);

        setSize(500, 350);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submit) {
            String nomorPegawai = cemployeeId.getSelectedItem();
            String hariKerja = (String) cWorkSchedule.getSelectedItem();
            String mingguPelatihan = (String) cTrainingSchedule.getSelectedItem();
            String topikPelatihan = "Briefing Promo dan Dekorasi Toko sesuai Event Bulan Depan";

            try {
                Connection conn = getConnection();
                String workQuery = "INSERT INTO JadwalKerja (NomorPegawai, HariKerja) VALUES (?, ?)";
                String trainingQuery = "INSERT INTO JadwalPelatihan (NomorPegawai, MingguPelatihan, TopikPelatihan) VALUES (?, ?, ?)";

                PreparedStatement psWork = conn.prepareStatement(workQuery);
                psWork.setString(1, nomorPegawai);
                psWork.setString(2, hariKerja);
                psWork.executeUpdate();

                PreparedStatement psTraining = conn.prepareStatement(trainingQuery);
                psTraining.setString(1, nomorPegawai);
                psTraining.setString(2, mingguPelatihan);
                psTraining.setString(3, topikPelatihan);
                psTraining.executeUpdate();

                JOptionPane.showMessageDialog(null, "Jadwal berhasil ditambahkan!");
                conn.close();
                new Home();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }
        } else if (ae.getSource() == back) {
            setVisible(false);
            new Home();
        }
    }

    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/karyawan";
        String user = "root";
        String password = "";
        return DriverManager.getConnection(url, user, password);
    }

    public static void main(String[] args) {
        new AddSchedule();
    }
}
