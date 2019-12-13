package net.rezxis.mchosting.spigot.gui2.shop.items;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;

import net.rezxis.mchosting.database.DBServer;
import net.rezxis.mchosting.database.ShopItem;
import net.rezxis.mchosting.gui.GUIItem;
import net.rezxis.mchosting.gui.GUIWindow;
import net.rezxis.mchosting.spigot.RezxisMCHosting;

public class ItemManageMenu extends GUIWindow {

	private int page;
	
	public ItemManageMenu(Player player, int page) {
		super(player, "Shop item editor", 6, RezxisMCHosting.instance);
		this.page = page;
	}

	@Override
	public HashMap<Integer, GUIItem> getOptions() {
		HashMap<Integer, GUIItem> map = new HashMap<>();
		DBServer server = RezxisMCHosting.getDBServer();
		ArrayList<ShopItem> items = server.getShop().getItems();
		if (items.size() > 21*page)
			setItem(8,5, new NextPageItem(page), map);
		if (page > 1) {
			setItem(0,5, new BackPageItem(page), map);
		}
		
		int sIndex = 0 + 21*(page-1);//=<20
		int a = 0;
		for (int i = sIndex; i <= sIndex+20; i++) {
			if (i == items.size())
				break;
			int x = a % 7;
			int y = (a-x)/7;
			setItem(x+1, y+1, new ItemManageItem(items.get(i)), map);
			a++;
		}
		return map;
	}
}
