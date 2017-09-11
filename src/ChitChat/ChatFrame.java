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
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;

public class ChatFrame extends JFrame implements ActionListener, KeyListener {
	
	private JTextArea output;
	private JTextField input;
	private JTextField nicknameField;
	private JButton loginButton;
	private JButton logoutButton;
	private JButton statusButton;

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
		// TODO change functions to use the actual nickname
		this.loginButton = new JButton("login");
		this.logoutButton = new JButton("logout");
		this.statusButton = new JButton("who's online");
		GridBagConstraints buttonConstraint = new GridBagConstraints();
		buttonConstraint.gridx = 1;
		
		// add login button and equip it with a listener/function
		pane.add(loginButton, buttonConstraint);
		this.loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ComCenter.login("me");
				} catch (ClientProtocolException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
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
					ComCenter.logout("me");
				} catch (ClientProtocolException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
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
		    	ComCenter.get(address);
			}
		});
	}

	/**
	 * @param person - the person sending the message
	 * @param message - the message content
	 */
	public void addMessage(String person, String message) {
		String chat = this.output.getText();
		this.output.setText(chat + "\n" + person + ": " + message);
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
				this.addMessage(this.nicknameField.getText(), this.input.getText());
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
	 * SETTER, GETTER AND OTHER FUNCTIONS
	 */
	
	public void giveInputFocus() {
		// gives focus in the window to the input field
		this.input.requestFocusInWindow();
	}
	
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
