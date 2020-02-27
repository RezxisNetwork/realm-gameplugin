package net.rezxis.mchosting.spigot.gui2.ranks;

import java.util.HashMap;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.gui.GUIItem;
import net.rezxis.mchosting.gui.GUIWindow;
import net.rezxis.mchosting.spigot.RezxisMCHosting;

public class BuyRankMenu extends GUIWindow {

	private Player player;
	
	public BuyRankMenu(Player player) {
		super(player, ChatColor.RED+"Rankを購入", 6, RezxisMCHosting.instance);
		this.player = player;
	}

	@Override
	public HashMap<Integer, GUIItem> getOptions() {
		HashMap<Integer, GUIItem> map = new HashMap<>();
		setItem(0, 0, new YourCoinItem(this.player), map);
		setItem(4, 3, new EmeraldRankItem(), map);
		setItem(2, 4, new GoldRankItem(), map);
		setItem(6, 4, new DiamondRankItem(), map);
		return map;
	}
}
