package net.rezxis.mchosting.spigot;

import java.util.ArrayList;
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
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerQuitEvent;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.database.Tables;
import net.rezxis.mchosting.database.object.player.DBPlayer;
import net.rezxis.mchosting.database.object.server.DBServer;
import net.rezxis.mchosting.database.object.server.ShopItem;
import net.rezxis.mchosting.spigot.gui.shop.items.item.ShopItemMenu;

public class ServerListener implements Listener {

	public static HashMap<UUID,ShopItem> cmd = new HashMap<>();
	public static ArrayList<UUID> vcmd = new ArrayList<>();
	public static ArrayList<UUID> texture = new ArrayList<>();
	public static ArrayList<UUID> direct = new ArrayList<>();
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		DBServer s = RezxisMCHosting.getDBServer(true);
		s.setPlayers(Bukkit.getOnlinePlayers().size());
		s.update();
	}
	
	@EventHandler
	public void onKick(PlayerKickEvent event) {
		if (event.getPlayer().getUniqueId().equals(RezxisMCHosting.getDBServer(false).getOwner())) {
			event.getPlayer().sendMessage(ChatColor.RED+"あなたはkickされました。 : "+event.getReason());
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent event) {
		DBServer s = RezxisMCHosting.getDBServer(true);
		s.setPlayers(Bukkit.getOnlinePlayers().size()-1);
		s.update();
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onLogin(PlayerLoginEvent event) {
		if (event.getPlayer().getUniqueId().equals(RezxisMCHosting.getDBServer(false).getOwner())) {
			if (event.getResult() != Result.ALLOWED) {
				event.getPlayer().sendMessage(ChatColor.RED+"あなたは接続拒否されました。 : "+event.getKickMessage());
			}
			event.setResult(Result.ALLOWED);
		}
		if (event.getResult() == Result.KICK_FULL) {
			DBPlayer dp = Tables.getPTable().get(event.getPlayer().getUniqueId());
			if (dp.isSupporter() && !dp.isExpiredSupporter()) {
				event.setResult(Result.ALLOWED);
				event.getPlayer().sendMessage(ChatColor.GREEN+"サーバーが満員ですが、サポーターがあるため接続できます。");
			}
		}
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
			DBServer ds = RezxisMCHosting.getDBServer(false);
			item.setCMD(message);
			ds.update();
			player.sendMessage(ChatColor.AQUA+"更新されました。");
			new ShopItemMenu(player, item).delayShow();
		} else if (vcmd.contains(player.getUniqueId())) {
			String message = event.getMessage();
			vcmd.remove(player.getUniqueId());
			if (message.equalsIgnoreCase("cancel")) {
				player.sendMessage(ChatColor.AQUA+"キャンセルされました。");
				return;
			}
			DBServer ds = RezxisMCHosting.getDBServer(true);
			if (message.equalsIgnoreCase("remove")) {
				ds.setVoteCmd("");
				ds.update();
				player.sendMessage(ChatColor.GREEN+"投票時に実行されるコマンドは削除されました。");
				return ;
			}
			if (message.contains("{") || message.contains("}") || message.contains(":") || message.contains("\"")) {
				player.sendMessage(ChatColor.AQUA+"使用できない文字が入っています。");
				return;
			}
			ds.setVoteCmd(message);
			ds.update();
			player.sendMessage(ChatColor.GREEN+message+"に投票時実行されるコマンドは変更されました。");
			event.setCancelled(true);
		} else if (texture.contains(player.getUniqueId())) {
			String message = event.getMessage();
			texture.remove(player.getUniqueId());
			if (message.equalsIgnoreCase("cancel")) {
				player.sendMessage(ChatColor.AQUA+"キャンセルされました。");
				return;
			}
			DBServer ds = RezxisMCHosting.getDBServer(true);
			if (message.equalsIgnoreCase("remove")) {
				ds.setResource("");
				ds.update();
				player.sendMessage(ChatColor.GREEN+"テクスチャは削除されました。");
				return ;
			}
			ds.setResource(message);
			ds.update();
			player.sendMessage(ChatColor.GREEN+message+"にテクスチャは変更されました。");
			event.setCancelled(true);
		} else if (direct.contains(player.getUniqueId())) {
			event.setCancelled(true);
			direct.remove(player.getUniqueId());
			String msg = event.getMessage();
			if (msg.length() <= 4) {
				player.sendMessage(ChatColor.RED+"5文字以上に設定してください。");
				return;
			}
			if (Tables.getSTable().getServerByDirect(msg) != null) {
				player.sendMessage(ChatColor.RED+"既に使用されています。");
				return;
			}
			DBServer s = RezxisMCHosting.getDBServer(true);
			s.setDirect(msg);
			s.update();
			player.sendMessage(ChatColor.GREEN+"次回の再起動以降 "+msg+".rezxis.net で接続が可能です。");
		}
	}
}
