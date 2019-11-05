package net.rezxis.mchosting.spigot.gui.plugins;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.gui.ExecutableItemStack;
import net.rezxis.mchosting.gui.GuiOpener;
import net.rezxis.mchosting.gui.GuiPresenter;
import net.rezxis.mchosting.gui.ItemExecution.ExecReturn;
import net.rezxis.mchosting.spigot.RezxisMCHosting;
import net.rezxis.mchosting.spigot.gui.plugins.config.ConfigManagerGui;

public class ManageConfig extends ExecutableItemStack {

	private GuiPresenter back;
	
	public ManageConfig(GuiPresenter back) {
		super(getIcon());
		this.back = back;
	}
	
	private static ItemStack getIcon() {
		ItemStack is = new ItemStack(Material.ANVIL);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.AQUA+"click to manage config");
		ArrayList<String> list = new ArrayList<String>();
		im.setLore(list);
		is.setItemMeta(im);
		return is;
	}

	@Override
	public ExecReturn Execute(Player player, ClickType click) {
		Bukkit.getScheduler().scheduleAsyncDelayedTask(RezxisMCHosting.instance, new Runnable() {
			public void run() {
				GuiOpener.open(player, new ConfigManagerGui(back,RezxisMCHosting.instance.getDataFolder().getParentFile(),true));
			}
		}, 2);
		return ExecReturn.CLOSE;
	}
}
