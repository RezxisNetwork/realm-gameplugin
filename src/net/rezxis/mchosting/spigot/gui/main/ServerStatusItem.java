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

public class ServerStatusItem extends ExecutableItemStack {

	public ServerStatusItem() {
		super(getIcon());
	}
	
	private static ItemStack getIcon() {
		ItemStack is = new ItemStack(Material.BOOK);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.GREEN+"Server Status");
		ArrayList<String> list = new ArrayList<>();
		list.add(ChatColor.AQUA+"Online players : "+Bukkit.getOnlinePlayers().size());
		im.setLore(list);
		is.setItemMeta(im);
		return is;
	}

	@Override
	public ExecReturn Execute(Player player, ClickType click) {
		// TODO Auto-generated method stub
		return ExecReturn.NO_ACTION;
	}
}
