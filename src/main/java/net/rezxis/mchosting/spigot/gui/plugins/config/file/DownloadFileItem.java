package net.rezxis.mchosting.spigot.gui.plugins.config.file;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.gui.GUIAction;
import net.rezxis.mchosting.gui.GUIItem;
import net.rezxis.mchosting.gui.GUIWindow;
import net.rezxis.mchosting.spigot.WebAPI;

public class DownloadFileItem extends GUIItem {

	private File file;
	private GUIWindow back;
	
	public DownloadFileItem(File file, GUIWindow back) {
		super(getIcon());
		this.file = file;
		this.back = back;
	}
	
	private static ItemStack getIcon() {
		ItemStack is = new ItemStack(Material.BOOK);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.AQUA+"ダウンロード");
		is.setItemMeta(im);
		return is;
	}

	@Override
	public GUIAction invClick(InventoryClickEvent e) {
		e.getWhoClicked().sendMessage(ChatColor.AQUA+"Uploading "+file.getName());
		new Thread(()->{
			try {
				String ret = WebAPI.upload(FileUtils.readFileToString(file, "UTF-8"), file.getName()+"-"+e.getWhoClicked().getName());
				e.getWhoClicked().sendMessage("アップロードされました : "+ret);
			} catch (IOException ex) {
				ex.printStackTrace();
				e.getWhoClicked().sendMessage("Error");
			}
			
		}).start();
		back.delayShow();
		return GUIAction.CLOSE;
	}
}
