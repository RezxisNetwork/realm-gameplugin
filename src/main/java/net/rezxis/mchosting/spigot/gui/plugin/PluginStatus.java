package net.rezxis.mchosting.spigot.gui.plugin;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.database.Tables;
import net.rezxis.mchosting.gui.GUIAction;
import net.rezxis.mchosting.gui.GUIItem;

public class PluginStatus extends GUIItem {
	
	public PluginStatus() {
		super(getIcon());
	}
	
	private static ItemStack getIcon() {
		ItemStack is = new ItemStack(Material.BOOK);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.AQUA+"Server plugin status");
		ArrayList<String> list = new ArrayList<String>();
		list.add(ChatColor.AQUA+"selectable plugins : "+(Tables.getPlTable().getPlugins().size()-2));
		list.add(ChatColor.AQUA+"enabled plugin : "+Bukkit.getPluginManager().getPlugins().length);
		im.setLore(list);
		is.setItemMeta(im);
		return is;
	}

	@Override
	public GUIAction invClick(InventoryClickEvent e) {
		return GUIAction.NO_ACTION;
	}
}