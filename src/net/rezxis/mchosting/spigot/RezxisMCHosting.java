package net.rezxis.mchosting.spigot;

import java.net.URI;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.Gson;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.database.DBServer;
import net.rezxis.mchosting.database.Database;
import net.rezxis.mchosting.database.tables.CrateTable;
import net.rezxis.mchosting.database.tables.FilesTable;
import net.rezxis.mchosting.database.tables.PlayersTable;
import net.rezxis.mchosting.database.tables.PluginsTable;
import net.rezxis.mchosting.database.tables.ServersTable;
import net.rezxis.mchosting.network.WSClient;
import net.rezxis.mchosting.network.packet.sync.SyncPlayerSendPacket;
import net.rezxis.mchosting.network.packet.sync.SyncStoppedServer;
import net.rezxis.mchosting.spigot.tasks.RewardTask;
import net.rezxis.mchosting.spigot.tasks.ShutdownTask;

public class RezxisMCHosting extends JavaPlugin {

	public static RezxisMCHosting instance;
	private static ServersTable sTable;
	private static PluginsTable plTable;
	private static PlayersTable pTable;
	private static FilesTable fTable;
	public CrateTable cTable;
	private static WSClient ws;
	private static DBServer me = null;
	private static Props props;
	private static boolean loaded = false;
	
	@SuppressWarnings("deprecation")
	public void onEnable() {
		instance = this;
		Bukkit.getPluginManager().registerEvents(new ServerListener(),this);
		if (!loaded) {
			props = new Props("hosting.propertis");
			Database.init();
			sTable = new ServersTable();
			plTable = new PluginsTable();
			pTable = new PlayersTable();
			cTable = new CrateTable();
			fTable = new FilesTable();
		}
		me = sTable.getByPort(this.getServer().getPort());
		if (!loaded) {
			initWS();
			Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, new RewardTask(), 0, 20*60*15);
			Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, new ShutdownTask(), 0, 20*60);
			loaded = true;
		}
	}
	
	public void onDisable() {
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		return CommandHandler.onCommand(sender, cmd, commandLabel, args);
	}
	
	public void hub(Player player) {
		player.sendMessage(ChatColor.AQUA+"接続中");
		SyncPlayerSendPacket sPacket = new SyncPlayerSendPacket(player.getUniqueId().toString(),"lobby");
		ws.send(new Gson().toJson(sPacket));
	}
	
	public static void initWS() {
		try {
			ws = new WSClient(new URI(props.SYNC_ADDRESS), new WSClientHandler());
			ws.connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Props getProps() {
		return props;
	}
	
	public static DBServer getDBServer() {
		return me;
	}
	
	public static WSClient getConn() {
		return ws;
	}
	
	public static ServersTable getSTable() {
		return sTable;
	}
	
	public static PluginsTable getPLTable() {
		return plTable;
	}
	
	public static FilesTable getFTable() {
		return fTable;
	}
	
	public static PlayersTable getPTable() {
		return pTable;
	}
}
