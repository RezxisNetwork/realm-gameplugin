package net.rezxis.mchosting.spigot.gui.main;

import java.io.File;

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
import net.rezxis.mchosting.spigot.RezxisMCHosting;
import net.rezxis.mchosting.spigot.gui.plugins.config.ConfigManagerGui;

public class Skript extends ExecutableItemStack {

	private GuiPresenter back;
	
	public Skript(GuiPresenter back) {
		super(getIcon());
		this.back = back;
	}
	
	private static ItemStack getIcon() {
		ItemStack is = new ItemStack(Material.DIAMOND_ORE);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.AQUA+"Manage Skript");
		is.setItemMeta(im);
		return is;
	}

	@Override
	public ExecReturn Execute(Player player, ClickType click) {
		File file = new File(RezxisMCHosting.instance.getDataFolder().getParentFile(),"Skript\\scripts");
		Bukkit.getScheduler().scheduleAsyncDelayedTask(RezxisMCHosting.instance, new Runnable() {
			public void run() {
				GuiOpener.open(player, new ConfigManagerGui(back, file, false));
			}
		}, 2);
		return ExecReturn.CLOSE;
	}
}
