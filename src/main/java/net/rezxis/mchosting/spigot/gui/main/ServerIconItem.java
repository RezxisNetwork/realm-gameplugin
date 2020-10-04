package net.rezxis.mchosting.spigot.gui.main;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.database.object.server.DBServer;
import net.rezxis.mchosting.gui.GUIAction;
import net.rezxis.mchosting.gui.GUIItem;
import net.rezxis.mchosting.spigot.RezxisMCHosting;

public class ServerIconItem extends GUIItem {
	
	public ServerIconItem() {
		super(getIcon(RezxisMCHosting.getDBServer(false)));
	}
	
	private static ItemStack getIcon(DBServer server) {
		ItemStack is = new ItemStack(Material.valueOf(server.getIcon()));
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.GREEN+"サーバーアイコンを変更");
		ArrayList<String> lore = new ArrayList<>();
		lore.add(ChatColor.AQUA+"アイコンにしたいアイテムを手に持ってください");
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	@SuppressWarnings("deprecation")
	@Override
	public GUIAction invClick(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();
		if (player.getItemInHand().getType() == Material.AIR) {
			player.sendMessage(ChatColor.RED+"アイテムを手に持ってください。");
			return GUIAction.NO_ACTION;
		}
		DBServer server = RezxisMCHosting.getDBServer(false);
		server.setIcon(player.getItemInHand().getType().name());
		server.update();
		player.sendMessage(ChatColor.AQUA+"アイコンを変更しました！");
		return GUIAction.UPDATE;
	}
}
