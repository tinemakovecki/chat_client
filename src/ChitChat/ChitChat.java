package ChitChat;

public class ChitChat {

	public static void main(String[] args) {
		ChatFrame chatFrame = new ChatFrame();
		PrimeRobot robot = new PrimeRobot(chatFrame);
		robot.activate();
		ParrotBot parrot = new ParrotBot(chatFrame.getOutput(), "Jeff");
		chatFrame.pack();
		chatFrame.giveInputFocus();
		chatFrame.setVisible(true);
	}

}
