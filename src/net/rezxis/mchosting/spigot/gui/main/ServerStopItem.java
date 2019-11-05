package net.rezxis.mchosting.spigot.gui.main;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.gson.Gson;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.gui.ExecutableItemStack;
import net.rezxis.mchosting.network.packet.sync.SyncStopServer;
import net.rezxis.mchosting.spigot.RezxisMCHosting;

public class ServerStopItem extends ExecutableItemStack {

	public ServerStopItem() {
		super(getIcon());
	}
	
	private static ItemStack getIcon() {
		ItemStack is = new ItemStack(Material.REDSTONE_BLOCK);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.RED+"Stop the server");
		ArrayList<String> list = new ArrayList<>();
		list.add(ChatColor.RED+"Click to stop server");
		im.setLore(list);
		is.setItemMeta(im);
		return is;
	}

	@Override
	public ExecReturn Execute(Player player, ClickType click) {
		SyncStopServer packet = new SyncStopServer(player.getUniqueId().toString());
		RezxisMCHosting.instance.ws.send(new Gson().toJson(packet));
		return ExecReturn.CLOSE;
	}
}
