
package aplikasi.manajemen.pegawai.kasir;

import java.awt.*;
import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import java.util.*;
import java.awt.event.*;

/**
 *
 * @author bagus
 */
public class AddPegawai extends JFrame implements ActionListener{
    
    JTextField tfname, tffname, tfaddress, tfphone, tfemail, tfsalary;
    
    Random ran = new Random();
    int number = ran.nextInt(999999);
    
    
    JDateChooser dcdob;
    JComboBox edu;
    JLabel lblempId;
    JButton add, back;
    
     AddPegawai(){
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        
        //Heading
        JLabel heading = new JLabel("Add Employee Detail");
        heading.setBounds(320, 30, 500, 50);
        heading.setFont(new Font("SAN_SERIF", Font.BOLD, 25));
        add(heading);
        
        //Nama
        JLabel labelname = new JLabel("Nama");
        labelname.setBounds(50, 150, 150, 30);
        labelname.setFont( new Font("Serif", Font.PLAIN, 20));
        add(labelname);
        
        JTextField tfname = new JTextField();
        tfname.setBounds(200, 150, 150, 30);
        add(tfname);
        
        //Nama2
        JLabel labelfname = new JLabel("Nama2");
        labelfname.setBounds(400, 150, 150, 30);
        labelfname.setFont( new Font("Serif", Font.PLAIN, 20));
        add(labelfname);
        
        JTextField tffname = new JTextField();
        tffname.setBounds(600, 150, 150, 30);
        add(tffname);
        
        //TTL
        JLabel labeltl = new JLabel("Tanggal Lahir");
        labeltl.setBounds(50, 200, 150, 30);
        labeltl.setFont( new Font("Serif", Font.PLAIN, 20));
        add(labeltl);
        
        JDateChooser dcdob = new JDateChooser();
        dcdob.setBounds(200, 200, 150, 30);
        add(dcdob);
        
        //Gaji
        JLabel labelsalary = new JLabel("Gaji");
        labelsalary.setBounds(400, 200, 150, 30);
        labelsalary.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelsalary);
        
        JTextField tfsalary = new JTextField();
        tfsalary.setBounds(600, 200, 150, 30);
        add(tfsalary);
        
        //Alamat
        JLabel labeladdress = new JLabel("Alamat");
        labeladdress.setBounds(50, 250, 150, 30);
        labeladdress.setFont(new Font("serif", Font.PLAIN, 20));
        add(labeladdress);
        
        JTextField tfaddress = new JTextField();
        tfaddress.setBounds(200, 250, 150, 30);
        add(tfaddress);
        
        //Telp
        JLabel labelphone = new JLabel("No. Telp");
        labelphone.setBounds(400, 250, 150, 30);
        labelphone.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelphone);
        
        JTextField tfphone = new JTextField();
        tfphone.setBounds(600, 250, 150, 30);
        add(tfphone);
        
        //Email
        JLabel labelemail = new JLabel("Email");
        labelemail.setBounds(50, 300, 150, 30);
        labelemail.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelemail);
        
        JTextField tfemail = new JTextField();
        tfemail.setBounds(200, 300, 150, 30);
        add(tfemail);
        
        //Riwayat Pendidikan
        JLabel labeleducation = new JLabel("Pendidikan Terakhir");
        labeleducation.setBounds(400, 300, 200, 30);
        labeleducation.setFont(new Font("serif", Font.PLAIN, 20));
        add(labeleducation);
        
        String courses[] = {"SD", "SMP", "SMA", "S1", "D3", "D4"};
        JComboBox edu = new JComboBox(courses);
        edu.setBounds(600, 300, 150, 30);
        add(edu);
        
        JLabel labelempId = new JLabel("ID Pegawai");
        labelempId.setBounds(50, 350, 150, 30);
        labelempId.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelempId);
        
        JLabel lblempId = new JLabel("" + number);
        lblempId.setBounds(200, 350, 150, 30);
        lblempId.setFont(new Font("serif", Font.PLAIN, 20));
        add(lblempId);
        
        JButton add = new JButton("Add");
        add.setBounds(250, 550, 150, 40);
        add.addActionListener(this);
        add.setBackground(Color.BLACK);
        add.setForeground(Color.WHITE);
        add(add);
        
        JButton back = new JButton("Back");
        back.setBounds(450, 550, 150, 40);
        back.addActionListener(this);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        add(back);
        
        
        
        
         setSize(900, 700);
         setLocation(300, 50);
         setVisible(true);
    }  
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == add ) {
            String name = tfname.getText();
            String fname = tffname.getText();
            String dob = ((JTextField) dcdob.getDateEditor().getUiComponent()).getText();
            String salary = tfsalary.getText();
            String address = tfaddress.getText();
            String phone = tfphone.getText();
            String email = tfemail.getText();
            String education = (String) edu.getSelectedItem();
            String empId = lblempId.getText();
            
            try {
                Conn conn = new Conn();
                String query = "insert into employee values('"+name+"', '"+fname+"', '"+dob+"', '"+salary+"', '"+address+"', '"+phone+"', '"+email+"', '"+education+"', '"+empId+"')";
                conn.s.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "Pegawai Berhasil Ditambahkan");
                setVisible(false);
                new Home();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }  else {
            setVisible(false);
            new Home();
        }
    } 
     
    public static void main(String[] args) {
        new AddPegawai();
    }
}
