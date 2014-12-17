package ihm;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import pjetwitter.PJETwitter;

public class Proxy extends JDialog {

	private static final long serialVersionUID = -167064994048281798L;
	private JTextField textFieldHost;
	private JTextField textFieldPort;
	private JTextField textFieldUser;
	private JPasswordField passwordFieldPassword;
	private JPanel buttonPanel;
	private JButton okButton;
	private JButton cancelButton;
	private JPanel contentPanel;
	private JLabel lblHost;
	private JLabel lblPort;
	private JLabel lblUser;
	private JLabel lblPassword;
	private JCheckBoxMenuItem chckbxmntmUseProxy;
	
	private PJETwitter pjeTwitter;

	public Proxy(PJETwitter pjeTwitter, JCheckBoxMenuItem chckbxmntmUseProxy) {
		setResizable(false);
		this.pjeTwitter = pjeTwitter;
		this.chckbxmntmUseProxy = chckbxmntmUseProxy;
		this.initialize();
	}
	
	private void initialize() {
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setTitle("Proxy Properties");
		this.setBounds(100, 100, 350, 220);
		this.getContentPane().setLayout(new BorderLayout());
			
		this.buttonPanel = new JPanel();
		this.buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		this.getContentPane().add(this.buttonPanel, BorderLayout.SOUTH);
		
		this.okButton = new JButton("OK");
		this.okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionPerformedOkButton(e);
			}
		});
		this.okButton.setActionCommand("OK");
		this.buttonPanel.add(this.okButton);
		this.getRootPane().setDefaultButton(this.okButton);
		
		this.cancelButton = new JButton("Cancel");
		this.cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionPerformedCancelButton(e);
			}
		});
		this.cancelButton.setActionCommand("Cancel");
		this.buttonPanel.add(this.cancelButton);
		
		this.contentPanel = new JPanel();
		this.getContentPane().add(this.contentPanel, BorderLayout.CENTER);
		this.contentPanel.setLayout(null);
			
		this.lblHost = new JLabel("Host");
		this.lblHost.setBounds(30, 20, 61, 16);
		this.contentPanel.add(this.lblHost);
			
		this.textFieldHost = new JTextField(this.pjeTwitter.getProxyHost());
		this.textFieldHost.setHorizontalAlignment(SwingConstants.CENTER);
		this.textFieldHost.setBounds(120, 14, 200, 28);
		this.contentPanel.add(this.textFieldHost);
		this.textFieldHost.setColumns(10);
			
		this.lblPort = new JLabel("Port");
		this.lblPort.setBounds(30, 54, 61, 16);
		this.contentPanel.add(this.lblPort);
			
		this.textFieldPort = new JTextField(Integer.toString(this.pjeTwitter.getProxyPort()));
		this.textFieldPort.setHorizontalAlignment(SwingConstants.CENTER);
		this.textFieldPort.setBounds(120, 48, 200, 28);
		this.contentPanel.add(this.textFieldPort);
		this.textFieldPort.setColumns(10);
			
		this.lblUser = new JLabel("User");
		this.lblUser.setBounds(30, 88, 61, 16);
		this.contentPanel.add(this.lblUser);
			
		this.textFieldUser = new JTextField(this.pjeTwitter.getProxyUser());
		this.textFieldUser.setHorizontalAlignment(SwingConstants.CENTER);
		this.textFieldUser.setBounds(120, 82, 200, 28);
		this.contentPanel.add(this.textFieldUser);
		this.textFieldUser.setColumns(10);
			
		this.passwordFieldPassword = new JPasswordField(this.pjeTwitter.getProxyPassword());
		this.passwordFieldPassword.setHorizontalAlignment(SwingConstants.CENTER);
		this.passwordFieldPassword.setBounds(120, 116, 200, 28);
		this.contentPanel.add(this.passwordFieldPassword);
			
		this.lblPassword = new JLabel("Password");
		this.lblPassword.setBounds(30, 122, 61, 16);
		this.contentPanel.add(this.lblPassword);
			
		this.setVisible(true);
	}
	
	private void actionPerformedCancelButton(ActionEvent e) {
		this.dispose();
	}
	
	private void actionPerformedOkButton(ActionEvent e) {
		if(!isValidHost() || !isValidPort()) {
			JOptionPane.showMessageDialog(this, "Bad proxy settings.", "Proxy error.", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if(isValidUser() && isValidPassword()) {
			char[] password = this.passwordFieldPassword.getPassword();
			String passwordString = new String();
			
			for(char c : password) {
				passwordString += c;
			}
			
			this.pjeTwitter.addProxyConfig(this.textFieldHost.getText(), Integer.parseInt(this.textFieldPort.getText()), this.textFieldUser.getText(), passwordString);
		}
		else {
			this.pjeTwitter.addProxyConfig(this.textFieldHost.getText(), Integer.parseInt(this.textFieldPort.getText()));
		}
		
		this.chckbxmntmUseProxy.setState(true);
		this.dispose();
	}
	
	private boolean isValidHost() {
		return !this.textFieldHost.getText().isEmpty();
	}
	
	private boolean isValidPort() {	
		if(this.textFieldPort.getText().isEmpty()) {
			return false;
		}
		
		Pattern pattern = Pattern.compile("\\D");
		Matcher matcher = pattern.matcher(this.textFieldPort.getText());
		if (matcher.find()) {
			return false;
		}
		
		int port = Integer.parseInt(this.textFieldPort.getText());
		
		return (port >= 0) && (port <= 65535);
	}
	
	private boolean isValidUser() {
		return !this.textFieldUser.getText().isEmpty();
	}
	
	private boolean isValidPassword() {
		char[] password = this.passwordFieldPassword.getPassword();
		String passwordString = new String();
		
		for(char c : password) {
			passwordString += c;
		}
		
		return !passwordString.isEmpty();
	}
}
