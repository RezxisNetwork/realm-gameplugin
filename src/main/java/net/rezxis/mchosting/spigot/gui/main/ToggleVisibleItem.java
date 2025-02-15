package net.rezxis.mchosting.spigot.gui.main;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.database.object.server.DBServer;
import net.rezxis.mchosting.gui.GUIAction;
import net.rezxis.mchosting.gui.GUIItem;
import net.rezxis.mchosting.spigot.RezxisMCHosting;

public class ToggleVisibleItem extends GUIItem {

	public ToggleVisibleItem() {
		super(getIcon());
	}
	
	private static ItemStack getIcon() {
		ItemStack is = null;
		DBServer server = RezxisMCHosting.getDBServer(false);
		if (server.isVisible()) {
			is = new ItemStack(Material.EMERALD_BLOCK);
		} else  {
			is = new ItemStack(Material.REDSTONE_BLOCK);
		}
		ItemMeta im = is.getItemMeta();
		if (server.isVisible()) {
			im.setDisplayName(ChatColor.GREEN+"表示");
		} else {
			im.setDisplayName(ChatColor.RED+"非表示");
		}
		ArrayList<String> lore = new ArrayList<>();
		lore.add(ChatColor.GRAY+"サーバーリストの表示設定");
		lore.add(ChatColor.AQUA+"クリックで切り替え");
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	@Override
	public GUIAction invClick(InventoryClickEvent e) {
		DBServer server = RezxisMCHosting.getDBServer(false);
		server.setVisible(!server.isVisible());
		server.update();
		return GUIAction.UPDATE;
	}
}
