package net.rezxis.mchosting.spigot.gui2.main;

import java.util.HashMap;

import org.bukkit.entity.Player;

import net.rezxis.mchosting.gui.GUIItem;
import net.rezxis.mchosting.gui.GUIWindow;
import net.rezxis.mchosting.spigot.RezxisMCHosting;

public class RealmMenu extends GUIWindow {

	public RealmMenu(Player player) {
		super(player, "Realm Settings", 6, RezxisMCHosting.instance);
	}

	@Override
	public HashMap<Integer, GUIItem> getOptions() {
		HashMap<Integer, GUIItem> map = new HashMap<>();
		setItem(0, 0, new ToggleVisibleItem(), map);
		setItem(0, 1, new OfflineBootItem(), map);
		setItem(8, 0, new DirectConnectItem(), map);
		setItem(4, 0, new OpenBuyRankMenu(), map);
		setItem(2, 2, new ResetOPItem(), map);
		setItem(4, 2, new PluginManagerItem(this), map);
		setItem(4, 3, new ChangeVoteListener(), map);
		setItem(3, 3, new OpenGameRuleItem(this), map);
		setItem(6, 2, new ServerIconItem(), map);
		setItem(5, 3, new MotdItem(), map);
		setItem(2, 4, new ConfigItem(this), map);
		setItem(6, 4, new ShopMenuItem(), map);
		setItem(5, 4, new TextureItem(), map);
		setItem(4, 4, new GetEarnedItem(), map);
		setItem(8, 5, new RestartItem(), map);
		setItem(8, 4, new StopItem(), map);
		setItem(8, 3, new OpenSkriptItem(), map);
		return map;
	}
}
