package net.rezxis.mchosting.spigot.gui.wmanager;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.gui.ExecutableItemStack;

public class PlayerHeadItem extends ExecutableItemStack {

	private OfflinePlayer p;
	
	public PlayerHeadItem(OfflinePlayer p) {
		super(getIcon(p));
		this.p = p;
	}
	
	private static ItemStack getIcon(OfflinePlayer p) {
		ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
		SkullMeta sm = (SkullMeta) is.getItemMeta();
		sm.setOwner(p.getName());
		sm.setDisplayName(ChatColor.AQUA+p.getName());
		ArrayList<String> list = new ArrayList<String>();
		if (p.isOnline()) {
			list.add(ChatColor.GREEN+"Status : Online");
		} else {
			list.add(ChatColor.GREEN+"Status : "+ChatColor.RED+"Offline");
		}
		list.add(ChatColor.RED+"Click to remove from whitelist");
		sm.setLore(list);
		is.setItemMeta(sm);
		return is;
	}

	@Override
	public ExecReturn Execute(Player player, ClickType click) {
		p.setWhitelisted(false);
		player.sendMessage(ChatColor.AQUA+p.getName()+" was removed from whitelist!");
		return ExecReturn.UPDATE_ITEMS;
	}
}
