package ChitChat;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.apache.http.client.ClientProtocolException;
import java.awt.Color;

public class ChatInterface {

	private JFrame frmChatClient;
	private JTextField input;
	private JTextArea output;
	private Boolean global;
	private Inbox inbox;
	private ParrotBot parrot;
	private PrimeRobot primeRobo;
	private Boolean isOnline;
	private RenameWindow reWindow;
	private JTextField nicknameField;
	private JTextField recipientField;
	private JPanel statusPanel;
	private StatusBox statBox;

	/**
	 * Launch the application.
	 */
	public static void launchGUI() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatInterface window = new ChatInterface();
					window.frmChatClient.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ChatInterface() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.isOnline = false;
		this.global = false;
		frmChatClient = new JFrame();
		frmChatClient.setTitle("Chat client");
		frmChatClient.setPreferredSize(new Dimension(300, 300));
		frmChatClient.setBounds(100, 100, 450, 300);
		frmChatClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {200, 400, 0};
		gridBagLayout.rowHeights = new int[]{35, 14, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		frmChatClient.getContentPane().setLayout(gridBagLayout);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.weighty = 2.0;
		gbc_scrollPane.weightx = 2.0;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 0;
		frmChatClient.getContentPane().add(scrollPane, gbc_scrollPane);
		
		output = new JTextArea();
		output.setEditable(false);
		scrollPane.setViewportView(output);
		
		statusPanel = new JPanel();
		GridBagConstraints gbc_statusPanel = new GridBagConstraints();
		gbc_statusPanel.weighty = 1.0;
		gbc_statusPanel.weightx = 1.0;
		gbc_statusPanel.gridheight = 2;
		gbc_statusPanel.insets = new Insets(0, 0, 5, 5);
		gbc_statusPanel.fill = GridBagConstraints.BOTH;
		gbc_statusPanel.gridx = 0;
		gbc_statusPanel.gridy = 0;
		frmChatClient.getContentPane().add(statusPanel, gbc_statusPanel);
		GridBagLayout gbl_statusPanel = new GridBagLayout();
		gbl_statusPanel.columnWidths = new int[]{114, 59, 0};
		gbl_statusPanel.rowHeights = new int[]{0, 16, 24, 24, 0, 26, 24, 20, 0, 0, 0};
		gbl_statusPanel.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_statusPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		statusPanel.setLayout(gbl_statusPanel);
		
		JLabel nicknameLabel = new JLabel("nickname:");
		GridBagConstraints gbc_nicknameLabel = new GridBagConstraints();
		gbc_nicknameLabel.anchor = GridBagConstraints.EAST;
		gbc_nicknameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_nicknameLabel.gridx = 0;
		gbc_nicknameLabel.gridy = 1;
		statusPanel.add(nicknameLabel, gbc_nicknameLabel);
		
		nicknameField = new JTextField();
		nicknameField.setEditable(false);
		nicknameField.setText("Tine");
		GridBagConstraints gbc_nicknameField = new GridBagConstraints();
		gbc_nicknameField.anchor = GridBagConstraints.SOUTH;
		gbc_nicknameField.insets = new Insets(0, 0, 5, 0);
		gbc_nicknameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_nicknameField.gridx = 1;
		gbc_nicknameField.gridy = 1;
		statusPanel.add(nicknameField, gbc_nicknameField);
		nicknameField.setColumns(10);
		
		JButton nicknameButton = new JButton("change nickname");
		nicknameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				reWindow = new RenameWindow(nicknameField, output, isOnline, inbox);
				reWindow.setLocationRelativeTo(frmChatClient); 
				reWindow.pack();
				reWindow.setVisible(true);
			}
		});
		GridBagConstraints gbc_nicknameButton = new GridBagConstraints();
		gbc_nicknameButton.gridwidth = 2;
		gbc_nicknameButton.insets = new Insets(0, 0, 5, 0);
		gbc_nicknameButton.gridx = 0;
		gbc_nicknameButton.gridy = 2;
		statusPanel.add(nicknameButton, gbc_nicknameButton);
		
		JLabel lblStatus = new JLabel("status:");
		GridBagConstraints gbc_lblStatus = new GridBagConstraints();
		gbc_lblStatus.anchor = GridBagConstraints.EAST;
		gbc_lblStatus.insets = new Insets(0, 0, 5, 5);
		gbc_lblStatus.gridx = 0;
		gbc_lblStatus.gridy = 4;
		statusPanel.add(lblStatus, gbc_lblStatus);
		
		JLabel onlineOfflineLabel = new JLabel("offline");
		onlineOfflineLabel.setForeground(Color.RED);
		GridBagConstraints gbc_onlineOfflineLabel = new GridBagConstraints();
		gbc_onlineOfflineLabel.insets = new Insets(0, 0, 5, 0);
		gbc_onlineOfflineLabel.gridx = 1;
		gbc_onlineOfflineLabel.gridy = 4;
		statusPanel.add(onlineOfflineLabel, gbc_onlineOfflineLabel);
		
		JButton logButton = new JButton("log in");
		logButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isOnline) {
					onlineOfflineLabel.setText("offline");
					onlineOfflineLabel.setForeground(Color.red);
					logButton.setText("log in");
					
					try {
						logout();
						isOnline = false;
					} catch (IOException | URISyntaxException x) {
						// TODO Auto-generated catch block
						x.printStackTrace();
					}
					
				} else {
					onlineOfflineLabel.setText("online");
					onlineOfflineLabel.setForeground(Color.green);
					logButton.setText("log out");
					
					try {
						login();
						inbox = new Inbox(output, nicknameField);
						inbox.activate();
						isOnline = true;
					} catch (IOException | URISyntaxException x) {
						// TODO Auto-generated catch block
						x.printStackTrace();
					}
				}
			}
		});
		GridBagConstraints gbc_logButton = new GridBagConstraints();
		gbc_logButton.insets = new Insets(0, 0, 5, 0);
		gbc_logButton.gridx = 1;
		gbc_logButton.gridy = 5;
		statusPanel.add(logButton, gbc_logButton);
		
		JButton btnWhosOnlio = new JButton("who's online");
		btnWhosOnlio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// creating a new StatusBox window and editing its settings
				statBox = new StatusBox(recipientField);
				statBox.setLocationRelativeTo(frmChatClient); 
				statBox.pack();
				statBox.setVisible(true);
			}
		});
		GridBagConstraints gbc_btnWhosOnlio = new GridBagConstraints();
		gbc_btnWhosOnlio.insets = new Insets(0, 0, 5, 0);
		gbc_btnWhosOnlio.gridx = 1;
		gbc_btnWhosOnlio.gridy = 8;
		statusPanel.add(btnWhosOnlio, gbc_btnWhosOnlio);
		
		input = new JTextField();
		input.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent key) {
				if (key.getKeyChar() == '\n') {
					String nickname = nicknameField.getText();
					String text = input.getText();
					
					// check if message is global
					if (statBox != null) {
						global = statBox.getGlobal();
					}
					System.out.println("global = " + global);
					
					// sending the typed message to server
					try {
						sendMessage();
					} catch (URISyntaxException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					// adding message to output area an clearing input line
					addMessage(nickname, text);
					input.setText("");
				}
			}
		});
		GridBagConstraints gbc_input = new GridBagConstraints();
		gbc_input.gridheight = 2;
		gbc_input.insets = new Insets(0, 0, 5, 0);
		gbc_input.fill = GridBagConstraints.BOTH;
		gbc_input.gridx = 1;
		gbc_input.gridy = 2;
		frmChatClient.getContentPane().add(input, gbc_input);
		input.setColumns(10);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 0, 5);
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 2;
		frmChatClient.getContentPane().add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{65, 45, 0};
		gbl_panel.rowHeights = new int[]{16, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel recipientLabel = new JLabel("send to:");
		GridBagConstraints gbc_recipientLabel = new GridBagConstraints();
		gbc_recipientLabel.insets = new Insets(0, 0, 0, 5);
		gbc_recipientLabel.anchor = GridBagConstraints.NORTHEAST;
		gbc_recipientLabel.gridx = 0;
		gbc_recipientLabel.gridy = 0;
		panel.add(recipientLabel, gbc_recipientLabel);
		
		recipientField = new JTextField();
		recipientField.setText("Tine");
		GridBagConstraints gbc_recipientField = new GridBagConstraints();
		gbc_recipientField.fill = GridBagConstraints.HORIZONTAL;
		gbc_recipientField.gridx = 1;
		gbc_recipientField.gridy = 0;
		panel.add(recipientField, gbc_recipientField);
		recipientField.setColumns(10);
		
		JMenuBar menuBar = new JMenuBar();
		frmChatClient.setJMenuBar(menuBar);
		
		JMenu optionsMenu = new JMenu("options");
		menuBar.add(optionsMenu);
		
		JMenuItem changeNicknameMenuItem = new JMenuItem("logall");
		changeNicknameMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ComCenter.login("a");
				} catch (IOException | URISyntaxException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					ComCenter.login("b");
				} catch (IOException | URISyntaxException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					ComCenter.login("c");
				} catch (IOException | URISyntaxException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					ComCenter.login("d");
				} catch (IOException | URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		changeNicknameMenuItem.setActionCommand("New Menu Item");
		optionsMenu.add(changeNicknameMenuItem);
		
		JMenu parrotBotMenu = new JMenu("parrot robot");
		optionsMenu.add(parrotBotMenu);
		
		JMenuItem parrotOn = new JMenuItem("turn on");
		parrotOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = "The loudmouth parrot";
				parrot = new ParrotBot(output, name);
				parrot.speakUp();
			}
		});
		parrotBotMenu.add(parrotOn);
		
		JMenuItem parrotOff = new JMenuItem("turn off");
		parrotOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parrot.shutUp();
			}
		});
		parrotBotMenu.add(parrotOff);
		
		JMenu primeRobotMenu = new JMenu("prime robot");
		optionsMenu.add(primeRobotMenu);
		
		JMenuItem primerOn = new JMenuItem("turn on");
		primerOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				primeRobo = new PrimeRobot(output);
				primeRobo.activate();
			}
		});
		primeRobotMenu.add(primerOn);
		
		JMenuItem primerOff = new JMenuItem("turn off");
		primerOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				primeRobo.stop();
			}
		});
		primeRobotMenu.add(primerOff);
	}
	
	
	/*
	 *       ADDITIONAL FUNCTIONS
	 * - end of window builder code -
	 */


	/**
	 * Adds the typed message to the output area
	 * @param person - the person sending the message
	 * @param message - the message content
	 */
	public void addMessage(String person, String message) {
		String chat = output.getText();
		output.setText(chat + "\n" + person + ": " + message);
	}
	
	/**
	 * adds contents of a received message to output area
	 * @param message - the message to be added
	 */
	public void addMessage(Message message) {
		String chat = output.getText();
		String sender = message.getSender();
		String messageText = message.getText();
		output.setText(chat + "\n" + sender + ": " + messageText);
	}
	
	// login for the current user
	public void login() throws ClientProtocolException, IOException, URISyntaxException {
		ComCenter.login(this.nicknameField.getText());
	}
	
	// logout for the current user
	public void logout() throws ClientProtocolException, IOException, URISyntaxException {
		ComCenter.logout(this.nicknameField.getText());
		// there is some inconsistency with inbox management so double calling .stop() is more certain
		if (reWindow != null) {
			if (reWindow.getInbox() != null) {
				reWindow.getInbox().stop();
			}
		}
		if (inbox != null) {
			inbox.stop();
		}
	}
	
	// sends the message with current parameters in interface
	protected void sendMessage() throws ClientProtocolException, URISyntaxException, IOException {
		String nickname = this.nicknameField.getText();
		String recipient = this.recipientField.getText();
		ComCenter.sendMessage(nickname, global, recipient, this.input.getText());
	}
	
	
	/*
	 * GETTERS & SETTERS
	 */
	
	public JTextField getNicknameField() {
		return nicknameField;
	}
	
	public Boolean getIsOnline() {
		return isOnline;
	}
	
	public void setRecipient(String name) {
		recipientField.setText(name);
	}
	
	public JPanel getStatusPanel() {
		return statusPanel;
	}
	
	public void setIsOnline(Boolean isOnline) {
		this.isOnline = isOnline;
	}
}
