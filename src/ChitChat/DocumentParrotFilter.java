package ChitChat;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class DocumentParrotFilter extends DocumentFilter {
	private String parrotName;
	
	public DocumentParrotFilter (String parrot) {
		this.parrotName = parrot;
	}
	
	// overrides the insert method for inserting text
	public void insertString(FilterBypass fb, int offset,
            				 String newText, AttributeSet a) throws BadLocationException {
		String fullText = newText + "\n" + parrotName + ":" + ParrotBot.shout(newText) + "\n";
		super.insertString(fb, offset, fullText, a);
	}
	
	// overrides the replace method for replacing text
	public void replace(FilterBypass fb, int offset, int end,
           			String text, AttributeSet a) throws BadLocationException {
		String lastLine = getTextAfter("\n", text);
		String parrotLine = ParrotBot.shout(getTextAfter(":", lastLine));
		String fullText = text + "\n" + parrotName + ":" + parrotLine;
		super.replace(fb, offset, end, fullText, a);
	} 
	
	// gives slice of string after last appearance of selected character/divider
	public String getTextAfter (String divider, String text) {
		String lastSlice = text.substring(text.lastIndexOf(divider) + 1);
		return lastSlice;
	}
}
