package net.rezxis.mchosting.spigot.gui2.plugins.config.file;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.gui.GUIAction;
import net.rezxis.mchosting.gui.GUIItem;
import net.rezxis.mchosting.gui.GUIWindow;
import net.rezxis.mchosting.spigot.PastebinUtils;
import net.rezxis.mchosting.spigot.RezxisMCHosting;
import net.rezxis.mchosting.gui.anvil.AnvilGUI;

public class UploadFileItem extends GUIItem {

	private File file;
	private GUIWindow back;
	
	public UploadFileItem(File file, GUIWindow back) {
		super(getIcon());
		this.file = file;
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
	public GUIAction invClick(InventoryClickEvent e) {
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
					PastebinUtils.download(link, file, pl);
				} catch (Exception e) {
					e.printStackTrace();
					pl.sendMessage("uploadできませんでした");
				}
				}).start();
				back.delayShow();
				return AnvilGUI.Response.close();
			})
			.text("URLを入れてください。")
			.plugin(RezxisMCHosting.instance)
			.open((Player) e.getWhoClicked());
			}},2);
		return GUIAction.CLOSE;
	}
}
