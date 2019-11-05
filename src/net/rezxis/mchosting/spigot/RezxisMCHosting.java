package net.rezxis.mchosting.spigot;

import java.net.URI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.Gson;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.databse.DBServer;
import net.rezxis.mchosting.databse.Database;
import net.rezxis.mchosting.databse.tables.PluginsTable;
import net.rezxis.mchosting.databse.tables.ServersTable;
import net.rezxis.mchosting.network.WSClient;
import net.rezxis.mchosting.network.packet.sync.SyncPlayerSendPacket;
import net.rezxis.mchosting.network.packet.sync.SyncStopServer;
import net.rezxis.mchosting.network.packet.sync.SyncStoppedServer;

public class RezxisMCHosting extends JavaPlugin {

	public ServersTable sTable;
	public PluginsTable plTable;
	public static RezxisMCHosting instance;
	public WSClient ws;
	public boolean isShutdown = false;
	public DBServer me = null;
	public Props props;
	public int count = 0;
	/*
	 * 
	 * 暇だからどんな感じなのかみちゃおっ　
	 * 
	 * タ　ピ　オ　カ
	 *
	 *☺ ンョ゛　ハー゛ 
	 *
	 //TODO: この世界に混沌を導く
	 * 
	 *
	 * 
	 * 
	 */
	@SuppressWarnings("deprecation")
	public void onEnable() {
		instance = this;
		Bukkit.getPluginManager().registerEvents(new ServerListener(),this);
		if (me == null) {
			props = new Props("hosting.propertis");
			Database.init();
			sTable = new ServersTable();
			plTable = new PluginsTable();
			me = sTable.getByPort(this.getServer().getPort());
			new Thread(()->{
					try {
						ws = new WSClient(new URI(props.SYNC_ADDRESS), new WSClientHandler());
					} catch (Exception e) {
						e.printStackTrace();
					}
					ws.connect();
					System.out.println("reconnecting...");
				
			}).start();
			Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
				public void run() {
					if (RezxisMCHosting.instance.count > 5) {
						ws.send(new Gson().toJson(new SyncStopServer(me.getOwner().toString())));
						return;
					}
					if (Bukkit.getServer().getOnlinePlayers().size() == 0) {
						++RezxisMCHosting.instance.count;
					} else {
						RezxisMCHosting.instance.count = 0;
					}
				}
			}, 0, 20*60);
		} else {
			me.setPort(Bukkit.getPort());
			me.update();
		}
	}
	
	public void onDisable() {
		ws.send(new Gson().toJson(new SyncStoppedServer(me.getID())));
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		return CommandHandler.onCommand(sender, cmd, commandLabel, args);
	}
	
	public void hub(Player player) {
		player.sendMessage(ChatColor.AQUA+"接続中");
		SyncPlayerSendPacket sPacket = new SyncPlayerSendPacket(player.getUniqueId().toString(),"lobby");
		ws.send(new Gson().toJson(sPacket));
	}
}
