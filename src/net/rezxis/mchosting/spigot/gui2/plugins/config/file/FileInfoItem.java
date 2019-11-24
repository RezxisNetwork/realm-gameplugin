package net.rezxis.mchosting.spigot.gui2.plugins.config.file;

import java.io.File;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.gui.GUIAction;
import net.rezxis.mchosting.gui.GUIItem;

public class FileInfoItem extends GUIItem {

	public FileInfoItem(File file) {
		super(getIcon(file));
	}
	
	private static ItemStack getIcon(File file) {
		ItemStack is = new ItemStack(Material.PAPER);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.AQUA+file.getName());
		return is;
	}

	@Override
	public GUIAction invClick(InventoryClickEvent e) {
		return GUIAction.NO_ACTION;
	}
}
