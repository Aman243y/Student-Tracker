package conn.karloyar;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class StudentRecord extends JFrame {

	private JPanel contentPane;
	Connection conn;
	private JTable table;
	private JTextField textField;
	private JButton btnInsertData;
	private JButton btnUpdate;
	private JButton btnDelete;
	private JButton btnPrint;
	private JTextField tf1;
	private JTextField tf2;
	private JTextField tf3;
	private JTextField tf4;
	private JLabel lblEmployeeId;
	private JLabel lblFullName;
	private JLabel lblEmail;
	private JLabel lblDepartment;
	private JLabel lblSalary;
	private JLabel lblAddress;
	private JTextField tf5;
	private JTextField tf6;
	private JLabel lblGender;
	private JRadioButton r1,r2,r3;
	private JComboBox comboBox;
	private JTable table_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentRecord frame = new StudentRecord();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public StudentRecord() {
		conn=DbConnection.getConn();
		System.out.println(conn);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1450, 950);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 218, 185));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		table = new JTable();
		table.setBackground(SystemColor.activeCaption);
		table.setBounds(622, 104, 650, 359);
		contentPane.add(table);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String q="Select * from employee where full_name=?";
				try {
					PreparedStatement ps=DbConnection.getConn().prepareStatement(q);
					ps.setString(1,textField.getText() );
					ResultSet rs=ps.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		textField.setBounds(725, 29, 414, 34);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Search here");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(628, 38, 87, 14);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Load");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadData();
				
				
				
			}//action listener ends here
		});
		btnNewButton.setBackground(SystemColor.textHighlight);
		btnNewButton.setBounds(297, 663, 89, 23);
		contentPane.add(btnNewButton);
		
		btnInsertData = new JButton("Insert data");
		btnInsertData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String q="INSERT into employee values(?,?,?,?,?,?,?)";
				JOptionPane.showMessageDialog(null, "Inserting");
				try {
					PreparedStatement ps=conn.prepareStatement(q);
					ps.setInt(1, Integer.parseInt(tf1.getText()));
					ps.setString(2, tf2.getText());
					ps.setString(3, tf3.getText());
					ps.setString(4, tf4.getText());
					ps.setString(5, tf5.getText());
					ps.setString(6, tf6.getText());
					ps.setString(7, getGender() );
					ps.execute();
					loadData();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}

		}
		);
		btnInsertData.setBackground(SystemColor.textHighlight);
		btnInsertData.setBounds(256, 636, 89, 23);
		contentPane.add(btnInsertData);
		
		btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String q="UPDATE employee set full_name=?,email=?,department=?,salary=?,address=?,gender=?where eid=?";
				try {
					PreparedStatement ps=conn.prepareStatement(q);
					ps.setString(1, tf2.getText());
					ps.setString(2, tf3.getText());
					ps.setString(3, tf4.getText());
					ps.setString(4, tf5.getText());
					ps.setString(5, tf6.getText());
					ps.setString(6, getGender());
					ps.setInt(7, Integer.parseInt(tf1.getText()));
					ps.execute();
					JOptionPane.showMessageDialog(null, "Record updated successfully");
					loadData();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnUpdate.setBackground(SystemColor.textHighlight);
		btnUpdate.setBounds(209, 599, 89, 23);
		contentPane.add(btnUpdate);
		
		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String q="Delete from employee where eid=?";
				try {
					PreparedStatement ps=conn.prepareStatement(q);
					ps.setInt(1, Integer.parseInt(tf1.getText()));
					ps.execute();
					JOptionPane.showMessageDialog(null, "Record deleted successfully");
					loadData();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnDelete.setBackground(SystemColor.textHighlight);
		btnDelete.setBounds(162, 565, 89, 23);
		contentPane.add(btnDelete);
		
		btnPrint = new JButton("Print");
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					table.print();
				} catch (PrinterException e1) {
					JOptionPane.showMessageDialog(btnPrint, "check printer connection proper");
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnPrint.setBackground(SystemColor.textHighlight);
		btnPrint.setBounds(117, 531, 89, 23);
		contentPane.add(btnPrint);
		
		tf1 = new JTextField();
		tf1.setBounds(183, 48, 237, 20);
		contentPane.add(tf1);
		tf1.setColumns(10);
		
		tf2 = new JTextField();
		tf2.setColumns(10);
		tf2.setBounds(183, 87, 237, 20);
		contentPane.add(tf2);
		
		tf3 = new JTextField();
		tf3.setColumns(10);
		tf3.setBounds(183, 123, 237, 20);
		contentPane.add(tf3);
		
		tf4 = new JTextField();
		tf4.setColumns(10);
		tf4.setBounds(183, 154, 237, 20);
		contentPane.add(tf4);
		
		lblEmployeeId = new JLabel("Employee ID");
		lblEmployeeId.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblEmployeeId.setBounds(72, 51, 87, 14);
		contentPane.add(lblEmployeeId);
		
		lblFullName = new JLabel("Full Name");
		lblFullName.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblFullName.setBounds(72, 90, 87, 14);
		contentPane.add(lblFullName);
		
		lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblEmail.setBounds(72, 126, 87, 14);
		contentPane.add(lblEmail);
		
		lblDepartment = new JLabel("Department");
		lblDepartment.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblDepartment.setBounds(72, 157, 87, 14);
		contentPane.add(lblDepartment);
		
		lblSalary = new JLabel("Salary");
		lblSalary.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblSalary.setBounds(72, 193, 87, 14);
		contentPane.add(lblSalary);
		
		lblAddress = new JLabel("Address");
		lblAddress.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblAddress.setBounds(72, 218, 87, 14);
		contentPane.add(lblAddress);
		
		tf5 = new JTextField();
		tf5.setColumns(10);
		tf5.setBounds(183, 185, 237, 20);
		contentPane.add(tf5);
		
		tf6 = new JTextField();
		tf6.setColumns(10);
		tf6.setBounds(183, 216, 237, 20);
		contentPane.add(tf6);
		
		lblGender = new JLabel("Gender");
		lblGender.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblGender.setBounds(72, 257, 87, 14);
		contentPane.add(lblGender);
		
		r1 = new JRadioButton("Male");
		r1.setBackground(SystemColor.textHighlight);
		r1.setBounds(183, 254, 68, 23);
		contentPane.add(r1);
		
		r2 = new JRadioButton("Female");
		r2.setBackground(SystemColor.textHighlight);
		r2.setBounds(266, 254, 68, 23);
		contentPane.add(r2);
		
		r3 = new JRadioButton("Others");
		r3.setBackground(SystemColor.textHighlight);
		r3.setBounds(345, 254, 75, 23);
		contentPane.add(r3);
		//radio button group
		ButtonGroup bg=new ButtonGroup();
		bg.add(r1);bg.add(r2);bg.add(r3);
comboBox = new JComboBox();
comboBox.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e) {
		int eid=(int) comboBox.getSelectedIndex();
		System.out.println(eid);
		
		String q="SELECT * from employee where eid=?";
		try {
			PreparedStatement ps= conn.prepareStatement(q);
			ps.setInt(1, eid);
			
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				tf1.setText(String.valueOf(eid));
				tf2.setText(rs.getString(2));
				tf3.setText(rs.getString(3));
				tf4.setText(rs.getString(4));
				tf5.setText(rs.getString(5));
				tf6.setText(rs.getString(6));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
});
comboBox.setBounds(72, 11, 348, 22);
contentPane.add(comboBox);
table_1 = new JTable();
table_1.setModel(new DefaultTableModel(
	new Object[][] {
		{"eid", "full_name", "email", "salary", "department", "address", "gender"},
	},
	new String[] {
		"New column", "New column", "New column", "New column", "New column", "New column", "New column"
	}
));
table_1.setBounds(622, 87, 650, 14);
contentPane.add(table_1);
JButton btnShutdown = new JButton("ShutDown");
btnShutdown.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e) {
		String time=JOptionPane.showInputDialog("enter time in second");
		System.out.println(time);
		try {
			Runtime.getRuntime().exec("shutdown -s -t "+time);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
});
btnShutdown.setBackground(SystemColor.textHighlight);
btnShutdown.setBounds(1234, 565, 89, 23);
contentPane.add(btnShutdown);
fillComboBox();

		
		
		
	}//constructor ends here
	String getGender() {
		String selectedGender;
		if(r1.isSelected()) {
			selectedGender="Male";
		}else if(r2.isSelected()) {
			selectedGender="Female";
		}
		else {
			selectedGender="others";
		}
		
		return selectedGender;
		
	}

	private void loadData() {
		String q="SELECT * from employee";
		try {
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery(q);
			table.setModel(DbUtils.resultSetToTableModel(rs));
			
			
			JOptionPane.showConfirmDialog(null, "loading");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	//fill combobox
	void fillComboBox() {
		String q="Select * from employee";
		try {
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery(q);
			while(rs.next()) {
				comboBox.addItem(rs.getInt(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}//class ends here
