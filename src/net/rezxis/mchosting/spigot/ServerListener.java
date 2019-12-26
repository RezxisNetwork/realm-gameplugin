package net.rezxis.mchosting.spigot;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.database.object.server.ShopItem;
import net.rezxis.mchosting.spigot.gui2.shop.items.item.ShopItemMenu;

@SuppressWarnings("deprecation")
public class ServerListener implements Listener {

	public static HashMap<UUID,ShopItem> cmd = new HashMap<>();
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Bukkit.getScheduler().scheduleAsyncDelayedTask(RezxisMCHosting.instance, new Runnable() {
			public void run() {
				RezxisMCHosting.getDBServer().sync();
				RezxisMCHosting.getDBServer().setPlayers(Bukkit.getOnlinePlayers().size());
				RezxisMCHosting.getDBServer().update();
			}
		}, 6);
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent event) {
		Bukkit.getScheduler().scheduleAsyncDelayedTask(RezxisMCHosting.instance, new Runnable() {
			public void run() {
				RezxisMCHosting.getDBServer().sync();
				RezxisMCHosting.getDBServer().setPlayers(Bukkit.getOnlinePlayers().size());
				RezxisMCHosting.getDBServer().update();
			}
		}, 6);
	}
	
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent event) {
		boolean flag = false;
		if (event.getMessage().equalsIgnoreCase("/bukkit:reload")) {
			flag = true;
		} else if (event.getMessage().equalsIgnoreCase("/reload")) {
			flag = true;
		} else if (event.getMessage().equalsIgnoreCase("bukkit:reload")) {
			flag = true;
		} else if (event.getMessage().equalsIgnoreCase("reload")) {
			flag = true;
		} else if (event.getMessage().equalsIgnoreCase("rl")) {
			flag = true;
		} else if (event.getMessage().equalsIgnoreCase("/rl")) {
			flag = true;
		} else if (event.getMessage().equalsIgnoreCase("bukkit:rl")) {
			flag = true;
		} else if (event.getMessage().equalsIgnoreCase("/bukkit:rl")) {
			flag = true;
		} else if (event.getMessage().contains("restart")) {
			event.setCancelled(true);
			event.getPlayer().sendMessage(ChatColor.RED+"/restartは使用できません。/realmから再起動を使っていください");
			return;
		}
		if (flag) {
			RezxisMCHosting.reload = true;
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		if (cmd.containsKey(player.getUniqueId())) {
			event.setCancelled(true);
			ShopItem item = cmd.get(player.getUniqueId());
			cmd.remove(player.getUniqueId());
			String message = event.getMessage();
			if (message.equalsIgnoreCase("cancel")) {
				player.sendMessage(ChatColor.AQUA+"キャンセルされました。");
				new ShopItemMenu(player, item).delayShow();
				return;
			}
			if (message.contains("{") || message.contains("}") || message.contains(":") || message.contains("\"")) {
				player.sendMessage(ChatColor.AQUA+"使用できない文字が入っています。");
				new ShopItemMenu(player, item).delayShow();
				return;
			}
				
			item.setCMD(message);
			RezxisMCHosting.getDBServer().update();
			player.sendMessage(ChatColor.AQUA+"更新されました。");
			new ShopItemMenu(player, item).delayShow();
		}
	}
}