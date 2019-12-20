package net.rezxis.mchosting.spigot.tasks;

public class ForceVMKillTask extends Thread {

	private int time = 60;
	
	public void run() {
		try {
			Thread.sleep(1000 * time);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.exit(0);
	}
}
