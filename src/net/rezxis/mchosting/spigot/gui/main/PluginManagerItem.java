package net.rezxis.mchosting.spigot.gui.main;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.gui.ExecutableItemStack;
import net.rezxis.mchosting.gui.GuiOpener;
import net.rezxis.mchosting.spigot.RezxisMCHosting;
import net.rezxis.mchosting.spigot.gui.plugins.PluginManagerGui;

public class PluginManagerItem extends ExecutableItemStack {

	public PluginManagerItem() {
		super(getIcon());
	}

	@Override
	public ExecReturn Execute(Player player, ClickType click) {
		Bukkit.getScheduler().scheduleAsyncDelayedTask(RezxisMCHosting.instance, new Runnable() {
			public void run() {
				GuiOpener.open(player, new PluginManagerGui());
			}
		}, 2);
		return ExecReturn.CLOSE;
	}
	
	private static ItemStack getIcon() {
		ItemStack is = new ItemStack(Material.GRASS);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.GOLD+"Plugin Manager");
		ArrayList<String> list = new ArrayList<>();
		list.add(ChatColor.AQUA+"Click to manage plugins");
		im.setLore(list);
		is.setItemMeta(im);
		return is;
	}
}
