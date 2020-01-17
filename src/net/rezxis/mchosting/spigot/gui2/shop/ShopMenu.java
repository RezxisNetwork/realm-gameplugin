package net.rezxis.mchosting.spigot.gui2.shop;

import java.util.HashMap;

import org.bukkit.entity.Player;

import net.rezxis.mchosting.gui.GUIItem;
import net.rezxis.mchosting.gui.GUIWindow;
import net.rezxis.mchosting.spigot.RezxisMCHosting;

public class ShopMenu extends GUIWindow {

	public ShopMenu(Player player) {
		super(player, "ショップを編集", 1, RezxisMCHosting.instance);
	}

	@Override
	public HashMap<Integer, GUIItem> getOptions() {
		HashMap<Integer, GUIItem> map = new HashMap<>();
		setItem(0, new AddItem(), map);
		if (RezxisMCHosting.getDBServer(false).getShop().getItems().size() != 0) {
			setItem(1, new PreviewItem(), map);
			setItem(2, new ManageItem(), map);
		}
		return map;
	}
}
