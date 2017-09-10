package ChitChat;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class DocumentParrotFilter extends DocumentFilter {
	
	
	public void insertString(FilterBypass fb, int offset,
            				 String newText, AttributeSet a) throws BadLocationException {
		String fullText = newText + "\n" + "Parrot:" + ParrotBot.shout(newText) + "\n";
		super.insertString(fb, offset, fullText, a);
	}
	
	
	public void replace(FilterBypass fb, int offset, int end,
           			String text, AttributeSet a) throws BadLocationException {
		// TODO specify the dividing character, add a filter for different posters
		String lastLine = getTextAfter("\n", text); // TODO get rid of OP!!!
		String parrotLine = ParrotBot.shout(getTextAfter(":", lastLine));
		String fullText = text + "\n" + "Parrot:" + parrotLine;
		super.replace(fb, offset, end, fullText, a);
	} 
	
	
	public String getTextAfter (String divider, String text) {
		String lastSlice = text.substring(text.lastIndexOf(divider) + 1);
		return lastSlice;
	}
}
