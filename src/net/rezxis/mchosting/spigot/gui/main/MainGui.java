package net.rezxis.mchosting.spigot.gui.main;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.rezxis.mchosting.gui.EmptyItem;
import net.rezxis.mchosting.gui.ExecutableItemStack;
import net.rezxis.mchosting.gui.GuiPresenter;

public class MainGui implements GuiPresenter {

	@Override
	public List<ExecutableItemStack> getOptions(Player player) {
		ArrayList<ExecutableItemStack> items = new ArrayList<>();
		items.add(new EmptyItem());
		items.add(new EmptyItem());
		items.add(new EmptyItem());
		items.add(new EmptyItem());
		items.add(new PluginManagerItem());
		if (Bukkit.getPluginManager().getPlugin("Skript") != null) {
			items.add(new Skript(this));
		} else {
			items.add(new EmptyItem());
		}
		items.add(new EmptyItem());
		items.add(new EmptyItem());
		items.add(new NetworkStatusItem());
		items.add(new ResetOPItem());
		items.add(new EmptyItem());
		items.add(new EmptyItem());
		items.add(new ServerRebootItem());
		items.add(new ServerStopItem());
		items.add(new WhiteListManagerItem());
		items.add(new EmptyItem());
		items.add(new EmptyItem());
		items.add(new ServerStatusItem());
		return items;
	}

	@Override
	public String getLabel() {
		return "Server Manager";
	}

	@Override
	public String getEmptyMessage() {
		return "Something went to worng";
	}
}
