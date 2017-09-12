package ChitChat;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JTextArea;

public class PrimeRobot extends TimerTask {
	private JTextArea output;
	private Timer primerTimer;
	private int k;
	
	private static boolean isPrime(int n) {
		for (int i = 2; i * i <= n; i++) {
			if (n % i == 0) { return false; }
		}
		return true;
	}

	public PrimeRobot(JTextArea output) {
		this.output = output;
		this.k = 2;
	}

	/**
	 * Activate the robot!
	 */
	public void activate() {
		this.primerTimer = new Timer();
		this.primerTimer.scheduleAtFixedRate(this, 2000, 1000);
	}
	
	// stop and reset the robot
	public void stop() {
		this.primerTimer.cancel();
		this.primerTimer.purge();
		this.primerTimer = null;
		this.k = 2;
	}
	
	@Override
	public void run() {
		if (isPrime(this.k)) {
			String chat = output.getText();
			output.setText(chat + "\n" + "primer: " + Integer.toString(this.k) + " is prime");
		}
		this.k++;
	}
}
