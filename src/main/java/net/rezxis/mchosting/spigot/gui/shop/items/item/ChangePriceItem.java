package net.rezxis.mchosting.spigot.gui.shop.items.item;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.database.object.server.DBShopItem;
import net.rezxis.mchosting.gui.GUIAction;
import net.rezxis.mchosting.gui.GUIItem;
import net.wesjd.anvilgui.AnvilGUI;
import net.rezxis.mchosting.spigot.RezxisMCHosting;

public class ChangePriceItem extends GUIItem {

	private DBShopItem item;

	public ChangePriceItem(DBShopItem item) {
		super(getIcon());
		this.item = item;
	}
	
	private static ItemStack getIcon() {
		ItemStack is = new ItemStack(Material.GOLD_NUGGET);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.YELLOW+"値段を変更");
		is.setItemMeta(im);
		return is;
	}

	@SuppressWarnings("deprecation")
	@Override
	public GUIAction invClick(InventoryClickEvent e) {
		Bukkit.getScheduler().scheduleAsyncDelayedTask(RezxisMCHosting.instance, new Runnable() {
			public void run() {
		new AnvilGUI.Builder()
		.onClose(pl -> {})
		.onComplete((pl,text) -> {
			if (text == null || text.equalsIgnoreCase("")) {
				pl.sendMessage(ChatColor.RED+"値段を入力してください。");
				new ShopItemMenu(pl,item).delayShow();
				return AnvilGUI.Response.close();
			}
			try {
				int value = Integer.valueOf(text);
				if (value < 0) {
					pl.sendMessage(ChatColor.RED+"値段を入力してください。");
					new ShopItemMenu(pl,item).delayShow();
					return AnvilGUI.Response.close();
				}
				item.setPrice(value);
				item.update();
			} catch (Exception ex) {
				pl.sendMessage(ChatColor.RED+"値段を入力してください。");
				new ShopItemMenu(pl,item).delayShow();
				return AnvilGUI.Response.close();
			}
			new ShopItemMenu(pl,item).delayShow();
			return AnvilGUI.Response.close();
		})
		.text(String.valueOf(item.getPrice()))
		.plugin(RezxisMCHosting.instance)
		.open((Player) e.getWhoClicked());
			}},2);
		return GUIAction.CLOSE;
	}
}
