package net.rezxis.mchosting.spigot.gui2.plugins.config;

import java.io.File;
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
	
	public ConfigManagerMenu(Player player, File file, GUIWindow back, boolean isRoot) {
		super(player, "Config Manager", 6, RezxisMCHosting.instance);
		this.file = file;
		this.back = back;
		this.isRoot = isRoot;
	}

	@Override
	public HashMap<Integer, GUIItem> getOptions() {
		HashMap<Integer, GUIItem> map = new HashMap<>();
		map.put(0, new ItemBack(back));
		boolean managable = Tables.getPTable().get(RezxisMCHosting.getDBServer().getOwner()).getRank().getPluginUpload();
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
		for (File f : file.listFiles()) {
			if (!(f.getName().contains("database.propertis") || f.getName().contains("hosting.propertis"))) {
				if (!f.getName().contains("GamePlugin.jar")) {
					if (!f.getName().contains(".jar") ) {
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
				}
			}
		}
		return map;
	}
}
