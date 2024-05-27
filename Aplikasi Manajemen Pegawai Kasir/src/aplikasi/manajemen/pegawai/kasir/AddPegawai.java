package aplikasi.manajemen.pegawai.kasir;

import java.awt.*;
import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import java.awt.event.*;
import java.sql.*;

public class AddPegawai extends JFrame implements ActionListener {
    
    JTextField tfname, tfidNumber, tfaddress, tfphone, tfemail, tfsalary, tfplaceOfBirth;
    JDateChooser dcDateOfBirth, dcStartDate;
    JButton add, back;
    
    AddPegawai() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        
        JLabel heading = new JLabel("Tambah Data Pegawai");
        heading.setBounds(320, 30, 500, 50);
        heading.setFont(new Font("SAN_SERIF", Font.BOLD, 25));
        add(heading);
        
        JLabel labelname = new JLabel("Nama");
        labelname.setBounds(50, 100, 150, 30);
        labelname.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelname);
        
        tfname = new JTextField();
        tfname.setBounds(200, 100, 200, 30);
        add(tfname);
        
        JLabel labelidNumber = new JLabel("Nomor Pegawai");
        labelidNumber.setBounds(50, 150, 150, 30);
        labelidNumber.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelidNumber);
        
        tfidNumber = new JTextField();
        tfidNumber.setBounds(200, 150, 200, 30);
        add(tfidNumber);
        
        JLabel labelplaceOfBirth = new JLabel("Tempat Lahir");
        labelplaceOfBirth.setBounds(50, 200, 150, 30);
        labelplaceOfBirth.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelplaceOfBirth);
        
        tfplaceOfBirth = new JTextField();
        tfplaceOfBirth.setBounds(200, 200, 200, 30);
        add(tfplaceOfBirth);
        
        JLabel labelDateOfBirth = new JLabel("Tanggal Lahir");
        labelDateOfBirth.setBounds(50, 250, 150, 30);
        labelDateOfBirth.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelDateOfBirth);
        
        dcDateOfBirth = new JDateChooser();
        dcDateOfBirth.setDateFormatString("yyyy-MM-dd");
        dcDateOfBirth.setBounds(200, 250, 200, 30);
        add(dcDateOfBirth);
        
        JLabel labeladdress = new JLabel("Alamat");
        labeladdress.setBounds(50, 300, 150, 30);
        labeladdress.setFont(new Font("serif", Font.PLAIN, 20));
        add(labeladdress);
        
        tfaddress = new JTextField();
        tfaddress.setBounds(200, 300, 200, 30);
        add(tfaddress);
        
        JLabel labelphone = new JLabel("Telepon");
        labelphone.setBounds(50, 350, 150, 30);
        labelphone.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelphone);
        
        tfphone = new JTextField();
        tfphone.setBounds(200, 350, 200, 30);
        add(tfphone);
        
        JLabel labelemail = new JLabel("Email");
        labelemail.setBounds(50, 400, 150, 30);
        labelemail.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelemail);
        
        tfemail = new JTextField();
        tfemail.setBounds(200, 400, 200, 30);
        add(tfemail);
        
        JLabel labelsalary = new JLabel("Gaji");
        labelsalary.setBounds(50, 450, 150, 30);
        labelsalary.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelsalary);
        
        tfsalary = new JTextField();
        tfsalary.setBounds(200, 450, 200, 30);
        add(tfsalary);
        
        JLabel labelStartDate = new JLabel("Tanggal Mulai");
        labelStartDate.setBounds(50, 500, 150, 30);
        labelStartDate.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelStartDate);
        
        dcStartDate = new JDateChooser();
        dcStartDate.setDateFormatString("yyyy-MM-dd");
        dcStartDate.setBounds(200, 500, 200, 30);
        add(dcStartDate);
        
        add = new JButton("Tambah Data");
        add.setBounds(250, 550, 150, 40);
        add.addActionListener(this);
        add(add);
        
        back = new JButton("Kembali");
        back.setBounds(450, 550, 150, 40);
        back.addActionListener(this);
        add(back);
        
        setSize(700, 650);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == add) {
            String name = tfname.getText();
            String idNumber = tfidNumber.getText();
            String placeOfBirth = tfplaceOfBirth.getText();
            String dateOfBirth = ((JTextField) dcDateOfBirth.getDateEditor().getUiComponent()).getText();
            String address = tfaddress.getText();
            String phone = tfphone.getText();
            String email = tfemail.getText();
            double salary = Double.parseDouble(tfsalary.getText());
            String startDate = ((JTextField) dcStartDate.getDateEditor().getUiComponent()).getText();
            
            try {
                // Menghubungkan ke database dan mengeksekusi query
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/karyawan", "root", "");
                String query = "INSERT INTO PegawaiKasir (Nama, NomorPegawai, TempatLahir, TanggalLahir, Alamat, NomorTelepon, Email, Gaji, TanggalMulai) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setString(1, name);
                pst.setString(2, idNumber);
                pst.setString(3, placeOfBirth);
                pst.setString(4, dateOfBirth);
                pst.setString(5, address);
                pst.setString(6, phone);
                pst.setString(7, email);
                pst.setDouble(8, salary);
                pst.setString(9, startDate);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan");
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
        new AddPegawai();
    }
}
