package ChitChat;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.ListIterator;
import java.util.Timer;
import java.util.TimerTask;

public class Inbox extends TimerTask {
	private ChatInterface chat;
	private List<Message> received;
	private Timer timer;

	public Inbox(ChatInterface chat) {
		this.chat = chat;
	}
	
	// start running the inbox
	public void activate() {
		this.timer = new Timer();
		timer.scheduleAtFixedRate(this, 1000, 1000);
	}
	
	// stop and reset inbox
	public void stop() {
		timer.cancel();
		timer.purge();
		this.timer = null;
	}
	
	@Override
	public void run() {
		// collecting received messages
		String nickname = chat.getNicknameField().getText();
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
				chat.addMessage(message);
			}
		}
	}
	
}
