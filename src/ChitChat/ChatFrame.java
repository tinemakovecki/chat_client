package ChitChat;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatFrame extends JFrame implements ActionListener, KeyListener {
	
	private JTextArea output;
	private JTextField input;
	private JTextField nicknameField;

	public ChatFrame() {
		super();
		this.setTitle("wat wat");
		Container pane = this.getContentPane();
		pane.setLayout(new GridBagLayout());
		
		this.output = new JTextArea(20, 40);
		this.output.setEditable(false);
		
		JScrollPane scrollPane = new JScrollPane(output);
		
		GridBagConstraints outputConstraint = new GridBagConstraints();
		outputConstraint.gridx = 0;
		outputConstraint.gridy = 1;
		outputConstraint.weightx = 1;
		outputConstraint.weighty = 1;
		outputConstraint.fill = GridBagConstraints.BOTH;
		pane.add(scrollPane, outputConstraint);
		
		
		this.input = new JTextField(40);
		
		GridBagConstraints inputConstraint = new GridBagConstraints();
		inputConstraint.gridx = 0;
		inputConstraint.gridy = 2;
		inputConstraint.weightx = 1;
		inputConstraint.weighty = 0;
		inputConstraint.fill = GridBagConstraints.HORIZONTAL;
		pane.add(input, inputConstraint);
		input.addKeyListener(this);
		
		
		String label  = "nickname: ";
		JLabel nicknamePrompt = new JLabel(label);
		this.nicknameField = new JTextField(System.getProperty("user.name"), 20);
		
		FlowLayout nicknameLayout = new FlowLayout(FlowLayout.LEFT);

		JPanel nicknamePanel = new JPanel(nicknameLayout);
		nicknamePanel.add(nicknamePrompt);
		nicknamePanel.add(nicknameField);
		
		GridBagConstraints nicknameConstraint = new GridBagConstraints();
		nicknameConstraint.gridx = 0;
		nicknameConstraint.gridy = 0;
		nicknameConstraint.weightx = 1;
		nicknameConstraint.weighty = 0;
		nicknameConstraint.fill = GridBagConstraints.HORIZONTAL;
		pane.add(nicknamePanel, nicknameConstraint);
	}

	/**
	 * @param person - the person sending the message
	 * @param message - the message content
	 */
	public void addMessage(String person, String message) {
		String chat = this.output.getText();
		this.output.setText(chat + "\n" + person + ": " + message);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getSource() == this.input) {
			if (e.getKeyChar() == '\n') {
				this.addMessage(this.nicknameField.getText(), this.input.getText());
				this.input.setText("");
			}
		}		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void giveInputFocus() {
		// gives focus in the window to the input field
		this.input.requestFocusInWindow();
	}
	
	/**
	 * @return the input
	 */
	public JTextField getInput() {
		return input;
	}
	
	/**
	 * @return the output
	 */
	public JTextArea getOutput() {
		return output;
	}

	/**
	 * @param output the output to set
	 */
	// NEUPORABNO ???
	public void setOutput(JTextArea output) {
		this.output = output;
	}
}
