package net.rezxis.mchosting.spigot.gui2.shop.buy;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.database.DBPlayer;
import net.rezxis.mchosting.database.ShopItem;
import net.rezxis.mchosting.gui.GUIAction;
import net.rezxis.mchosting.gui.GUIItem;
import net.rezxis.mchosting.spigot.RezxisMCHosting;

public class MenuShopItem extends GUIItem {

	private ShopItem item;
	private boolean preview;
	
	public MenuShopItem(ShopItem item, boolean preview) {
		super(getIcon(item));
		this.item = item;
		this.preview = preview;
	}
	
	private static ItemStack getIcon(ShopItem item) {
		ItemStack is = new ItemStack(Material.valueOf(item.getItemType()));
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(item.getName());
		ArrayList<String> lore = new ArrayList<>();
		lore.add(ChatColor.YELLOW+"値段: "+ChatColor.GOLD+ChatColor.UNDERLINE+item.getPrice()+ChatColor.RESET+" "+ChatColor.GOLD+"Realm"+ChatColor.AQUA+"Coins");
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	@Override
	public GUIAction invClick(InventoryClickEvent e) {
		if (preview)
			return GUIAction.NO_ACTION;
		Player player = (Player) e.getWhoClicked();
		DBPlayer dp = RezxisMCHosting.getPTable().get(player.getUniqueId());
		if (dp.getCoin() < item.getPrice()) {
			player.sendMessage(ChatColor.RED+"Coinが足りません。");
			return GUIAction.CLOSE;
		}
		dp.setCoin(dp.getCoin() - item.getPrice());
		dp.update();
		item.setEarned(item.getEarned()+item.getPrice());
		RezxisMCHosting.getDBServer().update();
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), item.getCMD().replace("[player]", player.getName()));
		player.sendMessage(item.getName()+ChatColor.GREEN+"を購入しました。");
		return GUIAction.UPDATE;
	}
}
