package aplikasi.manajemen.pegawai.kasir;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import net.proteanit.sql.DbUtils;

public class SalaryPayment extends JFrame implements ActionListener {
    private JTable table;
    private JButton search, printSlip;
    private JTextField tfNomorPegawai, tfMonthYear;

    public SalaryPayment() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel heading = new JLabel("Salary Payment");
        heading.setBounds(20, 20, 200, 20);
        add(heading);

        JLabel lblNomorPegawai = new JLabel("Nomor Pegawai:");
        lblNomorPegawai.setBounds(20, 50, 150, 20);
        add(lblNomorPegawai);

        tfNomorPegawai = new JTextField();
        tfNomorPegawai.setBounds(180, 50, 100, 20);
        add(tfNomorPegawai);

        JLabel lblMonthYear = new JLabel("Bulan-Tahun (MM-YYYY):");
        lblMonthYear.setBounds(20, 80, 150, 20);
        add(lblMonthYear);

        tfMonthYear = new JTextField();
        tfMonthYear.setBounds(180, 80, 100, 20);
        add(tfMonthYear);

        table = new JTable();
        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(20, 110, 860, 400);
        add(jsp);

        search = new JButton("Cari");
        search.setBounds(20, 530, 120, 30);
        search.addActionListener(this);
        add(search);

        printSlip = new JButton("Print Slip Gaji");
        printSlip.setBounds(150, 530, 150, 30);
        printSlip.addActionListener(this);
        add(printSlip);

        JButton back = new JButton("Kembali");
        back.setBounds(310, 530, 120, 30);
        back.addActionListener(e -> {
            setVisible(false);
            new Home();
        });
        add(back);

        setSize(900, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == search) {
            searchSalary();
        } else if (ae.getSource() == printSlip) {
            try {
                table.print();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void searchSalary() {
        String nomorPegawai = tfNomorPegawai.getText().trim();
        String monthYear = tfMonthYear.getText().trim();
        if (nomorPegawai.isEmpty() || monthYear.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Masukkan Nomor Pegawai dan Bulan-Tahun.");
            return;
        }

        LocalDate startDate, endDate;
        try {
            startDate = LocalDate.parse("01-" + monthYear, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Format Bulan-Tahun tidak valid.");
            return;
        }

        try {
            Connection conn = getConnection();
            String query = "SELECT PK.NomorPegawai, PK.Nama, PK.Gaji, " +
                "CASE " +
                "WHEN COUNT(AP.Tanggal) = 0 THEN 100 " +
                "ELSE ROUND((COUNT(CASE WHEN AP.KeteranganAbsensi IN ('Hadir', 'Sakit', 'Izin') THEN 1 END) / COUNT(AP.Tanggal)) * 100, 2) " +
                "END AS PersentaseKehadiran, " +
                "PK.Gaji * ROUND((COUNT(CASE WHEN AP.KeteranganAbsensi IN ('Hadir', 'Sakit', 'Izin') THEN 1 END) / COUNT(AP.Tanggal)), 2) AS GajiDibayar, " +
                "? AS BulanTahun, " +
                "CURRENT_DATE AS TanggalPembayaran " +
                "FROM PegawaiKasir PK " +
                "LEFT JOIN AbsensiPegawai AP ON PK.NomorPegawai = AP.NomorPegawai AND AP.Tanggal BETWEEN ? AND ? " +
                "WHERE PK.NomorPegawai = ? " +
                "GROUP BY PK.NomorPegawai, PK.Nama, PK.Gaji";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, monthYear);
            ps.setDate(2, Date.valueOf(startDate));
            ps.setDate(3, Date.valueOf(endDate));
            ps.setString(4, nomorPegawai);
            ResultSet rs = ps.executeQuery();

            table.setModel(DbUtils.resultSetToTableModel(rs));

            // Simpan data gaji ke database
        while (rs.next()) {
        double gajiDasar = rs.getDouble("Gaji");
        saveSalaryData(conn, nomorPegawai, monthYear, gajiDasar);
        }
        conn.close();
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat mencari data.");
    }
}

    private void saveSalaryData(Connection conn, String nomorPegawai, String monthYear, double gajiDasar) {
    try {
        String insertQuery = "INSERT INTO GajiPembayaran (NomorPegawai, BulanTahun, GajiDasar, TanggalPembayaran) VALUES (?, ?, ?, ?)";
        PreparedStatement insertStatement = conn.prepareStatement(insertQuery);
        insertStatement.setString(1, nomorPegawai);
        insertStatement.setString(2, monthYear);
        insertStatement.setDouble(3, gajiDasar);
        insertStatement.setDate(4, Date.valueOf(LocalDate.now()));

        // Cetak pernyataan SQL sebelum dieksekusi
        System.out.println("SQL Statement: " + insertStatement.toString());

        // Eksekusi pernyataan SQL
        int rowsAffected = insertStatement.executeUpdate();

        // Periksa hasil eksekusi
        if (rowsAffected > 0) {
            System.out.println("Data gaji berhasil disimpan.");
        } else {
            System.out.println("Tidak ada data gaji yang disimpan.");
        }

        // Commit transaksi
        conn.commit();
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat menyimpan data gaji.");
    }
}
    
    private void saveSalaryData(Connection conn, String nomorPegawai, String monthYear, double gajiDasar, double persentaseKehadiran, double gajiDibayarkan) {
    try {
        // Membuat pernyataan SQL untuk menyimpan data gaji
        String insertQuery = "INSERT INTO GajiPembayaran (NomorPegawai, BulanTahun, GajiDasar, PersentaseKehadiran, GajiDibayarkan, TanggalPembayaran) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement insertStatement = conn.prepareStatement(insertQuery);
        
        // Mengatur parameter pada pernyataan SQL
        insertStatement.setString(1, nomorPegawai);
        insertStatement.setString(2, monthYear);
        insertStatement.setDouble(3, gajiDasar);
        insertStatement.setDouble(4, persentaseKehadiran);
        insertStatement.setDouble(5, gajiDibayarkan);
        insertStatement.setDate(6, Date.valueOf(LocalDate.now()));

        // Eksekusi pernyataan SQL untuk menyimpan data gaji
        int rowsAffected = insertStatement.executeUpdate();

        // Commit transaksi jika penyimpanan berhasil
        if (rowsAffected > 0) {
            conn.commit();
            System.out.println("Data gaji berhasil disimpan.");
        } else {
            System.out.println("Tidak ada data gaji yang disimpan.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat menyimpan data gaji.");
    }
}

    private void printSalarySlip() {
        String nomorPegawai = tfNomorPegawai.getText().trim();
        String monthYear = tfMonthYear.getText().trim();
        if (nomorPegawai.isEmpty() || monthYear.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Masukkan Nomor Pegawai dan Bulan-Tahun.");
            return;
        }

        LocalDate date;
        try {
            date = LocalDate.parse("01-" + monthYear, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Format Bulan-Tahun tidak valid.");
            return;
        }

        try {
            Connection conn = getConnection();
            String query = "SELECT * FROM GajiPembayaran WHERE NomorPegawai = ? AND BulanTahun = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, nomorPegawai);
            ps.setString(2, monthYear);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String slip = "Slip Gaji\n";
                slip += "Nomor Pegawai: " + rs.getString("NomorPegawai") + "\n";
                slip += "Bulan-Tahun: " + rs.getString("BulanTahun") + "\n";
                slip += "Gaji Dasar: " + rs.getDouble("GajiDasar") + "\n";
                slip += "Persentase Kehadiran: " + rs.getDouble("PersentaseKehadiran") + "%\n";
                slip += "Gaji Dibayar: " + rs.getDouble("GajiDibayar") + "\n";
                slip += "Tanggal Pembayaran: " + rs.getDate("TanggalPembayaran") + "\n";

                JTextArea textArea = new JTextArea(slip);
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(400, 300));
                JOptionPane.showMessageDialog(this, scrollPane, "Slip Gaji", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Data gaji tidak ditemukan.");
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat mencetak slip gaji.");
        }
    }

    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/karyawan";
        String user = "root";
        String password = "";
        return DriverManager.getConnection(url, user, password);
    }

    public static void main(String[] args) {
        new SalaryPayment();
    }
}
