package net.rezxis.mchosting.spigot.gui.plugins.config;

import java.io.File;
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
import net.rezxis.mchosting.spigot.RezxisMCHosting;

public class DirectoryItem extends ExecutableItemStack {

	private File file;
	private GuiPresenter back;
	
	public DirectoryItem(GuiPresenter back, File file) {
		super(getIcon(file));
		this.file = file;
		this.back = back;
	}
	
	private static ItemStack getIcon(File file) {
		ItemStack is = new ItemStack(Material.BOOK);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.GOLD+"Directory:"+file.getName());
		ArrayList<String> list = new ArrayList<>();
		list.add(ChatColor.AQUA+"Click to open");
		im.setLore(list);
		is.setItemMeta(im);
		return is;
	}

	@Override
	public ExecReturn Execute(Player player, ClickType click) {
		Bukkit.getScheduler().scheduleAsyncDelayedTask(RezxisMCHosting.instance, new Runnable() {
			public void run() {
				GuiOpener.open(player, new ConfigManagerGui(back,file,false));
			}
		}, 2);
		return ExecReturn.CLOSE;
	}
}
