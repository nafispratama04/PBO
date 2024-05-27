package aplikasi.manajemen.pegawai.kasir;

import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UpdateEmployee extends JFrame implements ActionListener {

    JTextField tfname, tfidNumber, tfplaceOfBirth, tfaddress, tfphone, tfemail, tfsalary;
    JDateChooser dcDateOfBirth, dcStartDate;
    JButton update, back;
    JComboBox<String> cEmpId;
    String selectedEmpId;

    UpdateEmployee() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel heading = new JLabel("Ubah Data Pegawai");
        heading.setBounds(320, 30, 500, 50);
        heading.setFont(new Font("SAN_SERIF", Font.BOLD, 25));
        add(heading);

        JLabel labelSelectEmp = new JLabel("Pilih Nomor Pegawai");
        labelSelectEmp.setBounds(50, 100, 150, 30);
        labelSelectEmp.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelSelectEmp);

        cEmpId = new JComboBox<>();
        cEmpId.setBounds(200, 100, 200, 30);
        add(cEmpId);

        cEmpId.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedEmpId = (String) cEmpId.getSelectedItem();
                loadEmployeeData(selectedEmpId);
            }
        });

        JLabel labelname = new JLabel("Nama");
        labelname.setBounds(50, 150, 150, 30);
        labelname.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelname);

        tfname = new JTextField();
        tfname.setBounds(200, 150, 200, 30);
        add(tfname);

        JLabel labelidNumber = new JLabel("Nomor Pegawai");
        labelidNumber.setBounds(50, 200, 150, 30);
        labelidNumber.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelidNumber);

        tfidNumber = new JTextField();
        tfidNumber.setBounds(200, 200, 200, 30);
        add(tfidNumber);

        JLabel labelplaceOfBirth = new JLabel("Tempat Lahir");
        labelplaceOfBirth.setBounds(50, 250, 150, 30);
        labelplaceOfBirth.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelplaceOfBirth);

        tfplaceOfBirth = new JTextField();
        tfplaceOfBirth.setBounds(200, 250, 200, 30);
        add(tfplaceOfBirth);

        JLabel labelDateOfBirth = new JLabel("Tanggal Lahir");
        labelDateOfBirth.setBounds(50, 300, 150, 30);
        labelDateOfBirth.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelDateOfBirth);

        dcDateOfBirth = new JDateChooser();
        dcDateOfBirth.setDateFormatString("yyyy-MM-dd");
        dcDateOfBirth.setBounds(200, 300, 200, 30);
        add(dcDateOfBirth);

        JLabel labeladdress = new JLabel("Alamat");
        labeladdress.setBounds(50, 350, 150, 30);
        labeladdress.setFont(new Font("serif", Font.PLAIN, 20));
        add(labeladdress);

        tfaddress = new JTextField();
        tfaddress.setBounds(200, 350, 200, 30);
        add(tfaddress);

        JLabel labelphone = new JLabel("Telepon");
        labelphone.setBounds(50, 400, 150, 30);
        labelphone.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelphone);

        tfphone = new JTextField();
        tfphone.setBounds(200, 400, 200, 30);
        add(tfphone);

        JLabel labelemail = new JLabel("Email");
        labelemail.setBounds(50, 450, 150, 30);
        labelemail.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelemail);

        tfemail = new JTextField();
        tfemail.setBounds(200, 450, 200, 30);
        add(tfemail);

        JLabel labelsalary = new JLabel("Gaji");
        labelsalary.setBounds(50, 500, 150, 30);
        labelsalary.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelsalary);

        tfsalary = new JTextField();
        tfsalary.setBounds(200, 500, 200, 30);
        add(tfsalary);

        JLabel labelStartDate = new JLabel("Tanggal Mulai");
        labelStartDate.setBounds(50, 550, 150, 30);
        labelStartDate.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelStartDate);

        dcStartDate = new JDateChooser();
        dcStartDate.setDateFormatString("yyyy-MM-dd");
        dcStartDate.setBounds(200, 550, 200, 30);
        add(dcStartDate);

        update = new JButton("Ubah Data");
        update.setBounds(250, 600, 150, 40);
        update.addActionListener(this);
        add(update);

        back = new JButton("Kembali");
        back.setBounds(450, 600, 150, 40);
        back.addActionListener(this);
        add(back);

        setSize(700, 700);
        setLocationRelativeTo(null);
        setVisible(true);

        loadEmployeeIds();
    }

    private void loadEmployeeIds() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/karyawan", "root", "");
            String query = "SELECT NomorPegawai FROM PegawaiKasir";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                cEmpId.addItem(rs.getString("NomorPegawai"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadEmployeeData(String empId) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/karyawan", "root", "");
            String query = "SELECT * FROM PegawaiKasir WHERE NomorPegawai = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, empId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                tfname.setText(rs.getString("Nama"));
                tfidNumber.setText(rs.getString("NomorPegawai"));
                tfplaceOfBirth.setText(rs.getString("TempatLahir"));
                dcDateOfBirth.setDate(rs.getDate("TanggalLahir"));
                tfaddress.setText(rs.getString("Alamat"));
                tfphone.setText(rs.getString("NomorTelepon"));
                tfemail.setText(rs.getString("Email"));
                tfsalary.setText(rs.getString("Gaji"));
                dcStartDate.setDate(rs.getDate("TanggalMulai"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == update) {
            String name = tfname.getText();
            String idNumber = tfidNumber.getText();
            String placeOfBirth = tfplaceOfBirth.getText();
            java.util.Date dateOfBirth = dcDateOfBirth.getDate();
            String address = tfaddress.getText();
            String phone = tfphone.getText();
            String email = tfemail.getText();
            double salary = Double.parseDouble(tfsalary.getText());
            java.util.Date startDate = dcStartDate.getDate();

            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/karyawan", "root", "");
                String query = "UPDATE PegawaiKasir SET Nama=?, TempatLahir=?, TanggalLahir=?, Alamat=?, NomorTelepon=?, Email=?, Gaji=?, TanggalMulai=? WHERE NomorPegawai=?";
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setString(1, name);
                pst.setString(2, placeOfBirth);
                pst.setDate(3, new java.sql.Date(dateOfBirth.getTime()));
                pst.setString(4, address);
                pst.setString(5, phone);
                pst.setString(6, email);
                pst.setDouble(7, salary);
                pst.setDate(8, new java.sql.Date(startDate.getTime()));
                pst.setString(9, idNumber);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data berhasil diubah");
                setVisible(false);
                new Home();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == back) {
            setVisible(false);
            new Home();
        }
    }

    public static void main(String[] args) {
        new UpdateEmployee();
    }
}
