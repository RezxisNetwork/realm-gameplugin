package net.rezxis.mchosting.spigot.gui.plugins.config;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.gui.GUIAction;
import net.rezxis.mchosting.gui.GUIItem;
import net.rezxis.mchosting.gui.GUIWindow;

public class DirectoryItem extends GUIItem {

	private File file;
	private GUIWindow back;
	
	public DirectoryItem(File file, GUIWindow back) {
		super(getIcon(file));
		this.file = file;
		this.back = back;
	}
	
	private static ItemStack getIcon(File file) {
		ItemStack is = new ItemStack(Material.BOOK);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.GOLD+"Directory:"+file.getName());
		ArrayList<String> list = new ArrayList<>();
		list.add(ChatColor.AQUA+"Click to open");
		im.setLore(list);
		is.setItemMeta(im);
		return is;
	}

	@Override
	public GUIAction invClick(InventoryClickEvent e) {
		new ConfigManagerMenu((Player) e.getWhoClicked(), file, back, false, 0).delayShow();
		return GUIAction.CLOSE;
	}
}
