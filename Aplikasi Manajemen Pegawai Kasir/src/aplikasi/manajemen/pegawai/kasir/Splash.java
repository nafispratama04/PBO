package aplikasi.manajemen.pegawai.kasir;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Splash extends JFrame implements ActionListener {
    
    Splash() {
        
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        
        JLabel heading = new JLabel("SISTEM MANAJEMEN PEGAWAI KASIR");
        heading.setBounds(110, 5, 1100, 100);
        heading.setFont(new Font("Raleway", Font.BOLD, 50));
        heading.setForeground(Color.BLACK);
        add(heading);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/landing2.png"));
        Image i2 = i1.getImage().getScaledInstance(1100, 700, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(55, 100, 1050, 520);
        add(image);
        
        JButton clickhere = new JButton("CLICK HERE TO CONTINUE");
        clickhere.setBounds(400, 230, 220, 30);
        clickhere.setBackground(Color.YELLOW);
        clickhere.setForeground(Color.BLACK);
        clickhere.addActionListener(this);
        clickhere.setFont(clickhere.getFont().deriveFont(Font.BOLD));
        clickhere.setBorderPainted(false);
        clickhere.setContentAreaFilled(false);
        clickhere.setOpaque(true);
        image.add(clickhere);
        
        
        setSize(1170, 650);
        setLocation(200, 50);
        setVisible(true);
        
        while(true) {
            heading.setVisible(false);
            try {
                Thread.sleep(0);
            } catch (Exception e){
                e.printStackTrace();
            }
            
            heading.setVisible(true);
            try {
                Thread.sleep(500);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    
    public void actionPerformed(ActionEvent ae) {
        setVisible(false);
        new Login().setVisible(true);
    }
    
    public static void main(String args[]) {
        new Splash();
    }
}