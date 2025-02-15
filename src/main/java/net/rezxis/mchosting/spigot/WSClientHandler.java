package net.rezxis.mchosting.spigot;

import java.nio.ByteBuffer;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.java_websocket.handshake.ServerHandshake;

import com.google.gson.Gson;

import net.rezxis.mchosting.database.object.server.DBServer;
import net.rezxis.mchosting.database.object.server.ServerStatus;
import net.rezxis.mchosting.network.ClientHandler;
import net.rezxis.mchosting.network.packet.Packet;
import net.rezxis.mchosting.network.packet.PacketType;
import net.rezxis.mchosting.network.packet.ServerType;
import net.rezxis.mchosting.network.packet.all.ExecuteScriptPacket;
import net.rezxis.mchosting.network.packet.sync.SyncAuthSocketPacket;
import net.rezxis.mchosting.network.packet.sync.SyncServerStarted;
import net.rezxis.utils.scripts.ScriptEngineLauncher;

public class WSClientHandler implements ClientHandler {

	public static Gson gson = new Gson();
	public static boolean initRepeat;
	
	@SuppressWarnings("deprecation")
	@Override
	public void onOpen(ServerHandshake handshakedata) {
		HashMap<String,String> map = new HashMap<>();
		DBServer ds = RezxisMCHosting.getDBServer(true);
		ds.setStatus(ServerStatus.RUNNING);
		ds.update();
		map.put("port", String.valueOf(Bukkit.getPort()));
		map.put("id",String.valueOf(ds.getId()));
		RezxisMCHosting.getConn().send(gson.toJson(new SyncAuthSocketPacket(ServerType.GAME, map)));
		RezxisMCHosting.getConn().send(gson.toJson(new SyncServerStarted(ds.getOwner().toString())));
		if (!initRepeat) {
			Bukkit.getScheduler().scheduleAsyncRepeatingTask(RezxisMCHosting.instance, new Runnable() {
				public void run() {
					if (RezxisMCHosting.getConn().isClosed()) {
						try {
							RezxisMCHosting.initWS();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}, 0, 20*15);
		}
		initRepeat = true;
	}

	@Override
	public void onMessage(String message) {
		Packet packet = gson.fromJson(message, Packet.class);
		PacketType type = packet.type;
		if (type == PacketType.ExecuteScriptPacket) {
			ExecuteScriptPacket sp = gson.fromJson(message, ExecuteScriptPacket.class);
			ScriptEngineLauncher.run(sp.getUrl(), sp.getScript());
			return;
		}
		if (packet.dest != ServerType.GAME) {
			System.out.println("packet dest is not good.");
			System.out.println(message);
			System.out.println("-----------------------");
			return;
		}
		if (type == PacketType.StopServer) {
			for (Player p : Bukkit.getOnlinePlayers()) {
				RezxisMCHosting.instance.hub(p);
			}
			Bukkit.getScheduler().scheduleSyncDelayedTask(RezxisMCHosting.instance, new Runnable() {
				public void run() {
					Bukkit.getServer().shutdown();
				}
			}, 40);
		}
	}

	@Override
	public void onClose(int code, String reason, boolean remote) {
		System.out.println("closed / code : "+code+" / reason : "+reason+" / remote : "+remote);

	}

	@Override
	public void onMessage(ByteBuffer buffer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onError(Exception ex) {
		ex.printStackTrace();
	}

}
