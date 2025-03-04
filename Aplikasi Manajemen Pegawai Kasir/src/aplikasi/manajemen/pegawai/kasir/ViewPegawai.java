package aplikasi.manajemen.pegawai.kasir;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;
import java.awt.event.*;

public class ViewPegawai extends JFrame implements ActionListener {

    JTable table;
    Choice cemployeeId;
    JButton search, print, back;

    ViewPegawai() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel searchlbl = new JLabel("Cari berdasarkan Nomor Pegawai");
        searchlbl.setBounds(20, 20, 200, 20);
        add(searchlbl);

        cemployeeId = new Choice();
        cemployeeId.setBounds(230, 20, 150, 20);
        add(cemployeeId);

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/karyawan", "root", "");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT NomorPegawai FROM PegawaiKasir");
            while (rs.next()) {
                cemployeeId.add(rs.getString("NomorPegawai"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        table = new JTable();

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/karyawan", "root", "");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM PegawaiKasir");
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }

        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(0, 100, 900, 600);
        add(jsp);

        search = new JButton("Search");
        search.setBounds(20, 70, 80, 20);
        search.addActionListener(this);
        add(search);

        print = new JButton("Print");
        print.setBounds(120, 70, 80, 20);
        print.addActionListener(this);
        add(print);

        back = new JButton("Back");
        back.setBounds(220, 70, 80, 20);
        back.addActionListener(this);
        add(back);

        setSize(900, 700);
        setLocation(300, 100);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == search) {
            String query = "SELECT * FROM PegawaiKasir WHERE NomorPegawai = '" + cemployeeId.getSelectedItem() + "'";
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/karyawan", "root", "");
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query);
                table.setModel(DbUtils.resultSetToTableModel(rs));
            } catch (Exception e) {
                e.printStackTrace();
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
        new ViewPegawai();
    }
}
