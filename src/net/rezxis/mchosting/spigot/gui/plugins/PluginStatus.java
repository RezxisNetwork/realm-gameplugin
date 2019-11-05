package net.rezxis.mchosting.spigot.gui.plugins;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.gui.ExecutableItemStack;
import net.rezxis.mchosting.spigot.RezxisMCHosting;

public class PluginStatus extends ExecutableItemStack {

	public PluginStatus() {
		super(getIcon());
	}
	
	private static ItemStack getIcon() {
		ItemStack is = new ItemStack(Material.BOOK);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.AQUA+"Server plugin status");
		ArrayList<String> list = new ArrayList<String>();
		list.add(ChatColor.AQUA+"selectable plugins : "+(RezxisMCHosting.instance.plTable.getPlugins().size()-2));
		list.add(ChatColor.AQUA+"enabled plugin : "+Bukkit.getPluginManager().getPlugins().length);
		im.setLore(list);
		is.setItemMeta(im);
		return is;
	}

	@Override
	public ExecReturn Execute(Player player, ClickType click) {
		return ExecReturn.NO_ACTION;
	}
}
