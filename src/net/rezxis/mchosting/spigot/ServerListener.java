package net.rezxis.mchosting.spigot;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.gui.GuiOpener;
import net.rezxis.mchosting.spigot.gui.wmanager.WhiteListManagerGui;

@SuppressWarnings("deprecation")
public class ServerListener implements Listener {

	public static ArrayList<UUID> list = new ArrayList<>();
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Bukkit.getScheduler().scheduleAsyncDelayedTask(RezxisMCHosting.instance, new Runnable() {
			public void run() {
				RezxisMCHosting.instance.me.sync();
				RezxisMCHosting.instance.me.setPlayers(Bukkit.getOnlinePlayers().size());
				RezxisMCHosting.instance.me.update();
			}
		}, 6);
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent event) {
		Bukkit.getScheduler().scheduleAsyncDelayedTask(RezxisMCHosting.instance, new Runnable() {
			public void run() {
				RezxisMCHosting.instance.me.sync();
				RezxisMCHosting.instance.me.setPlayers(Bukkit.getOnlinePlayers().size());
				RezxisMCHosting.instance.me.update();
			}
		}, 6);
	}
	
	@EventHandler
	public void onChat(PlayerChatEvent event) {
		if (list.contains(event.getPlayer().getUniqueId())) {
			list.remove(event.getPlayer().getUniqueId());
			event.setCancelled(true);
			OfflinePlayer p = Bukkit.getOfflinePlayer(event.getMessage());
			if (p == null) {
				event.getPlayer().sendMessage(ChatColor.RED+"There is no player whose name is that.");
				return;
			}
			p.setWhitelisted(true);
			GuiOpener.open(event.getPlayer(), new WhiteListManagerGui());
		}
	}
}