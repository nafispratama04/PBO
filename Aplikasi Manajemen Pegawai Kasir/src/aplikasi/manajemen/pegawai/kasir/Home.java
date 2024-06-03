package aplikasi.manajemen.pegawai.kasir;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Home extends JFrame implements ActionListener {

    JButton view, add, update, remove, addSchedule, viewSchedule, employeeAttendance, employeePerformance, salaryPayment, logout;
    
    Home() {
        
        setLayout(null);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/home_1.png"));
        Image i2 = i1.getImage().getScaledInstance(1120, 630, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0, 0, 1120, 630);
        add(image);
        
        JLabel heading = new JLabel("Employee Management System");
        heading.setBounds(380, 120, 600, 40);
        heading.setFont(new Font("Raleway", Font.BOLD, 32));
        heading.setForeground(Color.WHITE);
        image.add(heading);

        logout = new JButton("Logout");
        logout.setBounds(20, 20, 100, 30);
        logout.addActionListener(this);
        logout.setBackground(Color.YELLOW);
        image.add(logout);
        
        add = new JButton("Add Employee");
        add.setBounds(450, 180, 150, 40);
        add.addActionListener(this);
        image.add(add);
        
        view = new JButton("View Employees");
        view.setBounds(620, 180, 150, 40);
        view.addActionListener(this);
        image.add(view);
        
        update = new JButton("Update Employee");
        update.setBounds(450, 240, 150, 40);
        update.addActionListener(this);
        image.add(update);
        
        remove = new JButton("Remove Employee");
        remove.setBounds(620, 240, 150, 40);
        remove.addActionListener(this);
        image.add(remove);
        
        addSchedule = new JButton("Add Schedule");
        addSchedule.setBounds(450, 300, 150, 40);
        addSchedule.addActionListener(this);
        image.add(addSchedule);
        
        viewSchedule = new JButton("View Schedule");
        viewSchedule.setBounds(620, 300, 150, 40);
        viewSchedule.addActionListener(this);
        image.add(viewSchedule);
        
        employeeAttendance = new JButton("Employee Attendance");
        employeeAttendance.setBounds(450, 360, 150, 40);
        employeeAttendance.addActionListener(this);
        image.add(employeeAttendance);
        
        employeePerformance = new JButton("Employee Performance");
        employeePerformance.setBounds(620, 360, 150, 40);
        employeePerformance.addActionListener(this);
        image.add(employeePerformance);
        
        salaryPayment = new JButton("Salary Payment");
        salaryPayment.setBounds(450, 420, 150, 40);
        salaryPayment.addActionListener(this);
        image.add(salaryPayment);
        
        setSize(1120, 630);
        setLocation(200, 100);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == add) {
            setVisible(false);
            new AddPegawai();
        } else if (ae.getSource() == view) {
            setVisible(false);
            new ViewPegawai();
        } else if (ae.getSource() == update) {
            setVisible(false);
            new UpdateEmployee();
        } else if (ae.getSource() == remove) {
            setVisible(false);
            new RemoveEmployee();
        } else if (ae.getSource() == addSchedule) {
            setVisible(false);
            new AddSchedule();
        } else if (ae.getSource() == viewSchedule) {
            setVisible(false);
            new ViewSchedule();
        } else if (ae.getSource() == employeeAttendance) {
            setVisible(false);
            new EmployeeAttendance();
        } else if (ae.getSource() == employeePerformance) {
            setVisible(false);
            new EmployeePerformance();
        } else if (ae.getSource() == salaryPayment) {
            setVisible(false);
            new SalaryPayment();
        } else if (ae.getSource() == logout) {
            setVisible(false);
            new Login().setVisible(true);
        }
    }
    
    public static void main(String[] args) {
        new Home();
    }
}
