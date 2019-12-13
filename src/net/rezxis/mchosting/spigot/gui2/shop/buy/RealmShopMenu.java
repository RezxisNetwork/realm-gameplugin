package net.rezxis.mchosting.spigot.gui2.shop.buy;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;

import net.rezxis.mchosting.database.DBServer;
import net.rezxis.mchosting.database.ShopItem;
import net.rezxis.mchosting.gui.GUIItem;
import net.rezxis.mchosting.gui.GUIWindow;
import net.rezxis.mchosting.spigot.RezxisMCHosting;

public class RealmShopMenu extends GUIWindow {

	private Player player;
	private int page;
	private boolean preview;
	
	public RealmShopMenu(Player player, int page, boolean preview) {
		super(player, "Realm Shop", 6, RezxisMCHosting.instance);
		this.player = player;
		this.page = page;
		this.preview = preview;
	}

	@Override
	public HashMap<Integer, GUIItem> getOptions() {
		HashMap<Integer,GUIItem> map = new HashMap<>();
		setItem(0, new YourCoinItem(player), map);
		DBServer server = RezxisMCHosting.getDBServer();
		ArrayList<ShopItem> items = server.getShop().getItems();
		if (items.size() > 21*page)
			setItem(8,5, new NextPageItem(page, preview), map);
		if (page > 1) {
			setItem(0,5, new BackPageItem(page, preview), map);
		}
		int sIndex = 0 + 21*(page-1);//=<20
		int a = 0;
		for (int i = sIndex; i <= sIndex+20; i++) {
			if (i == items.size())
				break;
			int x = a % 7;
			int y = (a-x)/7;
			setItem(x+1, y+1, new MenuShopItem(items.get(i), preview), map);
			a++;
		}
		return map;
	}
}
