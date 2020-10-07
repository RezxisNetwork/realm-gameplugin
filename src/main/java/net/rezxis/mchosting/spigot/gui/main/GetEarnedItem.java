package net.rezxis.mchosting.spigot.gui.main;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.database.Tables;
import net.rezxis.mchosting.database.object.player.DBPlayer;
import net.rezxis.mchosting.database.object.server.DBServer;
import net.rezxis.mchosting.database.object.server.DBShopItem;
import net.rezxis.mchosting.gui.GUIAction;
import net.rezxis.mchosting.gui.GUIItem;
import net.rezxis.mchosting.spigot.RezxisMCHosting;

public class GetEarnedItem extends GUIItem {
	
	public GetEarnedItem() {
		super(getIcon());
	}
	
	private static ItemStack getIcon() {
		ItemStack is = new ItemStack(Material.GOLD_NUGGET);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.YELLOW+"収益を回収");
		ArrayList<String> lore = new ArrayList<>();
		lore.add(ChatColor.GRAY+"shopで稼いだ利益を回収します。");
		int coin = 0;
		for (DBShopItem item : Tables.getSiTable().getShopItems(RezxisMCHosting.getDBServer(false).getId())) {
			coin += item.getEarned();
		}
		lore.add(ChatColor.GREEN+"利益: "+ChatColor.GOLD+ChatColor.UNDERLINE+coin+ChatColor.RESET+" "+ChatColor.GOLD+"Realm"+ChatColor.AQUA+"Coins");
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	@Override
	public GUIAction invClick(InventoryClickEvent e) {
		int coin = 0;
		for (DBShopItem item : Tables.getSiTable().getShopItems(RezxisMCHosting.getDBServer(false).getId())) {
			coin += item.getEarned();
			item.setEarned(0);
			item.update();
		}
		if (coin == 0) {
			e.getWhoClicked().sendMessage(ChatColor.RED+"利益は0なので回収できません。");
		} else {
			DBPlayer dp = Tables.getPTable().get(e.getWhoClicked().getUniqueId());
			dp.addCoin(coin);
			dp.update();
			e.getWhoClicked().sendMessage(""+ChatColor.AQUA+coin+ChatColor.GOLD+"Realm"+ChatColor.AQUA+"Coins"+ChatColor.AQUA+"回収されました。");
		}
		return GUIAction.UPDATE;
	}
}
