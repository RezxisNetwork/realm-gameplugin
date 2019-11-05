package net.rezxis.mchosting.spigot.gui.main;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.databse.DBServer;
import net.rezxis.mchosting.gui.ExecutableItemStack;
import net.rezxis.mchosting.spigot.RezxisMCHosting;

public class NetworkStatusItem extends ExecutableItemStack {

	public NetworkStatusItem() {
		super(getIcon());
	}
	
	private static ItemStack getIcon() {
		ItemStack is = new ItemStack(Material.NETHER_STAR);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.GREEN+""+ChatColor.BOLD+"NetworkServerStatus");
		ArrayList<String> list = new ArrayList<>();
		ArrayList<DBServer> servers = RezxisMCHosting.instance.sTable.getOnlineServers();
		int players = RezxisMCHosting.instance.sTable.getOnlinePlayers();
		list.add(ChatColor.AQUA+"Online players : "+players);
		list.add(ChatColor.AQUA+"Online servers : "+servers.size());
		im.setLore(list);
		is.setItemMeta(im);
		return is;
	}

	@Override
	public ExecReturn Execute(Player player, ClickType click) {
		return ExecReturn.NO_ACTION;
	}
}
