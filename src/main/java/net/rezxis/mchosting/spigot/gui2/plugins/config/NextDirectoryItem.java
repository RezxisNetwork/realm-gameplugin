package net.rezxis.mchosting.spigot.gui2.plugins.config;

import java.io.File;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.gui.GUIAction;
import net.rezxis.mchosting.gui.GUIItem;
import net.rezxis.mchosting.gui.GUIWindow;

public class NextDirectoryItem extends GUIItem {

	private File file;
	private GUIWindow back;
	private boolean isRoot;
	private int page;
	
	public NextDirectoryItem(File file, GUIWindow back, boolean isRoot, int page) {
		super(getIcon());
		this.file = file;
		this.back = back;
		this.isRoot = isRoot;
		this.page = page;
	}
	
	private static ItemStack getIcon() {
		ItemStack is = new ItemStack(Material.ARROW);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.AQUA+"Next Page");
		is.setItemMeta(im);
		return is;
	}

	@Override
	public GUIAction invClick(InventoryClickEvent e) {
		new ConfigManagerMenu((Player) e.getWhoClicked(), file, back, isRoot, page + 1).delayShow();
		return GUIAction.CLOSE;
	}
}
