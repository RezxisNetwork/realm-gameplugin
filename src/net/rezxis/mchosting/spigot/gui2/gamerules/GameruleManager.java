package net.rezxis.mchosting.spigot.gui2.gamerules;

import java.util.HashMap;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.gui.GUIItem;
import net.rezxis.mchosting.gui.GUIWindow;
import net.rezxis.mchosting.gui.ItemBack;
import net.rezxis.mchosting.spigot.RezxisMCHosting;

public class GameruleManager extends GUIWindow {

	private Player player;
	private GUIWindow back;
	
	public GameruleManager(Player player, GUIWindow back) {
		super(player, ChatColor.AQUA+"Gamerule Helper", 3, RezxisMCHosting.instance);
		this.player = player;
		this.back = back;
	}
	
	@Override
	public HashMap<Integer, GUIItem> getOptions() {
		HashMap<Integer, GUIItem> map = new HashMap<>();
		setItem(0, new ItemBack(back), map);
		int i = 1;
		for (String key : player.getWorld().getGameRules()) {
			try {
				Boolean.valueOf(player.getWorld().getGameRuleValue(key));
				setItem(i, new GameruleItem(this.player, key), map);
				++i;
			} catch (Exception ex) {}
		}
		return map;
	}
}
