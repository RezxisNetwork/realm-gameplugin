package net.rezxis.mchosting.spigot.tasks;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.gson.Gson;

import net.rezxis.mchosting.network.packet.sync.SyncStopServer;
import net.rezxis.mchosting.spigot.RezxisMCHosting;

public class ShutdownTask extends BukkitRunnable {

	private int count = 0;
	
	@Override
	public void run() {
		if (count > 5) {
			RezxisMCHosting.getConn().send(
					new Gson().toJson(
							new SyncStopServer(RezxisMCHosting.getDBServer().getOwner().toString())
							)
					);
			return;
		}
		if (Bukkit.getServer().getOnlinePlayers().size() == 0) {
			++count;
		} else {
			count = 0;
		}
	}
}
