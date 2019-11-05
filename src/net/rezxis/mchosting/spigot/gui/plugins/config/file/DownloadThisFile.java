package net.rezxis.mchosting.spigot.gui.plugins.config.file;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
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
import net.rezxis.mchosting.spigot.PastebinUtils;
import net.rezxis.mchosting.spigot.RezxisMCHosting;

public class DownloadThisFile extends ExecutableItemStack {

	private File f;
	private GuiPresenter back;
	
	public DownloadThisFile(GuiPresenter back, File f) {
		super(getIcon());
		this.f = f;
		this.back = back;
	}
	
	private static ItemStack getIcon() {
		ItemStack is = new ItemStack(Material.BOOK);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.AQUA+"Download this file from server");
		is.setItemMeta(im);
		return is;
	}

	@Override
	public ExecReturn Execute(Player player, ClickType click) {
		//upload this file to pastebin.
		player.sendMessage(ChatColor.AQUA+"Uploading "+f.getName());
		new Thread(()->{
			try {
				String ret = PastebinUtils.upload(FileUtils.readFileToString(f), f.getName()+"-"+player.getName());
				player.sendMessage("Uploaded : "+ret);
			} catch (IOException e) {
				e.printStackTrace();
				player.sendMessage("Something went to worng : "+e.getMessage());
			}
			
		}).start();
		Bukkit.getScheduler().scheduleAsyncDelayedTask(RezxisMCHosting.instance, new Runnable() {
			public void run() {
				GuiOpener.open(player, back);
			}
		}, 2);
		return ExecReturn.CLOSE;
	}
}