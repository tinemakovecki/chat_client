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

public class ChatInterface {

	private JFrame frmChatClient;
	private JTextField nicknameField;
	private JTextField input;
	private final ButtonGroup onlineStatus = new ButtonGroup();
	private JTextField recipientField;
	private JTextArea output;
	private Boolean global;
	private Inbox inbox;
	private ParrotBot parrot;
	private PrimeRobot primeRobo;
	private JRadioButton login;
	private Boolean isOnline;
	private RenameWindow reWindow;

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
		gridBagLayout.rowHeights = new int[]{35, 14, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		frmChatClient.getContentPane().setLayout(gridBagLayout);
		
		JPanel nicknamePanel = new JPanel();
		nicknamePanel.setAlignmentY(Component.TOP_ALIGNMENT);
		GridBagConstraints gbc_nicknamePanel = new GridBagConstraints();
		gbc_nicknamePanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_nicknamePanel.anchor = GridBagConstraints.NORTH;
		gbc_nicknamePanel.insets = new Insets(0, 0, 5, 5);
		gbc_nicknamePanel.gridx = 0;
		gbc_nicknamePanel.gridy = 0;
		frmChatClient.getContentPane().add(nicknamePanel, gbc_nicknamePanel);
		nicknamePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel nicknameLabel = new JLabel("nickname:");
		nicknamePanel.add(nicknameLabel);
		
		nicknameField = new JTextField(System.getProperty("user.name"));
		nicknameField.setEditable(false);
		nicknameField.setAlignmentX(Component.RIGHT_ALIGNMENT);
		nicknamePanel.add(nicknameField);
		nicknameField.setColumns(10);
		
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
		
		JPanel satusPanel = new JPanel();
		GridBagConstraints gbc_satusPanel = new GridBagConstraints();
		gbc_satusPanel.insets = new Insets(0, 0, 5, 5);
		gbc_satusPanel.fill = GridBagConstraints.BOTH;
		gbc_satusPanel.gridx = 0;
		gbc_satusPanel.gridy = 1;
		frmChatClient.getContentPane().add(satusPanel, gbc_satusPanel);
		
		login = new JRadioButton("online");
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					login();
					inbox = new Inbox(output, nicknameField);
					inbox.activate();
					isOnline = true;
				} catch (IOException | URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		onlineStatus.add(login);
		
		JRadioButton logout = new JRadioButton("offline");
		logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					logout();
					isOnline = false;
				} catch (IOException | URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		logout.setSelected(true);
		onlineStatus.add(logout);
		
		JButton getUsers = new JButton("online users");
		getUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				// creating a new StatusBox window and editing its settings
				StatusBox statBox = new StatusBox(recipientField);
				statBox.setLocationRelativeTo(frmChatClient); 
				statBox.pack();
				statBox.setVisible(true);
			}
		});
		
		JLabel lblStatus = new JLabel("status:");
		
		recipientField = new JTextField(System.getProperty("user.name"));
		recipientField.setColumns(10);
		
		JCheckBox globalCheck = new JCheckBox("send to all");
		globalCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				global = globalCheck.isSelected();
			}
		});
		
		JLabel recipientLabel = new JLabel("send to:");
		GroupLayout gl_satusPanel = new GroupLayout(satusPanel);
		gl_satusPanel.setHorizontalGroup(
			gl_satusPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_satusPanel.createSequentialGroup()
					.addGroup(gl_satusPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_satusPanel.createSequentialGroup()
							.addGap(28)
							.addGroup(gl_satusPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(globalCheck)
								.addComponent(getUsers)
								.addGroup(gl_satusPanel.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblStatus)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_satusPanel.createParallelGroup(Alignment.LEADING)
										.addComponent(login)
										.addComponent(logout)))))
						.addGroup(Alignment.TRAILING, gl_satusPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(recipientLabel)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(recipientField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_satusPanel.setVerticalGroup(
			gl_satusPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_satusPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_satusPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(login)
						.addComponent(lblStatus))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(logout)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(getUsers)
					.addPreferredGap(ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
					.addComponent(globalCheck)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_satusPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(recipientField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(recipientLabel))
					.addContainerGap())
		);
		satusPanel.setLayout(gl_satusPanel);
		
		input = new JTextField();
		input.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent key) {
				if (key.getKeyChar() == '\n') {
					String nickname = nicknameField.getText();
					String text = input.getText();
					
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
		gbc_input.fill = GridBagConstraints.HORIZONTAL;
		gbc_input.gridx = 1;
		gbc_input.gridy = 2;
		frmChatClient.getContentPane().add(input, gbc_input);
		input.setColumns(10);
		
		JMenuBar menuBar = new JMenuBar();
		frmChatClient.setJMenuBar(menuBar);
		
		JMenu optionsMenu = new JMenu("options");
		menuBar.add(optionsMenu);
		
		JMenuItem changeNicknameMenuItem = new JMenuItem("change nickname");
		changeNicknameMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reWindow = new RenameWindow(nicknameField, output, isOnline, inbox);
				reWindow.setLocationRelativeTo(frmChatClient); 
				reWindow.pack();
				reWindow.setVisible(true);
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
		inbox.stop();
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
	
}
