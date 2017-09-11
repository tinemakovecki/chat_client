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
	 * 
	 * @param username
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
	 * 
	 * @param username
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
	 * 
	 * @param address
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
	
	// TODO fix return types for send/recieve!!!
	// TODO add correct parameters, connect with GUI
	public static List<Message> recieveMessages(String nickname) throws URISyntaxException, ClientProtocolException, IOException {
		URI uri = new URIBuilder("http://chitchat.andrej.com/messages")
				.addParameter("username", nickname)
				.build();

		String jsonReceivedMessages = Request.Get(uri)
		                               .execute()
		                               .returnContent()
		                               .asString();
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(new ISO8601DateFormat());
    	
    	TypeReference<List<Message>> t = new TypeReference<List<Message>>() { };
    	List<Message> received = null;
    	received = mapper.readValue(jsonReceivedMessages, t);
    	System.out.println("recieving:" + received);
    	
    	return received;
	}
	
	// TODO add variable for recipient/global, decide where to clear input
	public static void sendMessage(String nickname, Boolean global, String recipient, String text) throws URISyntaxException, ClientProtocolException, IOException {
		URI uri = new URIBuilder("http://chitchat.andrej.com/messages")
		        .addParameter("username", nickname)
		        .build();

		String message = "{ \"global\" :" + global + 
				", \"recipient\" : \"" + recipient + 
				"\", \"text\" : \"" + text + "\"}";

		String responseBody = Request.Post(uri)
		        .bodyString(message, ContentType.APPLICATION_JSON)
		        .execute()
		        .returnContent()
		        .asString();

		System.out.println("sending:" + responseBody);
	}
}
