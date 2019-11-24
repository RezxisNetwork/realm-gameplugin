package net.rezxis.mchosting.spigot.gui2.shop.items.item;

import java.util.HashMap;

import org.bukkit.entity.Player;

import net.rezxis.mchosting.databse.ShopItem;
import net.rezxis.mchosting.gui.GUIItem;
import net.rezxis.mchosting.gui.GUIWindow;
import net.rezxis.mchosting.spigot.RezxisMCHosting;

public class ShopItemMenu extends GUIWindow {

	private ShopItem item;
	
	public ShopItemMenu(Player player, ShopItem item) {
		super(player, item.getName(), 1, RezxisMCHosting.instance);
		this.item = item;
	}

	@Override
	public HashMap<Integer, GUIItem> getOptions() {
		HashMap<Integer, GUIItem> map = new HashMap<>();
		setItem(0, new RenameItem(item), map);
		setItem(1, new ChangePriceItem(item), map);
		setItem(4, new ChangeIconItem(item), map);
		setItem(7, new SetCommandItem(item), map);
		setItem(8, new DeleteItem(item), map);
		return map;
	}
}
