package net.rezxis.mchosting.spigot.gui2.main;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.gui.GUIAction;
import net.rezxis.mchosting.gui.GUIItem;
import net.rezxis.mchosting.spigot.gui2.shop.ShopMenu;

public class ShopMenuItem extends GUIItem {

	public ShopMenuItem() {
		super(getIcon());
	}
	
	private static ItemStack getIcon() {
		ItemStack is = new ItemStack(Material.ENDER_CHEST);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.AQUA+"ショップを編集");
		ArrayList<String> lore = new ArrayList<>();
		lore.add(ChatColor.GRAY+"RealmCoinで取引できるshopを編集");
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	@Override
	public GUIAction invClick(InventoryClickEvent e) {
		new ShopMenu((Player) e.getWhoClicked()).delayShow();
		return GUIAction.CLOSE;
	}
}
