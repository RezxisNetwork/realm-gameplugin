package net.rezxis.mchosting.spigot.gui2.shop;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.gui.GUIAction;
import net.rezxis.mchosting.gui.GUIItem;
import net.rezxis.mchosting.spigot.gui2.shop.items.ItemManageMenu;

public class ManageItem extends GUIItem {

	public ManageItem() {
		super(getIcon());
	}
	
	private static ItemStack getIcon() {
		ItemStack is = new ItemStack(Material.REDSTONE);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.YELLOW+"アイテムを編集");
		ArrayList<String> lore = new ArrayList<>();
		lore.add(ChatColor.GRAY+"アイテムを編集します");
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	@Override
	public GUIAction invClick(InventoryClickEvent e) {
		new ItemManageMenu((Player) e.getWhoClicked(), 1).delayShow();
		return GUIAction.CLOSE;
	}
}
