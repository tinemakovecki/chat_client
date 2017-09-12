package ChitChat;


import javax.swing.JTextArea;
import javax.swing.text.AbstractDocument;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

public class ParrotBot {
	// declaring the variables
	@SuppressWarnings("unused")
	private JTextArea outputField;
	private Document outputDocument;
	private String parrotName;
	
	// function to create a ParrotBot for given field
	public ParrotBot(JTextArea outputField, String parrot) {
		this.outputField = outputField;
		this.outputDocument = outputField.getDocument();
		this.parrotName = parrot;
	}
	
	// function to activate a ParrotBot
	public void speakUp() {
		((AbstractDocument) this.outputDocument).setDocumentFilter(new DocumentParrotFilter(parrotName));
	}
	
	// function to stop a ParrotBot
	public void shutUp() {
		if (this.outputDocument != null) {
			((AbstractDocument) this.outputDocument).setDocumentFilter(new DocumentFilter());
		}
	}
	
	// function to convert message to all caps
	public static String shout(String originalMessage) {
		return originalMessage.toUpperCase();
	}
	
	public String getParrotName() {
		return parrotName;
	}

	public void setParrotName(String parrotName) {
		this.parrotName = parrotName;
	}
	
}
