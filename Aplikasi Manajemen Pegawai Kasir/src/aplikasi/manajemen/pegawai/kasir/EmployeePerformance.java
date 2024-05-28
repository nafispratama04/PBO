package aplikasi.manajemen.pegawai.kasir;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class EmployeePerformance extends JFrame {

    private JTable table;
    private JButton back;

    public EmployeePerformance() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel heading = new JLabel("Employee Performance");
        heading.setBounds(20, 20, 200, 20);
        add(heading);

        table = new JTable();

        try {
            Connection conn = getConnection();
            String query = "SELECT pk.NomorPegawai, pk.Nama, "
                    + "CASE "
                    + "WHEN COUNT(ap.Tanggal) = 0 THEN 100 "
                    + "ELSE ROUND((COUNT(CASE WHEN ap.KeteranganAbsensi IN ('Hadir', 'Sakit', 'Izin') THEN 1 END) / COUNT(ap.Tanggal)) * 100, 2) "
                    + "END AS PersentaseKehadiran, "
                    + "CASE "
                    + "WHEN COUNT(ap.Tanggal) = 0 THEN 'Sangat Baik' "
                    + "WHEN ROUND((COUNT(CASE WHEN ap.KeteranganAbsensi IN ('Hadir', 'Sakit', 'Izin') THEN 1 END) / COUNT(ap.Tanggal)) * 100, 2) >= 76 THEN 'Sangat Baik' "
                    + "WHEN ROUND((COUNT(CASE WHEN ap.KeteranganAbsensi IN ('Hadir', 'Sakit', 'Izin') THEN 1 END) / COUNT(ap.Tanggal)) * 100, 2) >= 51 THEN 'Baik' "
                    + "WHEN ROUND((COUNT(CASE WHEN ap.KeteranganAbsensi IN ('Hadir', 'Sakit', 'Izin') THEN 1 END) / COUNT(ap.Tanggal)) * 100, 2) >= 26 THEN 'Buruk' "
                    + "ELSE 'Sangat Buruk' "
                    + "END AS PerformaPegawai "
                    + "FROM PegawaiKasir pk "
                    + "LEFT JOIN AbsensiPegawai ap ON pk.NomorPegawai = ap.NomorPegawai "
                    + "GROUP BY pk.NomorPegawai, pk.Nama";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            table.setModel(DbUtils.resultSetToTableModel(rs));
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(20, 50, 860, 500);
        add(jsp);

        back = new JButton("Back");
        back.setBounds(20, 570, 80, 30);
        back.addActionListener(e -> {
            setVisible(false);
            new Home();
        });
        add(back);

        setSize(900, 650);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/karyawan";
        String user = "root";
        String password = "";
        return DriverManager.getConnection(url, user, password);
    }

    public static void main(String[] args) {
        new EmployeePerformance();
    }
}
