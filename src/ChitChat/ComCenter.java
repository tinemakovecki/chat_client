package ChitChat;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;

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
	public static void get (String address) { // TODO move function to ChatFrame
    	try {
            String hello = Request.Get(address)
                                  .execute()
                                  .returnContent().asString();
            System.out.println(hello);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
