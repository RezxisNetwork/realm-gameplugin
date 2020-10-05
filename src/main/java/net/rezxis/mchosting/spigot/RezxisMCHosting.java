package net.rezxis.mchosting.spigot;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URI;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.gson.Gson;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.database.Tables;
import net.rezxis.mchosting.database.object.server.DBServer;
import net.rezxis.mchosting.network.WSClient;
import net.rezxis.mchosting.network.packet.sync.SyncPlayerSendPacket;
import net.rezxis.mchosting.spigot.gui.Glow;
import net.rezxis.mchosting.spigot.logs.LogFilter2;
import net.rezxis.mchosting.spigot.tasks.ForceVMKillTask;
import net.rezxis.mchosting.spigot.tasks.ShutdownTask;
import net.rezxis.mchosting.spigot.tasks.ShutdownVMHook;

public class RezxisMCHosting extends JavaPlugin {

	public static RezxisMCHosting instance;
	private static WSClient ws;
	private static DBServer me = null;
	private static boolean loaded = false;
	public static boolean reload = false;
	public static ShutdownVMHook hook = null;
	
	public void onLoad() {
		this.getServer().spigot().getConfig().set("commands.silent-commandblock-console", "true");
		this.getServer().spigot().getConfig().set("commands.log", "false");
		
	}
	
	@SuppressWarnings("deprecation")
	public void onEnable() {
		instance = this;
		Bukkit.getPluginManager().registerEvents(new ServerListener(),this);
		/*if (!loaded) {
			Database.init(System.getenv("db_host"),System.getenv("db_user"),System.getenv("db_pass"),System.getenv("db_port"),System.getenv("db_name"));
		}*/
		me = Tables.getSTable().get(UUID.fromString(System.getenv("sowner")));
		if (!loaded) {
			initWS();
			Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, new ShutdownTask(), 0, 20*60);
			loaded = true;
			hook = new ShutdownVMHook(ws,me.getId());
			Runtime.getRuntime().addShutdownHook(hook);
		}
		for (World world : Bukkit.getWorlds()) {
			world.setGameRuleValue("logAdminCommands", "false");
		}
		getServer().getMessenger().registerIncomingPluginChannel(this,"rezxis:rezxis",new PMessageListener());
		//((DedicatedServer) MinecraftServer.getServer()).propertyManager.setProperty("resource-pack", getDBServer(true).getResource());
		//((Logger)LogManager.getLogger()).addFilter(new LogFilter());
		//((Logger)LogManager.getRootLogger()).addFilter(new LogFilter());
		Bukkit.getServer().getLogger().setFilter(new LogFilter2());
		java.util.logging.Logger.getGlobal().setFilter(new LogFilter2());
		registerGlow();
	}
	
	public void onDisable() {
		if (!reload) {
			new ForceVMKillTask().start();
		} else {
			Runtime.getRuntime().removeShutdownHook(hook);
		}
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		return CommandHandler.onCommand(sender, cmd, commandLabel, args);
	}
	
	public void registerGlow() {
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Glow glow = new Glow(70);
            Enchantment.registerEnchantment(glow);
        }
        catch (IllegalArgumentException e){
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
	
	public void hub(Player player) {
		player.sendMessage(ChatColor.AQUA+"接続中");
		SyncPlayerSendPacket sPacket = new SyncPlayerSendPacket(player.getUniqueId().toString(),"lobby");
		ws.send(new Gson().toJson(sPacket));
	}
	
	public static void initWS() {
		try {
			ws = new WSClient(new URI(System.getenv("sync_address")), new WSClientHandler());
			ws.connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static DBServer getDBServer(boolean sync) {
		if (sync) {
			me.sync();
		}
		return me;
	}
	
	public static WSClient getConn() {
		return ws;
	}
	
	private class PMessageListener implements PluginMessageListener {

		@Override
		public void onPluginMessageReceived(String ch, Player player, byte[] body) {
			if (ch.equalsIgnoreCase("rezxis:rezxis")) {
				DataInputStream in = new DataInputStream(new ByteArrayInputStream(body));
				String arg0 = null;
				String arg1 = null;
				try {
					arg0 = in.readUTF();
					arg1 = in.readUTF();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (arg0.equalsIgnoreCase("vote")) {
					me.sync();
					if (!me.getVoteCmd().isEmpty()) {
						Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), me.getVoteCmd().replace("[player]", arg1));
					}
				}
			}
		}
	}
}
