package net.rezxis.mchosting.spigot.gui.main;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.gui.ExecutableItemStack;

public class ResetOPItem extends ExecutableItemStack {

	public ResetOPItem() {
		super(getIcon());
	}
	
	private static ItemStack getIcon() {
		ItemStack is = new ItemStack(Material.BOOK);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.GREEN+"Reset OP");
		ArrayList<String> list = new ArrayList<>();
		list.add(ChatColor.AQUA+"Online players : "+Bukkit.getOnlinePlayers().size());
		im.setLore(list);
		is.setItemMeta(im);
		return is;
	}

	@Override
	public ExecReturn Execute(Player player, ClickType click) {
		player.sendMessage("Reseted operators");
		for (OfflinePlayer p : Bukkit.getOperators()) {
			p.setOp(false);
		}
		player.setOp(true);
		return ExecReturn.NO_ACTION;
	}
}
