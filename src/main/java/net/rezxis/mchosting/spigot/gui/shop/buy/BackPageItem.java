package net.rezxis.mchosting.spigot.gui.shop.buy;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.gui.GUIAction;
import net.rezxis.mchosting.gui.GUIItem;

public class BackPageItem extends GUIItem {

	private int page;
	private boolean preview;
	
	public BackPageItem(int page, boolean preview) {
		super (getIcon());
		this.page = page;
		this.preview = preview;
	}
	
	private static ItemStack getIcon() {
		ItemStack is = new ItemStack(Material.ARROW);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.LIGHT_PURPLE+"Previous Page");
		is.setItemMeta(im);
		return is;
	}

	@Override
	public GUIAction invClick(InventoryClickEvent e) {
		new RealmShopMenu((Player)e.getWhoClicked(),page+1,preview).delayShow();
		return GUIAction.CLOSE;
	}
}
