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
import net.rezxis.mchosting.spigot.gui.wmanager.WhiteListManagerGui;

public class WhiteListManagerItem extends ExecutableItemStack {

	public WhiteListManagerItem() {
		super(getIcon());
	}
	
	private static ItemStack getIcon() {
		ItemStack is = new ItemStack(Material.CACTUS);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.GOLD+"WhiteList");
		ArrayList<String> list = new ArrayList<>();
		list.add(ChatColor.AQUA+"Click to manage whitelist");
		im.setLore(list);
		is.setItemMeta(im);
		return is;
	}

	@Override
	public ExecReturn Execute(Player player, ClickType click) {
		
		Bukkit.getScheduler().scheduleAsyncDelayedTask(RezxisMCHosting.instance, new Runnable() {
			public void run() {
				GuiOpener.open(player, new WhiteListManagerGui());
			}
		}, 2);
		
		return ExecReturn.CLOSE;
	}
}
