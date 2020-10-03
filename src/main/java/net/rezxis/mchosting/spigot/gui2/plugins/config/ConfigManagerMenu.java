package net.rezxis.mchosting.spigot.gui2.plugins.config;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.entity.Player;

import net.rezxis.mchosting.database.Tables;
import net.rezxis.mchosting.gui.GUIItem;
import net.rezxis.mchosting.gui.GUIWindow;
import net.rezxis.mchosting.gui.ItemBack;
import net.rezxis.mchosting.spigot.RezxisMCHosting;

public class ConfigManagerMenu extends GUIWindow {

	private File file;
	private GUIWindow back;
	private boolean isRoot;
	private int page;
	private static String[] blacklisted = new String[] {"RezxisSQLPlugin","GamePlugin","database.yml","RezxisSQL",".jar"};
	
	public ConfigManagerMenu(Player player, File file, GUIWindow back, boolean isRoot, int page) {
		super(player, "Config Manager", 6, RezxisMCHosting.instance);
		this.file = file;
		this.back = back;
		this.isRoot = isRoot;
		this.page = page;
	}

	@Override
	public HashMap<Integer, GUIItem> getOptions() {
		HashMap<Integer, GUIItem> map = new HashMap<>();
		map.put(0, new ItemBack(back));
		boolean managable = Tables.getPTable().get(RezxisMCHosting.getDBServer(false).getOwner()).getRank().getPluginUpload();
		int i = 1;
		if (!isRoot) {
			map.put(1, new DeleteDirectoryItem(file));
			map.put(2, new CreateDirectoryItem(file, this));
			map.put(3, new CreateFileItem(file, this));
			i = 4;
		} else if (managable) {
			map.put(1, new DeleteDirectoryItem(file));
			map.put(2, new CreateDirectoryItem(file, this));
			map.put(3, new CreateFileItem(file, this));
			i = 4;
		}
		
		ArrayList<File> fRoot = new ArrayList<>();
		for (File ff : file.listFiles()) {
			boolean flag = false;
			for (String s : blacklisted)
				if (ff.getName().contains(s))
					flag = true;
			if (!flag) {
				fRoot.add(ff);
			}
		}
		File[] files = fRoot.toArray(new File[fRoot.size()]);
		int end = files.length;
		int from = 0;
		if (page != 0) {
			from += 49;
			for (int z = 0; z < page-1; z++) {
				from += 48;
			}
		}
		if (end > 50) {
			if (page == 0) {
				end = from + 49;
			} else {
				end = from + 48;
			}
			if (end > files.length - 1) {
				end = files.length - 1;
			}
		}
		int z = from;
		while (z < end) {
			if (files.length > 50) {
				if ((page != 0 && (i == 45 || i == 53)) || (page == 0 && i == 53)) {
					i++;
					continue;
				}
			}
			File f = files[z];
			System.out.println(files[z]);
			if (!f.getName().contains(".jar")) {
				if (f.isDirectory()) {
					map.put(i, new DirectoryItem(f,this));
				} else {
					map.put(i, new ConfigItem(f,this));
				}
				i++;
			} else if (managable) {
				if (f.isDirectory()) {
					map.put(i, new DirectoryItem(f,this));
				} else {
					map.put(i, new ConfigItem(f,this));
				}
				i++;
			}
			z++;
		}
		if (files.length > 50) {
			if (page != 0) {
				map.put(45, new BackDirectoryItem(file, back, isRoot, page));
			}
			int endi;
			if (page == 0) {
				endi = from + 49;
			} else {
				endi = from + 48;
			}
			if (endi <= files.length - 1)
				map.put(53, new NextDirectoryItem(file, back, isRoot, page));
		}
		return map;
	}
}
