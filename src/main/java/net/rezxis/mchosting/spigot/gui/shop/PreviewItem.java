package net.rezxis.mchosting.spigot.gui.shop;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.gui.GUIAction;
import net.rezxis.mchosting.gui.GUIItem;
import net.rezxis.mchosting.spigot.gui.shop.buy.RealmShopMenu;

public class PreviewItem extends GUIItem {

	public PreviewItem() {
		super(getIcon());
	}
	
	private static ItemStack getIcon() {
		ItemStack is = new ItemStack(Material.LAVA_BUCKET);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.GREEN+"ショップを表示");
		is.setItemMeta(im);
		return is;
	}

	@Override
	public GUIAction invClick(InventoryClickEvent e) {
		new RealmShopMenu((Player) e.getWhoClicked(),1,true).delayShow();
		return GUIAction.CLOSE;
	}
}
