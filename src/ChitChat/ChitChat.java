package ChitChat;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;

public class ChitChat {

	public static void main(String[] args) throws ClientProtocolException, IOException, URISyntaxException {
		ChatFrame chatFrame = new ChatFrame();
		// PrimeRobot robot = new PrimeRobot(chatFrame);
		// robot.activate();
		ParrotBot parrot = new ParrotBot(chatFrame.getOutput(), "Jeff");
		chatFrame.pack();
		chatFrame.giveInputFocus();
		chatFrame.setVisible(true);
		
	}

}
