package net.rezxis.mchosting.spigot.gui.plugins.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import net.rezxis.mchosting.gui.BackItem;
import net.rezxis.mchosting.gui.ExecutableItemStack;
import net.rezxis.mchosting.gui.GuiPresenter;

public class ConfigManagerGui implements GuiPresenter {

	private File file;
	private boolean isRoot;
	private GuiPresenter back;
	
	public ConfigManagerGui(GuiPresenter back, File file, boolean isRoot) {
		this.file = file;
		this.isRoot = isRoot;
		this.back = back;
	}
	
	@Override
	public List<ExecutableItemStack> getOptions(Player player) {
		ArrayList<ExecutableItemStack> items = new ArrayList<>();
		items.add(new BackItem(back));
		if (!isRoot) {
			//delete folder
			items.add(new DeleteThisDirectory(file));
			items.add(new CreateDirectory(this,file));
			items.add(new CreateFile(this,file));
		}
		for (File f : file.listFiles()) {
			if (!f.getName().contains("database.propertis")) {
				if (!f.getName().contains(".jar") ) {
					if (f.isDirectory()) {
						items.add(new DirectoryItem(this,f));
					} else {
						items.add(new ConfigItem(f,this));
					}
				}
			}
		}
		return items;
	}

	@Override
	public String getLabel() {
		return "Config Manager";
	}

	@Override
	public String getEmptyMessage() {
		return "Something went to worng";
	}

}
