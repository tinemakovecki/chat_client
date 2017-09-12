package ChitChat;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.ListIterator;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Inbox extends TimerTask {
	private JTextArea output;
	private List<Message> received;
	private Timer timer;
	private JTextField nicknameField;

	public Inbox(JTextArea output, JTextField nicknameField) {
		this.output = output;
		this.nicknameField = nicknameField;
	}
	
	// start running the inbox
	public void activate() {
		timer = new Timer();
		timer.scheduleAtFixedRate(this, 1000, 1000);
	}
	
	// stop and reset inbox
	public void stop() {
		timer.cancel();
		timer.purge();
	}
	
	@Override
	public void run() {
		// collecting received messages
		String nickname = nicknameField.getText();
		try {
			received = ComCenter.recieveMessages(nickname);
		} catch (URISyntaxException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// adding received messages to output area
		if (received.isEmpty() != true) {
			ListIterator<Message> iterator = received.listIterator();
			while (iterator.hasNext()) {
				Message message = iterator.next();
				addMessage(message);
			}
		}
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
	
}
