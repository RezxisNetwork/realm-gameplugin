package net.rezxis.mchosting.spigot.gui.shop.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.gui.GUIAction;
import net.rezxis.mchosting.gui.GUIItem;

public class BackPageItem extends GUIItem {

	private int page;
	
	public BackPageItem(int page) {
		super (getIcon());
		this.page = page;
	}
	
	private static ItemStack getIcon() {
		ItemStack is = new ItemStack(Material.ARROW);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.LIGHT_PURPLE+"Previous Page");
		is.setItemMeta(im);
		return is;
	}

	@Override
	public GUIAction invClick(InventoryClickEvent e) {
		new ItemManageMenu((Player)e.getWhoClicked(),page-1).delayShow();
		return GUIAction.CLOSE;
	}
}