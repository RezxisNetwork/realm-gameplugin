package net.rezxis.mchosting.spigot.gui2.plugins.config;

import java.io.File;
import java.io.IOException;

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
import net.rezxis.mchosting.spigot.RezxisMCHosting;
import net.rezxis.mchosting.spigot.gui2.plugins.config.file.FileManagerMenu;
import net.rezxis.mchosting.gui.anvil.AnvilGUI;

public class CreateFileItem extends GUIItem {

	private File file;
	private GUIWindow back;
	
	public CreateFileItem(File file, GUIWindow back) {
		super(getIcon());
		this.file = file;
		this.back = back;
	}
	
	private static ItemStack getIcon() {
		ItemStack is = new ItemStack(Material.ANVIL);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.AQUA+"Create file");
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
			String[] denied = new String[] {"/","\\"};
			if (text == null) {
				pl.sendMessage(ChatColor.RED+"正しいfile名をいれてください。");
				return AnvilGUI.Response.close();
			}
			if (text.startsWith(".")) {
				pl.sendMessage(ChatColor.RED+"正しいfile名をいれてください。");
				return AnvilGUI.Response.close();
			}
			for (String s : denied) {
				if (text.contains(s)) {
					pl.sendMessage(ChatColor.RED+"特殊記号は使えません");
					return AnvilGUI.Response.close();
				}
			}
			
			try {
				new File(file,text).createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				pl.sendMessage(ChatColor.RED+"ファイルを作成できませんでした。");
				return AnvilGUI.Response.close();
			}
			new FileManagerMenu(pl,new File(file,text), back).delayShow();
			pl.sendMessage(ChatColor.AQUA+"作成されました");
			return AnvilGUI.Response.close();
		})
		.text("ファイル名を入れてください")
		.plugin(RezxisMCHosting.instance)
		.open((Player) e.getWhoClicked());
			}},2);
		return GUIAction.CLOSE;
	}
}
