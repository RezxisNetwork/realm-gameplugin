package net.rezxis.mchosting.spigot.gui.plugin.versions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.database.Tables;
import net.rezxis.mchosting.database.object.server.DBPlugin;
import net.rezxis.mchosting.database.object.server.DBServerPluginLink;
import net.rezxis.mchosting.gui.GUIItem;
import net.rezxis.mchosting.gui.GUIWindow;
import net.rezxis.mchosting.gui.ItemBack;
import net.rezxis.mchosting.spigot.RezxisMCHosting;

public class VersionSelectorMenu extends GUIWindow {

	private int page;
	private GUIWindow back;
	private String pname;
	
	public VersionSelectorMenu(Player player,GUIWindow back, String name, int page) {
		super(player, ChatColor.LIGHT_PURPLE+name, 6, RezxisMCHosting.instance);
		this.pname = name;
		this.page = page;
		this.back = back;
	}

	@Override
	public HashMap<Integer, GUIItem> getOptions() {
		HashMap<Integer,GUIItem> map = new HashMap<>();
		setItem(0, new ItemBack(back), map);
		ArrayList<DBPlugin> plugins = new ArrayList<DBPlugin>();
		String[] str = new String[] {"REZXISMCHOSTING","REZXISQL"};
		for (DBPlugin plugin : Tables.getPlTable().get(pname)) {
			boolean flag = false;
			for (String s : str) {
				if (plugin.getName().equalsIgnoreCase(s)) {
					flag = true;
				}
			}
			if (!flag) {
				plugins.add(plugin);
			}
		}
		Collections.sort(plugins, new Sort());
		if (page > 1) {
			setItem(0, 5, new BackVersionsPage(page,back,pname), map);
		}
		if (plugins.size() > 28*page) {
			setItem(8, 5, new NextVersionsPage(page,back,pname), map);
		}
		int sIndex = 0 + 28*(page-1);
		int a = 0;
		for (int i = sIndex; i <= sIndex+27; i++) {
			if (i == plugins.size())
				break;
			int x = a % 7;
			int y = (a-x)/7;
			setItem(x+1, y+1, new VersionItem(plugins.get(i)), map);
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
