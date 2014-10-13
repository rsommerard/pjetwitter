package ihm;

import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JSeparator;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.border.TitledBorder;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JScrollPane;

import pjetwitter.PJETwitter;
import pjetwitter.TweetInfo;
import twitter4j.QueryResult;
import twitter4j.Status;

import javax.swing.ListSelectionModel;

import javax.swing.JLabel;

import java.awt.FlowLayout;

import javax.swing.ScrollPaneConstants;

import javax.swing.JTextArea;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class Main {

	private JFrame frame;
	
	private JTextField txtSearch;
	
	private JList listTweets;
	
	private JLabel lblTweetId;
	private JLabel lblTweetIdValue;
	private JLabel lblRamainingratelimit;
	private JLabel lblTweetPublisherValue;
	private JLabel lblTweetDateValue;
	private JLabel lblTweetDate;
	private JLabel lblPolarity;
	private JLabel lblTweetPublisher;
	private JLabel lblTweetMessage;
	
	private JTextArea textAreaTweetMessage;
	
	private JComboBox comboBoxTweetPolarity;
	
	private JMenuBar lblRateLimit;
	
	private JMenu mnPjetwitter;
	private JMenu mnRequest;
	private JMenu mnProxy;
	
	private JMenuItem mntmExit;
	private JMenuItem mntmRequestProperties;
	private JMenuItem mntmProxyProperties;
	
	private JCheckBoxMenuItem chckbxmntmUseProxy;
	
	private JSeparator separatorProxy;
	
	private JPanel panelSearchSave;
	private JPanel panelSearch;
	private JPanel panelSave;
	private JPanel panelTweetDetails;
	private JPanel panelTweets;
	
	private JButton btnSearch;
	private JButton btnSave;
	
	private JScrollPane scrollPaneTweets;
	
	private Map<Long, TweetInfo> tweets;
	
	private PJETwitter pjeTwitter;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initializeVariables();
		initialize();
	}
	
	private void initializeVariables() {
		this.pjeTwitter = new PJETwitter();
		this.tweets = new HashMap<Long, TweetInfo>();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.frame = new JFrame();
		this.frame.setBounds(100, 100, 650, 500);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.lblRateLimit = new JMenuBar();
		this.frame.setJMenuBar(this.lblRateLimit);
		
		this.mnPjetwitter = new JMenu("PJETwitter");
		this.lblRateLimit.add(this.mnPjetwitter);
		
		this.mntmExit = new JMenuItem("Exit");
		this.mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionPerformedMntmExit(e);
			}
		});
		this.mnPjetwitter.add(this.mntmExit);
		
		this.mnRequest = new JMenu("Request");
		this.lblRateLimit.add(this.mnRequest);
		
		this.mntmRequestProperties = new JMenuItem("Properties");
		this.mntmRequestProperties.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionPerformedMntmRequestProperties(e);
			}
		});
		this.mnRequest.add(this.mntmRequestProperties);
		
		this.mnProxy = new JMenu("Proxy");
		this.lblRateLimit.add(this.mnProxy);
		
		this.chckbxmntmUseProxy = new JCheckBoxMenuItem("Use Proxy");
		this.chckbxmntmUseProxy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionPerformedChckbxmntmUseProxy(e);
			}
		});
		this.mnProxy.add(this.chckbxmntmUseProxy);
		
		this.separatorProxy = new JSeparator();
		this.mnProxy.add(this.separatorProxy);
		
		this.mntmProxyProperties = new JMenuItem("Properties");
		this.mntmProxyProperties.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionPerformedMntmProxyProperties(e);
			}
		});
		this.mnProxy.add(this.mntmProxyProperties);
		
		this.panelSearchSave = new JPanel();
		this.frame.getContentPane().add(this.panelSearchSave, BorderLayout.NORTH);
		this.panelSearchSave.setLayout(new BorderLayout(0, 0));
		
		this.panelSearch = new JPanel();
		this.panelSearchSave.add(this.panelSearch, BorderLayout.WEST);
		this.panelSearch.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		this.txtSearch = new JTextField();
		this.panelSearch.add(this.txtSearch);
		this.txtSearch.setToolTipText("");
		this.txtSearch.setColumns(25);
		
		this.btnSearch = new JButton("Search");
		this.panelSearch.add(this.btnSearch);
		
		this.lblRamainingratelimit = new JLabel("Rate Limit: 180");
		this.panelSearch.add(this.lblRamainingratelimit);
		this.btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionPerformedBtnSearch(e);
			}
		});
		
		this.panelSave = new JPanel();
		this.panelSearchSave.add(this.panelSave, BorderLayout.EAST);
		
		this.btnSave = new JButton("Save");
		this.panelSave.add(this.btnSave);
		this.btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionPerformedBtnSave(e);
			}
		});
		
		this.panelTweetDetails = new JPanel();
		this.panelTweetDetails.setBorder(new TitledBorder(null, "Tweet Details", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.frame.getContentPane().add(this.panelTweetDetails, BorderLayout.CENTER);
		this.panelTweetDetails.setLayout(null);
		
		this.lblTweetId = new JLabel("ID");
		this.lblTweetId.setBounds(30, 35, 61, 16);
		this.panelTweetDetails.add(this.lblTweetId);
		
		this.lblTweetDate = new JLabel("Date");
		this.lblTweetDate.setBounds(30, 63, 61, 16);
		this.panelTweetDetails.add(this.lblTweetDate);
		
		this.lblTweetPublisher = new JLabel("Publisher");
		this.lblTweetPublisher.setBounds(30, 91, 61, 16);
		this.panelTweetDetails.add(this.lblTweetPublisher);
		
		this.lblPolarity = new JLabel("Polarity");
		this.lblPolarity.setBounds(30, 150, 61, 16);
		this.panelTweetDetails.add(this.lblPolarity);
		
		this.lblTweetMessage = new JLabel("Message");
		this.lblTweetMessage.setBounds(30, 200, 61, 16);
		this.panelTweetDetails.add(this.lblTweetMessage);
		
		this.textAreaTweetMessage = new JTextArea();
		this.textAreaTweetMessage.setWrapStyleWord(true);
		this.textAreaTweetMessage.setDragEnabled(false);
		this.textAreaTweetMessage.setLineWrap(true);
		this.textAreaTweetMessage.setTabSize(4);
		this.textAreaTweetMessage.setEditable(false);
		this.textAreaTweetMessage.setBounds(30, 228, 385, 140);
		this.panelTweetDetails.add(this.textAreaTweetMessage);
		
		this.lblTweetPublisherValue = new JLabel("TweetPublisherValue");
		this.lblTweetPublisherValue.setBounds(115, 91, 300, 16);
		this.panelTweetDetails.add(this.lblTweetPublisherValue);
		
		this.lblTweetDateValue = new JLabel("TweetDateValue");
		this.lblTweetDateValue.setBounds(115, 63, 300, 16);
		this.panelTweetDetails.add(this.lblTweetDateValue);
		
		this.lblTweetIdValue = new JLabel("TweetIdValue");
		this.lblTweetIdValue.setBounds(115, 35, 300, 16);
		this.panelTweetDetails.add(this.lblTweetIdValue);
		
		this.comboBoxTweetPolarity = new JComboBox();
		this.comboBoxTweetPolarity.setModel(new DefaultComboBoxModel(new String[] {"Not Anotated", "Negative", "Neutral", "Positive"}));
		this.comboBoxTweetPolarity.setBounds(115, 134, 140, 50);
		this.panelTweetDetails.add(this.comboBoxTweetPolarity);
		
		this.panelTweets = new JPanel();
		this.panelTweets.setPreferredSize(new Dimension(200, 10));
		this.panelTweets.setBorder(new TitledBorder(null, "Tweets", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.frame.getContentPane().add(this.panelTweets, BorderLayout.WEST);
		
		this.scrollPaneTweets = new JScrollPane();
		this.scrollPaneTweets.setPreferredSize(new Dimension(165, 380));
		this.scrollPaneTweets.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.panelTweets.add(this.scrollPaneTweets);
		
		this.listTweets = new JList();
		this.listTweets.setVisibleRowCount(22);
		this.listTweets.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				valueChangedListTweets(e);
			}
		});
		this.scrollPaneTweets.setViewportView(this.listTweets);
		this.listTweets.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
	private void actionPerformedMntmExit(ActionEvent e) {
		System.exit(0);
	}
	
	private void actionPerformedMntmRequestProperties(ActionEvent e) {
		//TODO
	}
	
	private void actionPerformedChckbxmntmUseProxy(ActionEvent e) {
		//TODO
	}
	
	private void actionPerformedMntmProxyProperties(ActionEvent e) {
		//TODO
	}
	
	private void actionPerformedBtnSearch(ActionEvent e) {
		if(this.txtSearch.getText().isEmpty()) {
			return;
		}
		
		DefaultListModel<Long> model = new DefaultListModel<Long>();
		
		this.tweets.clear();
		model.clear();

		QueryResult result = this.pjeTwitter.search(this.txtSearch.getText());
		
		if(result == null) {
			return;
		}
		
		int rateLimit = result.getRateLimitStatus().getRemaining();
		this.lblRamainingratelimit.setText("Rate Limit: " + Integer.toString(rateLimit));

		List<Status> status = result.getTweets();
		
		for(int i = 0; i < this.pjeTwitter.getCountResult() && i < status.size(); i++) {
			TweetInfo tweet = new TweetInfo(status.get(i), this.txtSearch.getText());
			this.tweets.put(tweet.getTweetID(), tweet);
			model.addElement(tweet.getTweetID());
		}
			
		this.listTweets.setModel(model);
	}
	
	private void actionPerformedBtnSave(ActionEvent e) {
		//TODO
	}
	
	private void valueChangedListTweets(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) {
			JList list = (JList)e.getSource();
			
			TweetInfo tweet = this.tweets.get(list.getSelectedValue());
			this.lblTweetIdValue.setText(String.valueOf(tweet.getTweetID()));
			this.lblTweetPublisherValue.setText(tweet.getTweetPublisher());
			this.lblTweetDateValue.setText(tweet.getTweetDate().toString());
			this.textAreaTweetMessage.setText(tweet.getTweetText());
			
			if(tweet.getTweetPolarity() == -1) {
				this.comboBoxTweetPolarity.setSelectedItem("Not Anotated");
			}
			else if(tweet.getTweetPolarity() == 0) {
				this.comboBoxTweetPolarity.setSelectedItem("Negative");
			}
			else if(tweet.getTweetPolarity() == 1) {
				this.comboBoxTweetPolarity.setSelectedItem("Neutral");
			}
			else {
				this.comboBoxTweetPolarity.setSelectedItem("Positive");
			}
	    }
	}
}
