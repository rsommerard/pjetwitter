package ihm;

import helper.Globals;
import helper.Utils;
import helper.csv.CsvHelper;
import helper.csv.CsvSingletons;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JScrollPane;

import pjetwitter.PJETwitter;
import pjetwitter.TweetInfo;
import pjetwitter.classifier.BayesienneBiFrequenceClassifier;
import pjetwitter.classifier.BayesienneBiPresenceClassifier;
import pjetwitter.classifier.BayesienneUniBiFrequenceClassifier;
import pjetwitter.classifier.BayesienneUniBiPresenceClassifier;
import pjetwitter.classifier.BayesienneUniFrequenceClassifier;
import pjetwitter.classifier.BayesienneUniPresenceClassifier;
import pjetwitter.classifier.KNNClassifier;
import pjetwitter.classifier.KeywordsClassifier;
import stats.CrossValidationStats;
import stats.charts.RatioSearchChart;
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
	
	private JMenuBar menuBar;
	
	private JMenu mnPjetwitter;
	private JMenu mnRequest;
	private JMenu mnProxy;
	private JMenu mnClassifier;
	
	private JMenuItem mntmExit;
	private JMenuItem mntmRequestProperties;
	private JMenuItem mntmProxyProperties;
	private JMenuItem mntmClassifierShowStats;
	
	private JCheckBoxMenuItem chckbxmntmUseProxy;
	private JCheckBoxMenuItem chckbxmntmKeywordsClassifier;
	private JCheckBoxMenuItem chckbxmntmKNNClassifier;
	private JCheckBoxMenuItem chckbxmntmBayesienneUniPresenceClassifier;
	private JCheckBoxMenuItem chckbxmntmBayesienneBiPresenceClassifier;
	private JCheckBoxMenuItem chckbxmntmBayesienneUniBiPresenceClassifier;
	private JCheckBoxMenuItem chckbxmntmBayesienneUniFrequenceClassifier;
	private JCheckBoxMenuItem chckbxmntmBayesienneBiFrequenceClassifier;
	private JCheckBoxMenuItem chckbxmntmBayesienneUniBiFrequenceClassifier;
	private JCheckBoxMenuItem chckbxmntmEnableSave;
	
	private JSeparator separatorProxy;
	private JSeparator separatorClassifier;
	private JSeparator separatorBisClassifier;
	
	private JPanel panelSearchSave;
	private JPanel panelSearch;
	private JPanel panelSave;
	private JPanel panelTweetDetails;
	private JPanel panelTweets;
	
	private JButton btnSearch;
	private JButton btnSave;
	
	private JScrollPane scrollPaneTweets;
	
	private Map<Long, TweetInfo> tweets;
	private List<TweetInfo> tweetInfoList;
	
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
		
		for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
		{
			if ("Mac OS X".equals(info.getName()))
			{
				UIManager.setLookAndFeel(info.getClassName());
				break;
			}
		}
		
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
		this.tweetInfoList = new ArrayList<TweetInfo>();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.frame = new JFrame();
		this.frame.setBounds(100, 100, 650, 500);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.menuBar = new JMenuBar();
		this.frame.setJMenuBar(this.menuBar);
		
		this.mnPjetwitter = new JMenu("PJETwitter");
		this.menuBar.add(this.mnPjetwitter);
		
		this.mntmExit = new JMenuItem("Exit");
		this.mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionPerformedMntmExit(e);
			}
		});
		this.mnPjetwitter.add(this.mntmExit);
		
		this.mnRequest = new JMenu("Request");
		this.menuBar.add(this.mnRequest);
		
		this.mnClassifier = new JMenu("Classifier");
		this.menuBar.add(this.mnClassifier);
		
		this.chckbxmntmKeywordsClassifier = new JCheckBoxMenuItem("Keywords");
		this.mnClassifier.add(this.chckbxmntmKeywordsClassifier);
		this.chckbxmntmKeywordsClassifier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionPerformedChckbxmntmKeywordsClassifier(e);
			}
		});
		
		this.chckbxmntmKNNClassifier = new JCheckBoxMenuItem("KNN");
		this.mnClassifier.add(this.chckbxmntmKNNClassifier);
		this.chckbxmntmKNNClassifier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionPerformedChckbxmntmKNNClassifier(e);
			}
		});
		
		this.chckbxmntmBayesienneUniPresenceClassifier = new JCheckBoxMenuItem("Bayesienne Uni Presence");
		this.mnClassifier.add(this.chckbxmntmBayesienneUniPresenceClassifier);
		this.chckbxmntmBayesienneUniPresenceClassifier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionPerformedChckbxmntmBayesienneUniPresenceClassifier(e);
			}
		});
		
		this.chckbxmntmBayesienneUniFrequenceClassifier = new JCheckBoxMenuItem("Bayesienne Uni Frequence");
		this.mnClassifier.add(this.chckbxmntmBayesienneUniFrequenceClassifier);
		this.chckbxmntmBayesienneUniFrequenceClassifier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionPerformedChckbxmntmBayesienneUniFrequenceClassifier(e);
			}
		});
		
		this.chckbxmntmBayesienneBiPresenceClassifier = new JCheckBoxMenuItem("Bayesienne Bi Presence");
		this.mnClassifier.add(this.chckbxmntmBayesienneBiPresenceClassifier);
		this.chckbxmntmBayesienneBiPresenceClassifier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionPerformedChckbxmntmBayesienneBiPresenceClassifier(e);
			}
		});
		
		this.chckbxmntmBayesienneBiFrequenceClassifier = new JCheckBoxMenuItem("Bayesienne Bi Frequence");
		this.mnClassifier.add(this.chckbxmntmBayesienneBiFrequenceClassifier);
		this.chckbxmntmBayesienneBiFrequenceClassifier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionPerformedChckbxmntmBayesienneBiFrequenceClassifier(e);
			}
		});
		
		this.chckbxmntmBayesienneUniBiPresenceClassifier = new JCheckBoxMenuItem("Bayesienne Uni+Bi Presence");
		this.mnClassifier.add(this.chckbxmntmBayesienneUniBiPresenceClassifier);
		this.chckbxmntmBayesienneUniBiPresenceClassifier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionPerformedChckbxmntmBayesienneUniBiPresenceClassifier(e);
			}
		});
		
		this.chckbxmntmBayesienneUniBiFrequenceClassifier = new JCheckBoxMenuItem("Bayesienne Uni+Bi Frequence");
		this.mnClassifier.add(this.chckbxmntmBayesienneUniBiFrequenceClassifier);
		this.chckbxmntmBayesienneUniBiFrequenceClassifier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionPerformedChckbxmntmBayesienneUniBiFrequenceClassifier(e);
			}
		});
		
		this.separatorClassifier = new JSeparator();
		this.mnClassifier.add(this.separatorClassifier);
		
		this.chckbxmntmEnableSave = new JCheckBoxMenuItem("Enable save");
		this.chckbxmntmEnableSave.setState(false);
		this.mnClassifier.add(this.chckbxmntmEnableSave);
		this.chckbxmntmEnableSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionPerformedChckbxmntmEnableSave(e);
			}
		});
		
		this.separatorBisClassifier = new JSeparator();
		this.mnClassifier.add(this.separatorBisClassifier);
		
		this.mntmClassifierShowStats = new JMenuItem("Show Stats");
		this.mnClassifier.add(this.mntmClassifierShowStats);
		this.mntmClassifierShowStats.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionPerformedMntmClassifierShowStats(e);
			}
		});
		
		this.mntmRequestProperties = new JMenuItem("Properties");
		this.mntmRequestProperties.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionPerformedMntmRequestProperties(e);
			}
		});
		this.mnRequest.add(this.mntmRequestProperties);
		
		this.mnProxy = new JMenu("Proxy");
		this.menuBar.add(this.mnProxy);
		
		this.chckbxmntmUseProxy = new JCheckBoxMenuItem("Use Proxy");
		this.chckbxmntmUseProxy.setState(!this.chckbxmntmUseProxy.getState());
		this.pjeTwitter.enableProxy();
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
		this.btnSave.setEnabled(false);
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
				new String[] {Globals.NON_ANNOTATED_TWEET_STR, Globals.NEGATIVE_TWEET_STR, Globals.NEUTRAL_TWEET_STR, Globals.POSITIVE_TWEET_STR}));
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

			TweetInfo tweet = this.tweets.get(listTweets.getSelectedValue());


			if (item.toString().equals(Globals.NEGATIVE_TWEET_STR))
				tweet.setTweetPolarity(Globals.NEGATIVE_TWEET);
			else if (item.toString().equals(Globals.NEUTRAL_TWEET_STR))
				tweet.setTweetPolarity(Globals.NEUTRAL_TWEET);
			else if (item.toString().equals(Globals.POSITIVE_TWEET_STR))
				tweet.setTweetPolarity(Globals.POSITIVE_TWEET);
			else
				tweet.setTweetPolarity(Globals.NON_ANNOTATED_TWEET);

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
		this.tweetInfoList.clear();
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
			if(!tweet.getTweetText().startsWith("RT"))
				this.tweetInfoList.add(tweet);
		}
		
		this.panelTweets.setBorder(new TitledBorder(null, "Tweets " + this.tweetInfoList.size(), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		List<TweetInfo> tweetInfoIntern = new ArrayList<TweetInfo>();
		for(TweetInfo tweet : this.tweetInfoList) {
			tweetInfoIntern.add((TweetInfo)tweet.clone());
		}
		
		if(this.chckbxmntmKeywordsClassifier.getState()) {
			KeywordsClassifier classifier = new KeywordsClassifier();

			for(TweetInfo tweet : tweetInfoIntern) {
				tweet.setTweetText(Utils.cleanTweet(tweet.getTweetText()));
				tweet.setTweetPolarity(classifier.classify(tweet.getTweetText()));
			}
			
			new RatioSearchChart(tweetInfoIntern, this.txtSearch.getText());
		}
		else if(this.chckbxmntmKNNClassifier.getState()) {
			CsvHelper csvReference =  CsvSingletons.getInstance().referenceCsv;
			
			KNNClassifier classifier = new KNNClassifier(csvReference.readAll());

			for(TweetInfo tweet : tweetInfoIntern) {
				tweet.setTweetText(Utils.cleanTweet(tweet.getTweetText()));
				tweet.setTweetPolarity(classifier.classify(tweet.getTweetText()));
			}
			
			new RatioSearchChart(tweetInfoIntern, this.txtSearch.getText());
		}
		else if(this.chckbxmntmBayesienneUniPresenceClassifier.getState()) {
			CsvHelper csvReference =  CsvSingletons.getInstance().referenceCsv;
			
			BayesienneUniPresenceClassifier classifier = new BayesienneUniPresenceClassifier(csvReference.readAll());

			for(TweetInfo tweet : tweetInfoIntern) {
				tweet.setTweetText(Utils.cleanTweet(tweet.getTweetText()));
				tweet.setTweetPolarity(classifier.classify(tweet.getTweetText()));
			}
			
			new RatioSearchChart(tweetInfoIntern, this.txtSearch.getText());
		}
		else if(this.chckbxmntmBayesienneUniFrequenceClassifier.getState()) {
			CsvHelper csvReference =  CsvSingletons.getInstance().referenceCsv;
			
			BayesienneUniFrequenceClassifier classifier = new BayesienneUniFrequenceClassifier(csvReference.readAll());

			for(TweetInfo tweet : tweetInfoIntern) {
				tweet.setTweetText(Utils.cleanTweet(tweet.getTweetText()));
				tweet.setTweetPolarity(classifier.classify(tweet.getTweetText()));
			}
			
			new RatioSearchChart(tweetInfoIntern, this.txtSearch.getText());
		}
		else if(this.chckbxmntmBayesienneBiPresenceClassifier.getState()) {
			CsvHelper csvReference =  CsvSingletons.getInstance().referenceCsv;
			
			BayesienneBiPresenceClassifier classifier = new BayesienneBiPresenceClassifier(csvReference.readAll());

			for(TweetInfo tweet : tweetInfoIntern) {
				tweet.setTweetText(Utils.cleanTweet(tweet.getTweetText()));
				tweet.setTweetPolarity(classifier.classify(tweet.getTweetText()));
			}
			
			new RatioSearchChart(tweetInfoIntern, this.txtSearch.getText());
		}
		else if(this.chckbxmntmBayesienneBiFrequenceClassifier.getState()) {
			CsvHelper csvReference =  CsvSingletons.getInstance().referenceCsv;
			
			BayesienneBiFrequenceClassifier classifier = new BayesienneBiFrequenceClassifier(csvReference.readAll());

			for(TweetInfo tweet : tweetInfoIntern) {
				tweet.setTweetText(Utils.cleanTweet(tweet.getTweetText()));
				tweet.setTweetPolarity(classifier.classify(tweet.getTweetText()));
			}
			
			new RatioSearchChart(tweetInfoIntern, this.txtSearch.getText());
		}
		else if(this.chckbxmntmBayesienneUniBiPresenceClassifier.getState()) {
			CsvHelper csvReference =  CsvSingletons.getInstance().referenceCsv;
			
			BayesienneUniBiPresenceClassifier classifier = new BayesienneUniBiPresenceClassifier(csvReference.readAll());

			for(TweetInfo tweet : tweetInfoIntern) {
				tweet.setTweetText(Utils.cleanTweet(tweet.getTweetText()));
				tweet.setTweetPolarity(classifier.classify(tweet.getTweetText()));
			}
			
			new RatioSearchChart(tweetInfoIntern, this.txtSearch.getText());
		}
		else if(this.chckbxmntmBayesienneUniBiFrequenceClassifier.getState()) {
			CsvHelper csvReference =  CsvSingletons.getInstance().referenceCsv;
			
			BayesienneUniBiFrequenceClassifier classifier = new BayesienneUniBiFrequenceClassifier(csvReference.readAll());

			for(TweetInfo tweet : tweetInfoIntern) {
				tweet.setTweetText(Utils.cleanTweet(tweet.getTweetText()));
				tweet.setTweetPolarity(classifier.classify(tweet.getTweetText()));
			}
			
			new RatioSearchChart(tweetInfoIntern, this.txtSearch.getText());
		}
		else {
			Utils.removeAlreadyAnnotatedTweetsFrom(tweetInfoIntern);
		}
		
		this.btnSave.setEnabled(this.chckbxmntmEnableSave.getState());
		
		int i = 0;
		for(TweetInfo tweet : tweetInfoIntern) {
			this.tweetInfoList.get(i++).setTweetPolarity(tweet.getTweetPolarity());
		}
		
		for(TweetInfo tweet : this.tweetInfoList) {
			this.tweets.put(tweet.getTweetID(), tweet);
			model.addElement(tweet.getTweetID());
		}
		
		System.out.println(this.tweets.size());

		this.listTweets.setModel(model);
		
		this.listTweets.setSelectedIndex(0);
	}
	
	private void actionPerformedBtnSave(ActionEvent e)
	{
		if (this.tweets.size() == 0 || listTweets.getModel().getSize() == 0 || this.tweets.size() != listTweets.getModel().getSize())
		{
			JOptionPane.showMessageDialog(this.frame, "Cannot save.", "Error", JOptionPane.ERROR_MESSAGE);
			System.out.println("[ERR] Cannot save tweets");
			return;
		}

		CsvHelper csvReference =  CsvSingletons.getInstance().referenceCsv;

		Set<Entry<Long, TweetInfo>> set = this.tweets.entrySet();
		for (Entry<Long, TweetInfo> entry : set)
		{
			TweetInfo tweet = entry.getValue();
			if (tweet.getTweetPolarity() != Globals.NON_ANNOTATED_TWEET)
			{
				csvReference.write(tweet, true);
			}
		}

		JOptionPane.showMessageDialog(this.frame, "Save success.", "Success", JOptionPane.INFORMATION_MESSAGE);
		
	}
	
	private void valueChangedListTweets(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) {
			JList list = (JList)e.getSource();

			TweetInfo tweet = this.tweets.get(list.getSelectedValue());
			
			if(tweet == null) {
				return;
			}
			
			this.lblTweetIdValue.setText(String.valueOf(tweet.getTweetID()));
			this.lblTweetPublisherValue.setText(tweet.getTweetPublisher());
			this.lblTweetDateValue.setText(tweet.getTweetDate().toString());
			this.textAreaTweetMessage.setText(tweet.getTweetText());
			
			if (tweet.getTweetPolarity() == Globals.POSITIVE_TWEET)
			{
				this.comboBoxTweetPolarity.setSelectedItem(Globals.POSITIVE_TWEET_STR);
			}
			else if (tweet.getTweetPolarity() == Globals.NEGATIVE_TWEET)
			{
				this.comboBoxTweetPolarity.setSelectedItem(Globals.NEGATIVE_TWEET_STR);
			}
			else if (tweet.getTweetPolarity() == Globals.NEUTRAL_TWEET)
			{
				this.comboBoxTweetPolarity.setSelectedItem(Globals.NEUTRAL_TWEET_STR);
			}
			else
			{
				this.comboBoxTweetPolarity.setSelectedItem(Globals.NON_ANNOTATED_TWEET_STR);
			}
	    }
	}
	
	private void actionPerformedChckbxmntmKeywordsClassifier(ActionEvent e) {
		this.chckbxmntmKNNClassifier.setSelected(false);
		this.chckbxmntmBayesienneUniPresenceClassifier.setSelected(false);
		this.chckbxmntmBayesienneUniFrequenceClassifier.setSelected(false);
		this.chckbxmntmBayesienneBiPresenceClassifier.setSelected(false);
		this.chckbxmntmBayesienneBiFrequenceClassifier.setSelected(false);
		this.chckbxmntmBayesienneUniBiPresenceClassifier.setSelected(false);
		this.chckbxmntmBayesienneUniBiFrequenceClassifier.setSelected(false);
	}
	
	private void actionPerformedChckbxmntmKNNClassifier(ActionEvent e) {
		this.chckbxmntmKeywordsClassifier.setSelected(false);
		this.chckbxmntmBayesienneUniPresenceClassifier.setSelected(false);
		this.chckbxmntmBayesienneUniFrequenceClassifier.setSelected(false);
		this.chckbxmntmBayesienneBiPresenceClassifier.setSelected(false);
		this.chckbxmntmBayesienneBiFrequenceClassifier.setSelected(false);
		this.chckbxmntmBayesienneUniBiPresenceClassifier.setSelected(false);
		this.chckbxmntmBayesienneUniBiFrequenceClassifier.setSelected(false);
	}
	
	private void actionPerformedChckbxmntmBayesienneUniPresenceClassifier(ActionEvent e) {
		this.chckbxmntmKeywordsClassifier.setSelected(false);
		this.chckbxmntmKNNClassifier.setSelected(false);
		this.chckbxmntmBayesienneUniFrequenceClassifier.setSelected(false);
		this.chckbxmntmBayesienneBiPresenceClassifier.setSelected(false);
		this.chckbxmntmBayesienneBiFrequenceClassifier.setSelected(false);
		this.chckbxmntmBayesienneUniBiPresenceClassifier.setSelected(false);
		this.chckbxmntmBayesienneUniBiFrequenceClassifier.setSelected(false);
	}
	
	private void actionPerformedChckbxmntmBayesienneUniFrequenceClassifier(ActionEvent e) {
		this.chckbxmntmKeywordsClassifier.setSelected(false);
		this.chckbxmntmKNNClassifier.setSelected(false);
		this.chckbxmntmBayesienneUniPresenceClassifier.setSelected(false);
		this.chckbxmntmBayesienneBiPresenceClassifier.setSelected(false);
		this.chckbxmntmBayesienneBiFrequenceClassifier.setSelected(false);
		this.chckbxmntmBayesienneUniBiPresenceClassifier.setSelected(false);
		this.chckbxmntmBayesienneUniBiFrequenceClassifier.setSelected(false);
	}
	
	private void actionPerformedChckbxmntmBayesienneBiPresenceClassifier(ActionEvent e) {
		this.chckbxmntmKeywordsClassifier.setSelected(false);
		this.chckbxmntmKNNClassifier.setSelected(false);
		this.chckbxmntmBayesienneUniPresenceClassifier.setSelected(false);
		this.chckbxmntmBayesienneUniFrequenceClassifier.setSelected(false);
		this.chckbxmntmBayesienneBiFrequenceClassifier.setSelected(false);
		this.chckbxmntmBayesienneUniBiPresenceClassifier.setSelected(false);
		this.chckbxmntmBayesienneUniBiFrequenceClassifier.setSelected(false);
	}
	
	private void actionPerformedChckbxmntmBayesienneBiFrequenceClassifier(ActionEvent e) {
		this.chckbxmntmKeywordsClassifier.setSelected(false);
		this.chckbxmntmKNNClassifier.setSelected(false);
		this.chckbxmntmBayesienneUniPresenceClassifier.setSelected(false);
		this.chckbxmntmBayesienneUniFrequenceClassifier.setSelected(false);
		this.chckbxmntmBayesienneBiPresenceClassifier.setSelected(false);
		this.chckbxmntmBayesienneUniBiPresenceClassifier.setSelected(false);
		this.chckbxmntmBayesienneUniBiFrequenceClassifier.setSelected(false);
	}
	
	private void actionPerformedChckbxmntmBayesienneUniBiPresenceClassifier(ActionEvent e) {
		this.chckbxmntmKeywordsClassifier.setSelected(false);
		this.chckbxmntmKNNClassifier.setSelected(false);
		this.chckbxmntmBayesienneUniPresenceClassifier.setSelected(false);
		this.chckbxmntmBayesienneUniFrequenceClassifier.setSelected(false);
		this.chckbxmntmBayesienneBiPresenceClassifier.setSelected(false);
		this.chckbxmntmBayesienneBiFrequenceClassifier.setSelected(false);
		this.chckbxmntmBayesienneUniBiFrequenceClassifier.setSelected(false);
	}
	
	private void actionPerformedChckbxmntmBayesienneUniBiFrequenceClassifier(ActionEvent e) {
		this.chckbxmntmKeywordsClassifier.setSelected(false);
		this.chckbxmntmKNNClassifier.setSelected(false);
		this.chckbxmntmBayesienneUniPresenceClassifier.setSelected(false);
		this.chckbxmntmBayesienneUniFrequenceClassifier.setSelected(false);
		this.chckbxmntmBayesienneBiPresenceClassifier.setSelected(false);
		this.chckbxmntmBayesienneBiFrequenceClassifier.setSelected(false);
		this.chckbxmntmBayesienneUniBiPresenceClassifier.setSelected(false);
	}
	
	private void actionPerformedChckbxmntmEnableSave(ActionEvent e) {
		this.btnSave.setEnabled(this.chckbxmntmEnableSave.getState());
	}
	
	private void actionPerformedMntmClassifierShowStats(ActionEvent e) {
    	new CrossValidationStats().main(null);
	}
}
