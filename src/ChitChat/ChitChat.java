package ChitChat;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;

public class ChitChat {

	public static void main(String[] args) throws ClientProtocolException, IOException, URISyntaxException {
		ChatInterface.launchGUI();
	}
}
