package net.rezxis.mchosting.spigot.gui2.plugins.config;

import java.io.File;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.gui.GUIAction;
import net.rezxis.mchosting.gui.GUIItem;

public class DeleteDirectoryItem extends GUIItem {

	private File file;
	
	public DeleteDirectoryItem(File file) {
		super(getIcon());
		this.file = file;
	}
	
	private static ItemStack getIcon() {
		ItemStack is = new ItemStack(Material.BARRIER);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.RED+"Delete directory");
		is.setItemMeta(im);
		return is;
	}

	@Override
	public GUIAction invClick(InventoryClickEvent e) {
		this.file.delete();
		e.getWhoClicked().sendMessage(ChatColor.RED+"the directory was deleted.");
		return GUIAction.UPDATE;
	}
}
