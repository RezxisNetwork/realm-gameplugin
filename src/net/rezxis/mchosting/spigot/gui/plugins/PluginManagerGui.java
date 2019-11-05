package net.rezxis.mchosting.spigot.gui.plugins;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.entity.Player;

import net.rezxis.mchosting.databse.DBPlugin;
import net.rezxis.mchosting.gui.BackItem;
import net.rezxis.mchosting.gui.ExecutableItemStack;
import net.rezxis.mchosting.gui.GuiPresenter;
import net.rezxis.mchosting.spigot.RezxisMCHosting;
import net.rezxis.mchosting.spigot.gui.main.MainGui;

public class PluginManagerGui implements GuiPresenter {

	@Override
	public List<ExecutableItemStack> getOptions(Player player) {
		ArrayList<ExecutableItemStack> items = new ArrayList<>();
		items.add(new BackItem(new MainGui()));
		String[] ignore = new String[] {"RezxisMCHostingGUI","RezxisMCHosting"};
		items.add(new ManageConfig(this));
		for (Entry<String,DBPlugin> e : RezxisMCHosting.instance.plTable.getPlugins().entrySet()) {
			boolean flag = false;
			for (String s : ignore) {
				if (e.getKey().equalsIgnoreCase(s))
					flag = true;
			}
			if (!flag)
				items.add(new PluginItem(e.getValue()));
		}
		return items;
	}

	@Override
	public String getLabel() {
		return "Plugin Manager";
	}

	@Override
	public String getEmptyMessage() {
		return "Something went to worng";
	}

}
