package net.rezxis.mchosting.spigot.gui2.plugins;

import java.util.ArrayList;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.database.object.server.DBPlugin;
import net.rezxis.mchosting.database.object.server.DBServer;
import net.rezxis.mchosting.gui.GUIAction;
import net.rezxis.mchosting.gui.GUIItem;
import net.rezxis.mchosting.spigot.RezxisMCHosting;

public class PluginItem extends GUIItem {

	private DBPlugin plugin;
	
	public PluginItem(DBPlugin plugin, DBServer ds) {
		super(getIcon(plugin,ds));
		this.plugin = plugin;
	}
	
	@SuppressWarnings("deprecation")
	private static ItemStack getIcon(DBPlugin plugin, DBServer ds) {
		ItemStack is;
		ChatColor c;
		if (ds.getPlugins().contains(plugin.getName())) {
			is = new ItemStack(Material.INK_SACK,1 , DyeColor.LIME.getDyeData());
			c = ChatColor.GREEN;
		} else {
			is = new ItemStack(Material.INK_SACK,1 , DyeColor.RED.getDyeData());
			c = ChatColor.RED;
		}
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(c+plugin.getName());
		ArrayList<String> list = new ArrayList<>();
		list.add(ChatColor.AQUA+"依存関係-"+plugin.getDepends().size());
		for (String s : plugin.getDepends()) {
			list.add(ChatColor.AQUA+s);
		}
		list.add(ChatColor.AQUA+"--------------------------");
		list.add(ChatColor.AQUA+"Version : "+plugin.getVersion());
		list.add(ChatColor.AQUA+"click to toggle");
		im.setLore(list);
		is.setItemMeta(im);
		return is;
	}

	@Override
	public GUIAction invClick(InventoryClickEvent e) {
		DBServer ds = RezxisMCHosting.getDBServer();
		ArrayList<String> list = ds.getPlugins();
		if (ds.getPlugins().contains(plugin.getName())) {
			list.remove(plugin.getName());
		} else {
			list.add(plugin.getName());
		}
		ds.setPlugins(list);
		ds.update();
		e.getWhoClicked().sendMessage(ChatColor.AQUA+"変更を反映するには再起動してください。");
		return GUIAction.UPDATE;
	}
}
