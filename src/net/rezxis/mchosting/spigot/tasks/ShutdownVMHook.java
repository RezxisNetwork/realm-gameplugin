package net.rezxis.mchosting.spigot.tasks;

import com.google.gson.Gson;

import net.rezxis.mchosting.network.WSClient;
import net.rezxis.mchosting.network.packet.sync.SyncStoppedServer;

public class ShutdownVMHook extends Thread {

	private WSClient ws;
	private int id;
	
	public ShutdownVMHook(WSClient ws, int id) {
		this.ws = ws;
		this.id = id;
	}
	
	public void run() {
		ws.send(new Gson().toJson(new SyncStoppedServer(id)));
	}
}
