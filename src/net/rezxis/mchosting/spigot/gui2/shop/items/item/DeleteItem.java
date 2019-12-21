package net.rezxis.mchosting.spigot.gui2.shop.items.item;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.database.object.server.DBServer;
import net.rezxis.mchosting.database.object.server.ShopItem;
import net.rezxis.mchosting.gui.GUIAction;
import net.rezxis.mchosting.gui.GUIItem;
import net.rezxis.mchosting.spigot.RezxisMCHosting;
import net.rezxis.mchosting.spigot.gui2.shop.ShopMenu;

public class DeleteItem extends GUIItem {

	private ShopItem item;
	
	public DeleteItem(ShopItem item) {
		super(getIcon());
		this.item = item;
	}
	
	private static ItemStack getIcon() {
		ItemStack is = new ItemStack(Material.BARRIER);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.RED+"削除");
		is.setItemMeta(im);
		return is;
	}

	@Override
	public GUIAction invClick(InventoryClickEvent e) {
		DBServer server = RezxisMCHosting.getDBServer();
		server.getShop().removeItem(item);
		server.update();
		new ShopMenu((Player) e.getWhoClicked()).delayShow();
		return GUIAction.CLOSE;
	}
}
