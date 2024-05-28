package aplikasi.manajemen.pegawai.kasir;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;
import java.awt.event.*;

public class ViewSchedule extends JFrame implements ActionListener {

    JTable table;
    JButton print, back, search;

    ViewSchedule() {

        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel heading = new JLabel("View Schedule");
        heading.setBounds(20, 20, 200, 20);
        add(heading);

        table = new JTable();
        
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/karyawan", "root", "");
            String query = "SELECT pk.NomorPegawai, pk.Nama, jk.HariKerja, jp.MingguPelatihan, jp.TopikPelatihan FROM PegawaiKasir pk LEFT JOIN JadwalKerja jk ON pk.NomorPegawai = jk.NomorPegawai LEFT JOIN JadwalPelatihan jp ON pk.NomorPegawai = jp.NomorPegawai";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }

        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(0, 100, 900, 600);
        add(jsp);

        print = new JButton("Print");
        print.setBounds(20, 70, 80, 20);
        print.addActionListener(this);
        add(print);

        back = new JButton("Back");
        back.setBounds(120, 70, 80, 20);
        back.addActionListener(this);
        add(back);
        
        search = new JButton("Search");
        search.setBounds(220, 70, 80, 20);
        search.addActionListener(this);
        add(search);

        setSize(900, 700);
        setLocation(300, 100);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == search) {
            String searchTerm = JOptionPane.showInputDialog(this, "Enter employee name to search:");
            if (searchTerm != null && !searchTerm.isEmpty()) {
                try {
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/karyawan", "root", "");
                    String query = "SELECT pk.NomorPegawai, pk.Nama, jk.HariKerja, jp.MingguPelatihan, jp.TopikPelatihan FROM PegawaiKasir pk LEFT JOIN JadwalKerja jk ON pk.NomorPegawai = jk.NomorPegawai LEFT JOIN JadwalPelatihan jp ON pk.NomorPegawai = jp.NomorPegawai WHERE pk.Nama LIKE ?";
                    PreparedStatement ps = conn.prepareStatement(query);
                    ps.setString(1, "%" + searchTerm + "%");
                    ResultSet rs = ps.executeQuery();
                    table.setModel(DbUtils.resultSetToTableModel(rs));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a valid search term.");
            }
        } else if (ae.getSource() == print) {
            try {
                table.print();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == back) {
            setVisible(false);
            new Home();
        }
    }

    public static void main(String[] args) {
        new ViewSchedule();
    }
}
