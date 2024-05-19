package aplikasi.manajemen.pegawai.kasir;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Home extends JFrame implements ActionListener{
    
    JButton view, add, update, remove;
    
    Home(){
        setLayout(null);
        ImageIcon il = new ImageIconLoader(ClassLoader.getSystemResource("icons/home.jpg"));
        Image i2 - il.getImage().getScaledInstance(1120, 630, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0, 0, 1120, 630);
        add(image);
        
        JLabel heading = new JLabel("Manajemen Pegawai Kasir");
        heading.setBounds(620,20,400,40);
        heading.setFont(new Font("Raleway", Font.BOLD, 25));
        image.add(heading);
        
        add = new JButton("Tambah Pegawai");
        add.setBounds(650, 80, 150, 40);
        add.addActionListener(this);
        image.add(add);
        
        view = new JButton("Lihat Pegawai");
        view.setBounds(820, 80, 150, 40);
        view.addActionListener(this);
        image.add(view);
        
        update = new JButton("Update Pegawai");
        update.setBounds(650, 140, 150, 40);
        update.addActionListener(this);
        image.add(update);
        
        remove = new JButton("Hapus Pegawai");
        remove.setBounds(820, 140, 150, 40);
        remove.addActionListener(this);
        image.add(remove);
        
        setSize(1120, 630);
        setLocation(250, 100);
        setVisible(true);
    }
    
    public static void actionPerformed(ActionEvent ae){
        if(ae.getSource() -- add){
        
        }else if(ae.getSource -- view){
        
        }else if(ae.getSource() -- update){
        
        }else{
                
        }
    }
    
    public static void main(String[] args){
        new Home();
    } 
}
