package spendingdb;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.Font;
import java.awt.Frame;
import java.awt.SystemColor;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JOptionPane; 

public class form_login extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField password;
	private String username;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					form_login frame = new form_login();
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
	public form_login() {
	setBackground(SystemColor.textHighlight);
	setTitle("Fitness Tracker");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setExtendedState(JFrame.MAXIMIZED_BOTH);
		setBounds(100, 100, 550, 561);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(219, 112, 147));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.setBounds(73, 309, 155, 48);
		contentPane.add(btnRegister);
		btnRegister.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent arg0) {
		        // Assuming your registration code returns a boolean indicating success
		        if (registerUserMethod()) {
		            // If registration is successful, open the tracker window
		            openTrackerWindow(username);
		            dispose();  // Close the registration window
		        } else {
		            // Display an error message if registration fails
		            JOptionPane.showMessageDialog(null, "Registration failed. Please try again.");
		        }
		    }
		});
		
		textField = new JTextField();
		textField.setBounds(204, 176, 265, 28);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblUserName = new JLabel("User name");
		lblUserName.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblUserName.setBounds(36, 181, 139, 14);
		contentPane.add(lblUserName);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPassword.setBounds(36, 233, 139, 14);
		contentPane.add(lblPassword);
		
		JButton btnSubmit = new JButton("login");
		btnSubmit.setIcon(null);
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		        DbLogin obj = new DbLogin();
		        if (obj.checkLogin(textField.getText(), password.getText())) {
		            // If login is successful, open the tracker window
		            openTrackerWindow(username);
		            dispose();  // Close the login window
		        } else {
		            // Display an error message if login fails
		            JOptionPane.showMessageDialog(null, "Invalid username or password. Please try again.");
		        }
		    }
		});
		btnSubmit.setBounds(286, 309, 155, 48);
		contentPane.add(btnSubmit);
		
		password = new JPasswordField();
		password.setBounds(204, 228, 265, 28);
		contentPane.add(password);
		
		JLabel lblPleaseEnterThe = new JLabel("Please enter the user name and password");
		lblPleaseEnterThe.setHorizontalAlignment(SwingConstants.CENTER);
		lblPleaseEnterThe.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		lblPleaseEnterThe.setBounds(22, 72, 472, 38);
		contentPane.add(lblPleaseEnterThe);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBackground(new Color(128, 128, 255));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(128, 128, 255));
		//lblNewLabel.setIcon(new ImageIcon("C:\\Users\\Apple\\Desktop\\Best-Background-1.jpg"));
		lblNewLabel.setBounds(0, 0, 534, 522);
		lblNewLabel.setOpaque(true);
		contentPane.add(lblNewLabel);
	}
	private boolean registerUserMethod() {
	    String username = textField.getText();
	    String userPassword = password.getText();

	    if (DbLogin.registerUser(username, userPassword)) {
	        System.out.println("Registration successful");
	        return true;
	    } else {
	        System.out.println("Registration failed");
	        return false;
	    }
	}
	private void openTrackerWindow(final String username) {
	    EventQueue.invokeLater(new Runnable() {
	        public void run() {
	            try {
	            	SpendingTrackerGui.main(new String[]{username});
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    });
	}
}