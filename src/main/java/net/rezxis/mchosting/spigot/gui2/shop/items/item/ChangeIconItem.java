package net.rezxis.mchosting.spigot.gui2.shop.items.item;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.database.object.server.ShopItem;
import net.rezxis.mchosting.gui.GUIAction;
import net.rezxis.mchosting.gui.GUIItem;
import net.rezxis.mchosting.spigot.RezxisMCHosting;

public class ChangeIconItem extends GUIItem {

	private ShopItem item;
	
	public ChangeIconItem(ShopItem item) {
		super(getIcon(item));
		this.item = item;
	}
	
	private static ItemStack getIcon(ShopItem item) {
		ItemStack is = new ItemStack(Material.valueOf(item.getItemType()));
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
		item.setItemType(player.getItemInHand().getType().name());
		RezxisMCHosting.getDBServer(false).update();
		player.sendMessage(ChatColor.AQUA+"アイコンを変更しました！");
		return GUIAction.UPDATE;
	}
}
