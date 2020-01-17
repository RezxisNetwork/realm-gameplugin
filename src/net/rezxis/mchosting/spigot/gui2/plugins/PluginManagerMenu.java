package net.rezxis.mchosting.spigot.gui2.plugins;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.database.Tables;
import net.rezxis.mchosting.database.object.server.DBPlugin;
import net.rezxis.mchosting.database.object.server.DBServer;
import net.rezxis.mchosting.gui.GUIItem;
import net.rezxis.mchosting.gui.GUIWindow;
import net.rezxis.mchosting.gui.ItemBack;
import net.rezxis.mchosting.spigot.RezxisMCHosting;

public class PluginManagerMenu extends GUIWindow {

	private int page;
	private GUIWindow back;
	
	public PluginManagerMenu(Player player,GUIWindow back, int page) {
		super(player, ChatColor.LIGHT_PURPLE+"Plugin Helper", 6, RezxisMCHosting.instance);
		this.page = page;
		this.back = back;
	}

	@Override
	public HashMap<Integer, GUIItem> getOptions() {
		HashMap<Integer,GUIItem> map = new HashMap<>();
		setItem(4, new PluginStatus(), map);
		setItem(0, new ItemBack(back), map);
		ArrayList<DBPlugin> plugins = new ArrayList<DBPlugin>(Tables.getPlTable().getPlugins().values());
		Collections.sort(plugins, new Sort());
		if (page > 1) {
			setItem(0, 5, new BackPluginPage(page,back), map);
		}
		if (plugins.size() > 28*page) {
			setItem(8, 5, new NextPluginPage(page,back), map);
		}
		int sIndex = 0 + 28*(page-1);
		int a = 0;
		String[] str = new String[] {"REZXISMCHOSTING"};
		DBServer ds = RezxisMCHosting.getDBServer(false);
		for (int i = sIndex; i <= sIndex+27; i++) {
			if (i == plugins.size())
				break;
			for (String s : str) {
				if (plugins.get(i).getName().equalsIgnoreCase(s))
					continue;
			}
			int x = a % 7;
			int y = (a-x)/7;
			setItem(x+1, y+1, new PluginItem(plugins.get(i),ds), map);
			a++;
		}
		return map;
	}
	
	public class Sort implements Comparator<DBPlugin> {
	    public int compare(DBPlugin o1, DBPlugin o2) {
	        return o1.getName().compareTo(o2.getName());
	    }
	} 
}
