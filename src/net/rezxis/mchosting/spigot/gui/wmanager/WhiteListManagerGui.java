package net.rezxis.mchosting.spigot.gui.wmanager;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import net.rezxis.mchosting.gui.BackItem;
import net.rezxis.mchosting.gui.EmptyItem;
import net.rezxis.mchosting.gui.ExecutableItemStack;
import net.rezxis.mchosting.gui.GuiPresenter;
import net.rezxis.mchosting.spigot.gui.main.MainGui;
import net.rezxis.mchosting.spigot.gui.main.NetworkStatusItem;
import net.rezxis.mchosting.spigot.gui.main.PluginManagerItem;
import net.rezxis.mchosting.spigot.gui.main.ResetOPItem;
import net.rezxis.mchosting.spigot.gui.main.ServerStatusItem;
import net.rezxis.mchosting.spigot.gui.main.ServerStopItem;

public class WhiteListManagerGui implements GuiPresenter  {

	@Override
	public List<ExecutableItemStack> getOptions(Player player) {
		ArrayList<ExecutableItemStack> items = new ArrayList<>();
		items.add(new BackItem(new MainGui()));
		items.add(new WhitelistStatusItem());
		items.add(new WhitelistAddItem());
		for (OfflinePlayer p : Bukkit.getWhitelistedPlayers()) {
			items.add(new PlayerHeadItem(p));
		}
		return items;
	}

	@Override
	public String getLabel() {
		return "Whitelist Manager";
	}

	@Override
	public String getEmptyMessage() {
		return "Something went to worng";
	}
}
