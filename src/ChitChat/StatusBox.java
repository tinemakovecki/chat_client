package ChitChat;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

@SuppressWarnings("serial")
public class StatusBox extends JFrame {
	private JTextField currentRecipient;
	private Boolean global;
	
	public StatusBox (JTextField recipientField) {
		super();
		this.currentRecipient  = recipientField;
		this.global = false;
		this.setTitle("who's online");
		
		Container pane = this.getContentPane();
		pane.setLayout(new GridBagLayout());
		
		// creating a new Label and adding it to the frame
		JLabel label = new JLabel("online users:");
		GridBagConstraints labelCons = new GridBagConstraints();
		labelCons.gridx = 0;
		labelCons.gridy = 0;
		labelCons.insets = new Insets(0, 5, 0, 10); // spacing around the label
		pane.add(label, labelCons);
				
		// creating the JComboBox
		String[] users = getActiveUsersArray();		
		JComboBox<String> testBox = new JComboBox<String>(users);
		testBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unchecked")
				JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
				String recipient = (String) comboBox.getSelectedItem();
				if (recipient == "everyone") {
					global = true;
				} else {
					global = false;
				}
				currentRecipient.setText(recipient);
			}
		});
		
		// placing the comboBox in the frame
		GridBagConstraints boxCons = new GridBagConstraints();
		boxCons.gridx = 1;
		boxCons.gridy = 0;
		pane.add(testBox, boxCons);
	}
	
	/*
	 *  OTHER FUNCTIONS
	 */
	
	// returns an array of active users on the server
	// the array is used to create a JComboBox with possible recipients
	public String[] getActiveUsersArray() {
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
		
		// organising the user names into an array
		int len = activeUsers.size();
		String[] users = new String[len + 1];
		if (activeUsers.isEmpty() != true) {
			for (int k = 0; k < len; k++) {
				String username = activeUsers.get(k).getUsername();
				users[k] = username;
			}
		}
		users[len] = "everyone";
		
		return users;
	}

	public Boolean getGlobal() {
		return global;
	}
	
	
}
