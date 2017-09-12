package ChitChat;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.http.client.ClientProtocolException;

@SuppressWarnings("serial")
public class RenameWindow extends JFrame {
	private JTextField nicknameField;
	private JTextField newNicknameField;
	private JTextArea output;
	private JButton confirmButton;
	private Inbox inbox;
	
	public RenameWindow(JTextField nicknameField, JTextArea output, Boolean isOnline, Inbox inbox) {
		super();
		this.nicknameField  = nicknameField;
		this.inbox = inbox;
		this.output = output;
		
		Container pane = this.getContentPane();
		pane.setLayout(new GridBagLayout());
		
		// creating a new Label and adding it to the frame
		JLabel label = new JLabel("nickname:");
		GridBagConstraints labelCons = new GridBagConstraints();
		labelCons.gridx = 0;
		labelCons.gridy = 0;
		labelCons.insets = new Insets(0, 5, 0, 10); // spacing around the label
		pane.add(label, labelCons);
		
		// creating a new Label and adding it to the frame
		String oldNickname = nicknameField.getText();
		newNicknameField  = new JTextField(oldNickname, 15);
		GridBagConstraints nicknameCons = new GridBagConstraints();
		nicknameCons.gridx = 1;
		nicknameCons.gridy = 0;
		nicknameCons.insets = new Insets(0, 5, 0, 5); // spacing around the field
		pane.add(newNicknameField, nicknameCons);
		
		// creating a confirmation button
		confirmButton = new JButton("change nickname");
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newNickname = newNicknameField.getText();
				try {
					changeNickname(isOnline, newNickname);
					dispose();
				} catch (IOException | URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		// adding the button to frame
		GridBagConstraints buttonCons = new GridBagConstraints();
		buttonCons.gridx = 2;
		buttonCons.gridy = 0;
		buttonCons.insets = new Insets(0, 5, 0, 5);
		pane.add(confirmButton, buttonCons);
	}


	// a setter with extra precautions to avoid creating errors
	public void changeNickname(Boolean isOnline, String nickname) throws ClientProtocolException, IOException, URISyntaxException {
		// if we have an active inbox we terminate it
		if (isOnline) {
			ComCenter.logout(this.nicknameField.getText());
			inbox.stop();
		}
			
		nicknameField.setText(nickname);
			
		// if the user is online we run a new inbox
		if (isOnline) {
			ComCenter.login(this.nicknameField.getText());
			inbox = new Inbox(output, nicknameField);
			inbox.activate();
		}
	}
	
	public Inbox getInbox() {
		return inbox;
	}
		
}
