package net.rezxis.mchosting.spigot.gui.wmanager;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.gui.ExecutableItemStack;

public class WhitelistStatusItem extends ExecutableItemStack {

	public WhitelistStatusItem() {
		super(getIcon());
	}
	
	private static ItemStack getIcon() {
		ItemStack is = new ItemStack(Material.BOOK);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.GREEN+"Whitelist Status");
		ArrayList<String> list = new ArrayList<>();
		list.add(ChatColor.AQUA+"Click to toggle whitelist");
		if (Bukkit.hasWhitelist()) {
			list.add(ChatColor.AQUA+"Whitelist is enabled");
		} else {
			list.add(ChatColor.RED+"Whitelist is disabled");
		}
		list.add(ChatColor.AQUA+"Allowed players : "+Bukkit.getWhitelistedPlayers().size());
		im.setLore(list);
		is.setItemMeta(im);
		return is;
	}

	@Override
	public ExecReturn Execute(Player player, ClickType click) {
		Bukkit.setWhitelist(!Bukkit.hasWhitelist());
		player.sendMessage(ChatColor.AQUA+"Whitelist was toggled!");
		return ExecReturn.UPDATE_ITEMS;
	}
}
