package ChitChat;


import javax.swing.JTextArea;
import javax.swing.text.AbstractDocument;
import javax.swing.text.Document;

public class ParrotBot {
	// TODO add timer and time delay
	// declaring the variables
	private JTextArea outputField;
	private Document outputDocument;
	private String parrotName;
	
	// function to create a ParrotBot for given field
	public ParrotBot(JTextArea outputField, String parrotName) { // TODO popravi klic v .main
		this.outputField = outputField;
		this.outputDocument = outputField.getDocument();
		((AbstractDocument) this.outputDocument).setDocumentFilter(new DocumentParrotFilter());
	}
	
	// function to convert message to all caps
	public static String shout(String originalMessage) {
		return originalMessage.toUpperCase();
	}
	
}
