package net.rezxis.mchosting.spigot.gui2.shop.items;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.databse.ShopItem;
import net.rezxis.mchosting.gui.GUIAction;
import net.rezxis.mchosting.gui.GUIItem;
import net.rezxis.mchosting.spigot.gui2.shop.items.item.ShopItemMenu;

public class ItemManageItem extends GUIItem {

	private ShopItem item;
	
	public ItemManageItem(ShopItem item) {
		super(getIcon(item));
		this.item = item;
	}
	
	private static ItemStack getIcon(ShopItem item) {
		ItemStack is = new ItemStack(Material.valueOf(item.getItemType()));
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(item.getName());
		ArrayList<String> lore = new ArrayList<>();
		lore.add(ChatColor.YELLOW+"クリックで編集");
		lore.add(ChatColor.YELLOW+"値段:"+ChatColor.GOLD+ChatColor.UNDERLINE+item.getPrice()+ChatColor.RESET+" "+ChatColor.AQUA+"Realm"+ChatColor.GOLD+"Coin");
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	@Override
	public GUIAction invClick(InventoryClickEvent e) {
		new ShopItemMenu((Player) e.getWhoClicked(), item).delayShow();;
		return GUIAction.CLOSE;
	}
}
