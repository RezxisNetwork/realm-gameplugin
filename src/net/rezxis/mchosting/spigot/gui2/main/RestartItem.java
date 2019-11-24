package net.rezxis.mchosting.spigot.gui2.main;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.gson.Gson;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.gui.GUIAction;
import net.rezxis.mchosting.gui.GUIItem;
import net.rezxis.mchosting.network.packet.sync.SyncRebootServer;
import net.rezxis.mchosting.spigot.RezxisMCHosting;

public class RestartItem extends GUIItem {

	public RestartItem() {
		super(getIcon());
	}
	
	private static ItemStack getIcon() {
		ItemStack is = new ItemStack(Material.DIAMOND_AXE);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.AQUA+"再起動");
		is.setItemMeta(im);
		return is;
	}

	@Override
	public GUIAction invClick(InventoryClickEvent e) {
		e.getWhoClicked().sendMessage(ChatColor.AQUA+"再起動中");
		SyncRebootServer packet = new SyncRebootServer(e.getWhoClicked().getUniqueId().toString());
		RezxisMCHosting.getConn().send(new Gson().toJson(packet));
		return GUIAction.CLOSE;
	}
}
