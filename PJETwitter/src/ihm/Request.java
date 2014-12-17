package ihm;

import helper.Globals;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import pjetwitter.PJETwitter;
import twitter4j.Query.ResultType;

public class Request extends JDialog {

	private static final long serialVersionUID = -2302002971665796587L;
	private final JPanel contentPanel = new JPanel();
	private JLabel lblCount;
	private JLabel lblResultType;
	private JComboBox comboBoxResultType;
	private JSpinner spinnerCount;
	private JPanel buttonPane;
	private JButton okButton;
	private JButton cancelButton;
	private JComboBox comboBoxLang;
	private JLabel lblLang;
	
	private PJETwitter pjeTwitter;
	
	public Request(PJETwitter pjeTwitter) {
		this.pjeTwitter = pjeTwitter;
		this.initialize();
	}
	
	private void initialize() {
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("Request Properties");
		this.setBounds(100, 100, 350, 200);
		this.getContentPane().setLayout(new BorderLayout());
		
		this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.getContentPane().add(this.contentPanel, BorderLayout.CENTER);
		this.contentPanel.setLayout(null);
		
		this.lblCount = new JLabel("Count");
		this.lblCount.setBounds(30, 20, 61, 16);
		this.contentPanel.add(this.lblCount);
		
		this.lblResultType = new JLabel("Popularity");
		this.lblResultType.setBounds(30, 52, 70, 16);
		this.contentPanel.add(this.lblResultType);
		
		this.comboBoxResultType = new JComboBox();
		this.comboBoxResultType.setModel(new DefaultComboBoxModel(ResultType.values()));
		this.comboBoxResultType.setSelectedItem(this.pjeTwitter.getResultType());
		this.comboBoxResultType.setBounds(120, 48, 200, 27);
		this.contentPanel.add(this.comboBoxResultType);
		
		SpinnerNumberModel spinnerModel = new SpinnerNumberModel(this.pjeTwitter.getCountResult(), 1, 100, 1);
		this.spinnerCount = new JSpinner(spinnerModel);
		this.spinnerCount.setBounds(193, 14, 55, 28);
		this.contentPanel.add(this.spinnerCount);
		
		this.comboBoxLang = new JComboBox();
		this.comboBoxLang.setModel(new DefaultComboBoxModel(new String[] {"All", "English", "French"}));
		if(this.pjeTwitter.getLang().equals("fr")) {
			this.comboBoxLang.setSelectedItem(Globals.LANG_FR);
		}
		else if(this.pjeTwitter.getLang().equals("en")) {
			this.comboBoxLang.setSelectedItem(Globals.LANG_EN);
		}
		else {
			this.comboBoxLang.setSelectedItem(Globals.LANG_ALL);
		}
		this.comboBoxLang.setBounds(120, 87, 200, 27);
		this.contentPanel.add(this.comboBoxLang);
		
		this.lblLang = new JLabel("Lang");
		this.lblLang.setBounds(30, 91, 61, 16);
		this.contentPanel.add(this.lblLang);
			
		this.buttonPane = new JPanel();
		this.buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		this.getContentPane().add(this.buttonPane, BorderLayout.SOUTH);

		this.okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionPerformedOkButton(e);
			}
		});
		this.okButton.setActionCommand("OK");
		this.buttonPane.add(this.okButton);
		this.getRootPane().setDefaultButton(this.okButton);

		this.cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionPerformedCancelButton(e);
			}
		});
		this.cancelButton.setActionCommand("Cancel");
		this.buttonPane.add(this.cancelButton);

		this.setVisible(true);
	}
	
	private void actionPerformedCancelButton(ActionEvent e) {
		this.dispose();
	}
	
	private void actionPerformedOkButton(ActionEvent e) {
		if(this.comboBoxLang.getSelectedItem().toString().equals(Globals.LANG_FR)) {
			this.pjeTwitter.setLang("fr");
		}
		else if(this.comboBoxLang.getSelectedItem().toString().equals(Globals.LANG_EN)) {
			this.pjeTwitter.setLang("en");
		}
		else {
			this.pjeTwitter.setLang(null);
		}
		this.pjeTwitter.setCountResult((Integer)this.spinnerCount.getValue());
		this.pjeTwitter.setResultType((ResultType)this.comboBoxResultType.getSelectedItem());
		
		this.dispose();
	}
}
