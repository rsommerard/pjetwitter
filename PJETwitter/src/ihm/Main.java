package ihm;

import helper.Constants;
import helper.CsvHelper;

import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.border.TitledBorder;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JScrollPane;

import pjetwitter.KeywordsClassifier;
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
import javax.swing.AbstractListModel;

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
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws Exception {
		for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
		{
			if ("Nimbus".equals(info.getName()))
			{
				UIManager.setLookAndFeel(info.getClassName());
				break;
			}
		}
		
		/*for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
		{
			if ("Mac OS X".equals(info.getName()))
			{
				UIManager.setLookAndFeel(info.getClassName());
				break;
			}
		}*/
		
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
		comboBoxTweetPolarity.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e)
			{
				itemStateChangedComboBoxTweetPolarity(e);				
			}
		});
		this.comboBoxTweetPolarity.setModel(new DefaultComboBoxModel(
				new String[] {Constants.NON_ANNOTATED_TWEET_STR, Constants.NEGATIVE_TWEET_STR, Constants.NEUTRAL_TWEET_STR, Constants.POSITIVE_TWEET_STR}));
		this.comboBoxTweetPolarity.setBounds(115, 134, 140, 50);
		this.panelTweetDetails.add(this.comboBoxTweetPolarity);
		
		this.panelTweets = new JPanel();
		this.panelTweets.setPreferredSize(new Dimension(200, 10));
		this.panelTweets.setBorder(new TitledBorder(null, "Tweets", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.frame.getContentPane().add(this.panelTweets, BorderLayout.WEST);
		
		this.scrollPaneTweets = new JScrollPane();
		this.scrollPaneTweets.setPreferredSize(new Dimension(180, 380));
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
	
	private void itemStateChangedComboBoxTweetPolarity(ItemEvent event)
	{
		if (event.getStateChange() == ItemEvent.SELECTED)
		{
			if (this.tweets.size() == 0 || listTweets.getModel().getSize() == 0 || this.tweets.size() != listTweets.getModel().getSize())
			{
				System.out.println("[WARN] Event called without objects");
				return;
			}
			
			Object item = event.getItem();
			System.out.println("Combobox selected value: " + item.toString());

			TweetInfo tweet = this.tweets.get(listTweets.getSelectedValue());


			if (item.toString().equals(Constants.NEGATIVE_TWEET_STR))
				tweet.setTweetPolarity(Constants.NEGATIVE_TWEET);
			else if (item.toString().equals(Constants.NEUTRAL_TWEET_STR))
				tweet.setTweetPolarity(Constants.NEUTRAL_TWEET);
			else if (item.toString().equals(Constants.POSITIVE_TWEET_STR))
				tweet.setTweetPolarity(Constants.POSITIVE_TWEET);
			else
				tweet.setTweetPolarity(Constants.NON_ANNOTATED_TWEET);

		}
	}
	private void actionPerformedMntmExit(ActionEvent e) {
		System.exit(0);
	}
	
	private void actionPerformedMntmRequestProperties(ActionEvent e) {
		new Request(this.pjeTwitter);
	}
	
	private void actionPerformedChckbxmntmUseProxy(ActionEvent e) {
		if(!this.chckbxmntmUseProxy.getState()) {
			this.pjeTwitter.disableProxy();
		}
		else {
			if(!this.pjeTwitter.enableProxy()) {
				this.chckbxmntmUseProxy.setState(!this.chckbxmntmUseProxy.getState());
				JOptionPane.showMessageDialog(this.frame, "Bad proxy settings.", "Proxy error.", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	private void actionPerformedMntmProxyProperties(ActionEvent e) {
		new Proxy(this.pjeTwitter, this.chckbxmntmUseProxy);
	}
	
	private void actionPerformedBtnSearch(ActionEvent e) {
		if(this.txtSearch.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this.frame, "You need to search a word.", "No Requested word", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		DefaultListModel<Long> model = new DefaultListModel<Long>();
		
		this.tweets.clear();
		model.clear();

		QueryResult result = this.pjeTwitter.search(this.txtSearch.getText());
		
		if(result == null || result.getTweets().size() == 0) {
			JOptionPane.showMessageDialog(this.frame, "No result were found.", "No Result", JOptionPane.ERROR_MESSAGE);
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
		
		//TODO: Exception ici. Recherche 100 puis recherche 1 par ex.

		this.listTweets.setModel(model);
		
		this.listTweets.setSelectedIndex(0);
	}
	
	private void actionPerformedBtnSave(ActionEvent e)
	{
		if (this.tweets.size() == 0 || listTweets.getModel().getSize() == 0 || this.tweets.size() != listTweets.getModel().getSize())
		{
			System.out.println("[ERR] Cannot save tweets");
			return;
		}

		CsvHelper csv = new CsvHelper(Constants.CSV_FINAL_LOCATION, Constants.CSV_DELIMITER);

		Set<Entry<Long, TweetInfo>> set = tweets.entrySet();
		Iterator<Entry<Long, TweetInfo>> i = set.iterator();
		while (i.hasNext())
		{
			Map.Entry me = (Map.Entry) i.next();
			// Write to csv
			TweetInfo tweet = (TweetInfo) me.getValue();
			csv.write(tweet, true);
		}
	}
	
	private void valueChangedListTweets(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) {
			JList list = (JList)e.getSource();
			
			TweetInfo tweet = this.tweets.get(list.getSelectedValue());
			this.lblTweetIdValue.setText(String.valueOf(tweet.getTweetID()));
			this.lblTweetPublisherValue.setText(tweet.getTweetPublisher());
			this.lblTweetDateValue.setText(tweet.getTweetDate().toString());
			this.textAreaTweetMessage.setText(tweet.getTweetText());
			
			if (tweet.getTweetPolarity() == Constants.POSITIVE_TWEET)
			{
				this.comboBoxTweetPolarity.setSelectedItem(Constants.POSITIVE_TWEET_STR);
			}
			else if (tweet.getTweetPolarity() == Constants.NEGATIVE_TWEET)
			{
				this.comboBoxTweetPolarity.setSelectedItem(Constants.NEGATIVE_TWEET_STR);
			}
			else if (tweet.getTweetPolarity() == Constants.NEUTRAL_TWEET)
			{
				this.comboBoxTweetPolarity.setSelectedItem(Constants.NEUTRAL_TWEET_STR);
			}
			else
			{
				this.comboBoxTweetPolarity.setSelectedItem(Constants.NON_ANNOTATED_TWEET_STR);
			}
	    }
	}
}
