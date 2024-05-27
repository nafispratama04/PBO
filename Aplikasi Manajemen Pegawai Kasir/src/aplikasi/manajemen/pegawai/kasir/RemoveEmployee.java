package aplikasi.manajemen.pegawai.kasir;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;

public class RemoveEmployee extends JFrame implements ActionListener {
    
    Choice cEmpId;
    JButton delete, back;
    
    RemoveEmployee() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        
        JLabel labelempId = new JLabel("Nomor Pegawai");
        labelempId.setBounds(50, 50, 100, 30);
        add(labelempId);
        
        cEmpId = new Choice();
        cEmpId.setBounds(200, 50, 150, 30);
        add(cEmpId);
        
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/karyawan", "root", "");
            String query = "select * from PegawaiKasir";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()) {
                cEmpId.add(rs.getString("NomorPegawai"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        JLabel labelname = new JLabel("Nama");
        labelname.setBounds(50, 100, 100, 30);
        add(labelname);
        
        JLabel lblname = new JLabel();
        lblname.setBounds(200, 100, 100, 30);
        add(lblname);
        
        JLabel labelphone = new JLabel("Telepon");
        labelphone.setBounds(50, 150, 100, 30);
        add(labelphone);
        
        JLabel lblphone = new JLabel();
        lblphone.setBounds(200, 150, 100, 30);
        add(lblphone);
        
        JLabel labelemail = new JLabel("Email");
        labelemail.setBounds(50, 200, 100, 30);
        add(labelemail);
        
        JLabel lblemail = new JLabel();
        lblemail.setBounds(200, 200, 100, 30);
        add(lblemail);
        
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/karyawan", "root", "");
            String query = "select * from PegawaiKasir where NomorPegawai = '"+cEmpId.getSelectedItem()+"'";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()) {
                lblname.setText(rs.getString("Nama"));
                lblphone.setText(rs.getString("NomorTelepon"));
                lblemail.setText(rs.getString("Email"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        cEmpId.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ie) {
                try {
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/karyawan", "root", "");
                    String query = "select * from PegawaiKasir where NomorPegawai = '"+cEmpId.getSelectedItem()+"'";
                    Statement st = conn.createStatement();
                    ResultSet rs = st.executeQuery(query);
                    while(rs.next()) {
                        lblname.setText(rs.getString("Nama"));
                        lblphone.setText(rs.getString("NomorTelepon"));
                        lblemail.setText(rs.getString("Email"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        
        delete = new JButton("Hapus");
        delete.setBounds(80, 250, 100,30);
        delete.setBackground(Color.BLACK);
        delete.setForeground(Color.WHITE);
        delete.addActionListener(this);
        add(delete);
        
        back = new JButton("Kembali");
        back.setBounds(220, 250, 100,30);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.addActionListener(this);
        add(back);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/delete.png"));
        Image i2 = i1.getImage().getScaledInstance(600, 400, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(350, 0, 600, 400);
        add(image);
        
        setSize(1000, 400);
        setLocation(300, 150);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == delete) {
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/karyawan", "root", "");
                String query = "delete from PegawaiKasir where NomorPegawai = '"+cEmpId.getSelectedItem()+"'";
                Statement st = conn.createStatement();
                st.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "Informasi Pegawai Telah Dihapus");
                setVisible(false);
                new Home();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            setVisible(false);
            new Home();
        }
    }

    public static void main(String[] args) {
        new RemoveEmployee();
    }
}
