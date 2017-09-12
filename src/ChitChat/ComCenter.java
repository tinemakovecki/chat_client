package ChitChat;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

public class ComCenter {

	/**
	 * login into the server for current user
	 * @param username - the user to be logged in
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static void login (String username) throws ClientProtocolException, IOException, URISyntaxException {
    	
		URIBuilder builder = new URIBuilder("http://chitchat.andrej.com/users");
		builder.addParameter("username", username);
		URI uri = new URI(builder.toString());

		String responseBody = Request.Post(uri)
							  .execute()
							  .returnContent()
							  .asString();

		System.out.println(responseBody);
    }
	
	/**
	 * logout from the server for current user
	 * @param username - the user to be logged out
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static void logout (String username) throws URISyntaxException, ClientProtocolException, IOException {

    	URIBuilder builder = new URIBuilder("http://chitchat.andrej.com/users");
		builder.addParameter("username", username);
		URI uri = new URI(builder.toString());

		String responseBody = Request.Delete(uri)
							  .execute()
							  .returnContent()
							  .asString();

		System.out.println(responseBody);
    }
	
	/**
	 * gets information from selected site
	 * @param address - the site source for getting information
	 */
	public static String get (String address) {
		String content = null;
		
    	try {
            content = Request.Get(address)
            		.execute().returnContent()
            		.asString();
            
            System.out.println(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    	
    	return content;
    }
	
	/**
	 * collects queued messages for selected user from server
	 * @param nickname - the user receiving the messages
	 * @return - returns the received messages as a list of Message objects
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static List<Message> recieveMessages(String nickname) throws URISyntaxException, ClientProtocolException, IOException {
		
		URI uri = new URIBuilder("http://chitchat.andrej.com/messages")
				.addParameter("username", nickname)
				.build();

		String jsonReceivedMessages = Request.Get(uri)
		                               .execute()
		                               .returnContent()
		                               .asString();
		
		// transforming the received messages into a list of Message objects
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(new ISO8601DateFormat());
    	
    	TypeReference<List<Message>> t = new TypeReference<List<Message>>() { };
    	List<Message> received = null;
    	received = mapper.readValue(jsonReceivedMessages, t);
    	System.out.println("recieving:" + received);
    	
    	return received;
	}
	
	/**
	 * sends a message with selected parameters to server
	 * @param nickname - the person sending the message
	 * @param global - is the message global
	 * @param recipient - the person the message is sent to
	 * @param text - message content
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static void sendMessage(String nickname, Boolean global, String recipient, String text) throws URISyntaxException, ClientProtocolException, IOException {
		
		URI uri = new URIBuilder("http://chitchat.andrej.com/messages")
		        .addParameter("username", nickname)
		        .build();
		
		if (global) { 
			// sending a global message
			// formatting the message to be sent
			String message = "{ \"global\" :" + global + 
					"\", \"text\" : \"" + text + "\"}";

			String responseBody = Request.Post(uri)
			        .bodyString(message, ContentType.APPLICATION_JSON)
			        .execute()
			        .returnContent()
			        .asString();

			System.out.println("globally sending:" + responseBody);
		} else {
			// sending a private message
			// formatting the message to be sent
			String message = "{ \"global\" :" + global + 
					", \"recipient\" : \"" + recipient + 
					"\", \"text\" : \"" + text + "\"}";

			String responseBody = Request.Post(uri)
					.bodyString(message, ContentType.APPLICATION_JSON)
					.execute()
					.returnContent()
					.asString();

			System.out.println("privately sending:" + responseBody);
		}
	}
}
