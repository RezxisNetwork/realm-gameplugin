package net.rezxis.mchosting.spigot.gui2.shop.items.item;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.databse.DBServer;
import net.rezxis.mchosting.databse.ShopItem;
import net.rezxis.mchosting.gui.GUIAction;
import net.rezxis.mchosting.gui.GUIItem;
import net.rezxis.mchosting.gui.anvil.AnvilGUI;
import net.rezxis.mchosting.spigot.RezxisMCHosting;

public class RenameItem extends GUIItem {

	private ShopItem item;
	
	public RenameItem(ShopItem item) {
		super(getIcon());
		this.item = item;
	}
	
	private static ItemStack getIcon() {
		ItemStack is = new ItemStack(Material.PAPER);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.AQUA+"名前を変更");
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
				pl.sendMessage(ChatColor.RED+"名前を入れてください。");
				new ShopItemMenu(pl,item).delayShow();
				return AnvilGUI.Response.close();
			}
			DBServer server = RezxisMCHosting.getDBServer();
			text = text.replace("&", "§");
			for (ShopItem item : server.getShop().getItems()) {
				if (item.getName().equalsIgnoreCase(text)) {
					pl.sendMessage(ChatColor.RED+"同じ名前は使えません。");
					new ShopItemMenu(pl,item).delayShow();
					return AnvilGUI.Response.close();
				}
			}
			item.setName(text);
			server.update();
			new ShopItemMenu(pl,item).delayShow();
			return AnvilGUI.Response.close();
		})
		.text(String.valueOf(item.getName()))
		.plugin(RezxisMCHosting.instance)
		.open((Player) e.getWhoClicked());
			}},2);
		return GUIAction.CLOSE;
	}
}
