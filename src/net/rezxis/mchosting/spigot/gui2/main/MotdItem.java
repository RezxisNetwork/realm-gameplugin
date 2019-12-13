package net.rezxis.mchosting.spigot.gui2.main;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.database.DBServer;
import net.rezxis.mchosting.gui.GUIAction;
import net.rezxis.mchosting.gui.GUIItem;
import net.rezxis.mchosting.spigot.RezxisMCHosting;
import net.rezxis.mchosting.gui.anvil.AnvilGUI;

public class MotdItem extends GUIItem {

	public MotdItem() {
		super(getIcon());
	}
	
	private static ItemStack getIcon() {
		ItemStack is = new ItemStack(Material.BOOK);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.GREEN+"MOTDを変更");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(RezxisMCHosting.getDBServer().getMotd());
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	@SuppressWarnings("deprecation")
	@Override
	public GUIAction invClick(InventoryClickEvent e) {
		Bukkit.getScheduler().scheduleAsyncDelayedTask(RezxisMCHosting.instance, new Runnable() {
			public void run() {
				new AnvilGUI.Builder()
				.onClose(pl -> {
				})
				.onComplete((pl,text) -> {
					if (text == null) {
						return AnvilGUI.Response.text("Motdを入れてください。");
					}
					DBServer server = RezxisMCHosting.getDBServer();
					server.setMotd(text.replace("&", "§"));
					server.update();
					pl.sendMessage(ChatColor.GREEN+"変更されました。");
					new RealmMenu(pl).delayShow();
					return AnvilGUI.Response.close();
				})
				.text("Motdを入れてください。")
				.plugin(RezxisMCHosting.instance)
				.open((Player) e.getWhoClicked());
			}
		}, 2);
		return GUIAction.UPDATE;
	}
}
