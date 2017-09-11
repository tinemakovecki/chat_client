package ChitChat;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.http.client.ClientProtocolException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

@SuppressWarnings("serial")
public class ChatFrame extends JFrame implements ActionListener, KeyListener {
	
	private JTextArea output;
	private JTextField input;
	private JTextField nicknameField;
	private JButton loginButton;
	private JButton logoutButton;
	private JButton statusButton;
	private JButton sendButton;
	private JButton receiveButton;

	public ChatFrame() {
		super();
		this.setTitle("Chat client");
		Container pane = this.getContentPane();
		pane.setLayout(new GridBagLayout());
		
		this.output = new JTextArea(20, 40);
		this.output.setEditable(false);
		
		JScrollPane scrollPane = new JScrollPane(output);
		
		GridBagConstraints outputConstraint = new GridBagConstraints();
		outputConstraint.gridx = 0;
		outputConstraint.gridy = 1;
		outputConstraint.weightx = 1;
		outputConstraint.weighty = 1;
		outputConstraint.fill = GridBagConstraints.BOTH;
		pane.add(scrollPane, outputConstraint);
		
		
		this.input = new JTextField(40);
		
		GridBagConstraints inputConstraint = new GridBagConstraints();
		inputConstraint.gridx = 0;
		inputConstraint.gridy = 2;
		inputConstraint.weightx = 1;
		inputConstraint.weighty = 0;
		inputConstraint.fill = GridBagConstraints.HORIZONTAL;
		pane.add(input, inputConstraint);
		input.addKeyListener(this);
		
		
		String label  = "nickname: ";
		JLabel nicknamePrompt = new JLabel(label);
		this.nicknameField = new JTextField(System.getProperty("user.name"), 20);
		
		FlowLayout nicknameLayout = new FlowLayout(FlowLayout.LEFT);

		JPanel nicknamePanel = new JPanel(nicknameLayout);
		nicknamePanel.add(nicknamePrompt);
		nicknamePanel.add(nicknameField);
		
		GridBagConstraints nicknameConstraint = new GridBagConstraints();
		nicknameConstraint.gridx = 0;
		nicknameConstraint.gridy = 0;
		nicknameConstraint.weightx = 1;
		nicknameConstraint.weighty = 0;
		nicknameConstraint.fill = GridBagConstraints.HORIZONTAL;
		pane.add(nicknamePanel, nicknameConstraint);
		
		// Buttons for server interaction
		this.loginButton = new JButton("login");
		this.logoutButton = new JButton("logout");
		this.statusButton = new JButton("who's online");
		this.sendButton = new JButton("send");
		this.receiveButton = new JButton("receive");
		GridBagConstraints buttonConstraint = new GridBagConstraints();
		buttonConstraint.gridx = 1;
		
		// add login button and equip it with a listener/function
		pane.add(loginButton, buttonConstraint);
		this.loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					login();
				} catch (IOException | URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		// add logout button and equip it with a listener/function
		pane.add(logoutButton, buttonConstraint);
		this.logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					logout();
				} catch (URISyntaxException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		// add status button and equip it with a listener/function
		pane.add(statusButton, buttonConstraint);
		this.statusButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String address = "http://chitchat.andrej.com/users";
				String jsonActiveUsers = ComCenter.get(address);
				
		    	// converting the data from json
				ObjectMapper mapper = new ObjectMapper();
		    	mapper.setDateFormat(new ISO8601DateFormat());
		    	
		    	TypeReference<List<User>> t = new TypeReference<List<User>>() { };
		    	List<User> activeUsers = null;
				try {
					activeUsers = mapper.readValue(jsonActiveUsers, t);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    	System.out.println(activeUsers);
			}
		});
		
		// add send button and equip it with a listener/function
		pane.add(sendButton, buttonConstraint);
		this.sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					sendMessage();
				} catch (URISyntaxException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		// add receive button and equip it with a listener/function
		pane.add(receiveButton, buttonConstraint);
		this.receiveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					recieveMessages();
				} catch (URISyntaxException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}
	

	/*
	 *  LISTENER FUNCTIONS
	 */
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getSource() == this.input) {
			if (e.getKeyChar() == '\n') {
				String nickname = this.nicknameField.getText();
				String text = this.input.getText();
				
				try {
					sendMessage();
				} catch (URISyntaxException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				this.addMessage(nickname, text);
				this.input.setText("");
			}
		}		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	/*
	 * OTHER FUNCTIONS
	 */
	
	/**
	 * @param person - the person sending the message
	 * @param message - the message content
	 */
	public void addMessage(String person, String message) {
		String chat = this.output.getText();
		this.output.setText(chat + "\n" + person + ": " + message);
	}
	
	public void login() throws ClientProtocolException, IOException, URISyntaxException {
		ComCenter.login(this.nicknameField.getText());
	}
	
	public void logout() throws ClientProtocolException, IOException, URISyntaxException {
		ComCenter.logout(this.nicknameField.getText());
	}
	
	protected void sendMessage() throws ClientProtocolException, URISyntaxException, IOException {
		String nickname = this.nicknameField.getText();
		ComCenter.sendMessage(nickname, false, nickname, this.input.getText());
	}
	
	protected void recieveMessages() throws ClientProtocolException, URISyntaxException, IOException {
		ComCenter.recieveMessages(this.nicknameField.getText());
	}
	
	// gives focus in the window to the input field
	public void giveInputFocus() {
		this.input.requestFocusInWindow();
	}
	
	/*
	 * SETTERS & GETTERS
	 */
	
	/**
	 * @return the input
	 */
	public JTextField getInput() {
		return input;
	}
	
	/**
	 * @return the output
	 */
	public JTextArea getOutput() {
		return output;
	}

	/**
	 * @param output the output to set
	 */
	public void setOutput(JTextArea output) {
		this.output = output;
	}
}
