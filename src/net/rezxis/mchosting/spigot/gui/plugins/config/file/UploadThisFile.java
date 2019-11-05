package net.rezxis.mchosting.spigot.gui.plugins.config.file;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

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
import net.rezxis.mchosting.spigot.PastebinUtils;
import net.rezxis.mchosting.spigot.RezxisMCHosting;
import net.wesjd.anvilgui.AnvilGUI;

public class UploadThisFile extends ExecutableItemStack {

	private File f;
	private GuiPresenter back;
	
	public UploadThisFile(GuiPresenter back, File f) {
		super(getIcon());
		this.f = f;
		this.back = back;
	}
	
	private static ItemStack getIcon() {
		ItemStack is = new ItemStack(Material.WEB);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.AQUA+"Upload this file to server");
		is.setItemMeta(im);
		return is;
	}

	@SuppressWarnings("deprecation")
	@Override
	public ExecReturn Execute(Player player, ClickType click) {
		Bukkit.getScheduler().scheduleAsyncDelayedTask(RezxisMCHosting.instance, new Runnable() {
		public void run() {
		new AnvilGUI.Builder()
		.onClose(pl -> {})
		.onComplete((pl,text) -> {
			final String link;
			if (text == null) {
				return AnvilGUI.Response.text("URLを入れてください。");
			} else {
				if (text.contains("pastebin.com/raw/")) {
					link = text;
				} else {
					if (text.contains("pastebin.com/")) {
						link = "https://pastebin.com/raw/"+text.split("pastebin.com/")[1];
					} else {
						pl.sendMessage(ChatColor.RED+"正しいURLを入れてください");
						return AnvilGUI.Response.close();
					}
				}
			}
			new Thread(()->{
			try {
				PastebinUtils.download(link, f, pl);
			} catch (Exception e) {
				e.printStackTrace();
				pl.sendMessage("uploadできませんでした");
			}
			}).start();
			Bukkit.getScheduler().scheduleAsyncDelayedTask(RezxisMCHosting.instance, new Runnable() {
				public void run() {
					GuiOpener.open(player, back);
				}
			}, 2);
			return AnvilGUI.Response.close();
		})
		.text("URLを入れてください。")
		.plugin(RezxisMCHosting.instance)
		.open(player);
		}},2);
		return ExecReturn.CLOSE;
	}
}
