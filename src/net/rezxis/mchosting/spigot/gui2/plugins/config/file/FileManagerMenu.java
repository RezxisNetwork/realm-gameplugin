package net.rezxis.mchosting.spigot.gui2.plugins.config.file;

import java.io.File;
import java.util.HashMap;

import org.bukkit.entity.Player;

import net.rezxis.mchosting.gui.GUIItem;
import net.rezxis.mchosting.gui.GUIWindow;
import net.rezxis.mchosting.gui.ItemBack;
import net.rezxis.mchosting.spigot.RezxisMCHosting;

public class FileManagerMenu extends GUIWindow {

	private File file;
	private GUIWindow back;
	
	public FileManagerMenu(Player player, File file, GUIWindow back) {
		super(player, "Manage File", 1, RezxisMCHosting.instance);
		this.file = file;
		this.back = back;
	}

	@Override
	public HashMap<Integer, GUIItem> getOptions() {
		HashMap<Integer, GUIItem> map = new HashMap<>();
		map.put(0, new ItemBack(back));
		map.put(1, new FileInfoItem(file));
		map.put(3, new DeleteFileItem(file, this));
		map.put(4, new DownloadFileItem(file, this));
		map.put(5, new UploadFileItem(file, this));
		return map;
	}
}
