package net.rezxis.mchosting.spigot.gui.plugins;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.databse.DBPlugin;
import net.rezxis.mchosting.gui.ExecutableItemStack;
import net.rezxis.mchosting.gui.GuiOpener;
import net.rezxis.mchosting.spigot.RezxisMCHosting;
import net.rezxis.mchosting.spigot.gui.plugins.config.ConfigManagerGui;

public class PluginItem extends ExecutableItemStack {

	private DBPlugin plugin;
	
	public PluginItem(DBPlugin plugin) {
		super(getIcon(plugin));
		this.plugin = plugin;
	}
	
	private static ItemStack getIcon(DBPlugin plugin) {
		ItemStack is;
		ChatColor c;
		if (RezxisMCHosting.instance.me.getPlugins().contains(plugin.getName())) {
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
	public ExecReturn Execute(Player player, ClickType click) {
		//if (click.isRightClick()) {
			ArrayList<String> list = RezxisMCHosting.instance.me.getPlugins();
			if (RezxisMCHosting.instance.me.getPlugins().contains(plugin.getName())) {
				list.remove(plugin.getName());
			} else {
				list.add(plugin.getName());
			}
			RezxisMCHosting.instance.me.setPlugins(list);
			RezxisMCHosting.instance.me.update();
			player.sendMessage(ChatColor.AQUA+"変更を反映するには再起動してください。");
			return ExecReturn.UPDATE_ITEMS;
		//}
		//return ExecReturn.NO_ACTION;
	}
}
