package net.rezxis.mchosting.spigot.gui.main;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.gson.Gson;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.gui.ExecutableItemStack;
import net.rezxis.mchosting.network.packet.sync.SyncRebootServer;
import net.rezxis.mchosting.spigot.RezxisMCHosting;

public class ServerRebootItem extends ExecutableItemStack {

	private static ItemStack getIcon() {
		ItemStack is = new ItemStack(Material.FURNACE);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.RED+"Reboot server");
		is.setItemMeta(im);
		return is;
	}
	
	public ServerRebootItem() {
		super(getIcon());
	}

	@Override
	public ExecReturn Execute(Player player, ClickType click) {
		// TODO Auto-generated method stub
		player.sendMessage(ChatColor.AQUA+"Rebooting server....");
		SyncRebootServer packet = new SyncRebootServer(player.getUniqueId().toString());
		RezxisMCHosting.instance.ws.send(new Gson().toJson(packet));
		return ExecReturn.CLOSE;
	}
}
