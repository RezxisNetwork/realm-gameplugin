package net.rezxis.mchosting.spigot.gui.shop;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.database.Tables;
import net.rezxis.mchosting.database.object.server.DBServer;
import net.rezxis.mchosting.database.object.server.DBShopItem;
import net.rezxis.mchosting.gui.GUIAction;
import net.rezxis.mchosting.gui.GUIItem;
import net.rezxis.mchosting.spigot.RezxisMCHosting;
import net.wesjd.anvilgui.AnvilGUI;

public class AddItem extends GUIItem {

	public AddItem() {
		super(getIcon());
	}
	
	private static ItemStack getIcon() {
		ItemStack is = new ItemStack(Material.CHEST);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.GREEN+"アイテムを追加");
		ArrayList<String> lore = new ArrayList<>();
		lore.add(ChatColor.GRAY+"ショップにアイテムを追加します");
		im.setLore(lore);
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
					if (text == null)
						return AnvilGUI.Response.close();
					if (text.equalsIgnoreCase(""))
						return AnvilGUI.Response.close();
					DBServer server = RezxisMCHosting.getDBServer(false);
					
					for (DBShopItem item : Tables.getSiTable().getShopItems(server.getId())) {
						if (item.getName().equalsIgnoreCase(text)) {
							pl.sendMessage(ChatColor.RED+"同じ名前は使えません。");
							new ShopMenu(pl).delayShow();
							return AnvilGUI.Response.close();
						}
					}
					text = text.replace("&", "§");
					DBShopItem item = new DBShopItem(-1, server.getId(), text, Material.APPLE.name(), "", 0, 0);
					item.insert();
					new ShopMenu(pl).delayShow();
					return AnvilGUI.Response.close();
				})
				.text("名前を入れてください")
				.plugin(RezxisMCHosting.instance)
				.open((Player) e.getWhoClicked());
			}
		}, 2);
		return GUIAction.CLOSE;
	}
}
