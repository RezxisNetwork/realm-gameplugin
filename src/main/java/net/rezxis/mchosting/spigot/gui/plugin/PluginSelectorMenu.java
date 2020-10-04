package net.rezxis.mchosting.spigot.gui.plugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.database.Tables;
import net.rezxis.mchosting.database.object.server.DBServerPluginLink;
import net.rezxis.mchosting.gui.GUIItem;
import net.rezxis.mchosting.gui.GUIWindow;
import net.rezxis.mchosting.gui.ItemBack;
import net.rezxis.mchosting.spigot.RezxisMCHosting;

public class PluginSelectorMenu extends GUIWindow {

	private int page;
	private GUIWindow back;
	private static String[] blacklisted = new String[] {"RezxisMCHosting","RezxisSQL"};
	
	public PluginSelectorMenu(Player player, GUIWindow back, int page) {
		super(player, ChatColor.LIGHT_PURPLE+"Plugin Helper", 6, RezxisMCHosting.instance);
		this.back = back;
		this.page = page;
		
	}

	@Override
	public HashMap<Integer, GUIItem> getOptions() {
		HashMap<Integer, GUIItem> map = new HashMap<>();
		setItem(4, new PluginStatus(), map);
		setItem(0, new ItemBack(back), map);
		ArrayList<String> plugins = new ArrayList<>();
		for (String s : Tables.getPlTable().getNames()) {
			boolean flag = true;
			for (String z : blacklisted)
				if (z.equalsIgnoreCase(s))
					flag = false;
			if (flag)
				plugins.add(s);
		}
		Collections.sort(plugins, new Sort());
		if (page > 1) {
			setItem(0, 5, new BackPluginPage(page,back), map);
		}
		if (plugins.size() > 28*page) {
			setItem(8, 5, new NextPluginPage(page,back), map);
		}
		int sIndex = 0 + 28*(page-1);
		int a = 0;
		for (int i = sIndex; i <= sIndex+27; i++) {
			if (i == plugins.size())
				break;
			int x = a % 7;
			int y = (a-x)/7;
			boolean enabled = false;
			DBServerPluginLink link = Tables.getSplTable().getLink(RezxisMCHosting.getDBServer(false).getId(), plugins.get(i));
			if (link != null) {
				enabled = link.isEnabled();
			}
			setItem(x+1, y+1, new PluginItem(plugins.get(i),this, enabled), map);
			a++;
		}
		return map;
	}
	
	public class Sort implements Comparator<String> {
	    public int compare(String o1, String o2) {
	        return o1.compareTo(o2);
	    }
	} 
}
