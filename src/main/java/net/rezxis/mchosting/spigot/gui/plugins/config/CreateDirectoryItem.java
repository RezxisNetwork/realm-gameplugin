package net.rezxis.mchosting.spigot.gui.plugins.config;

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
import net.rezxis.mchosting.spigot.RezxisMCHosting;
import net.rezxis.mchosting.gui.anvil.AnvilGUI;

public class CreateDirectoryItem extends GUIItem {
	
	private File file;
	private GUIWindow back;

	public CreateDirectoryItem(File file, GUIWindow back) {
		super(getIcon());
		this.file = file;
		this.back = back;
	}
	
	private static ItemStack getIcon() {
		ItemStack is = new ItemStack(Material.ANVIL);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.AQUA+"Create directory");
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
					for (String s : denied) {
						if (text.contains(s)) {
							pl.sendMessage(ChatColor.RED+"特殊記号は使えません");
							return AnvilGUI.Response.close();
						}
					}
			
					new File(file,text).mkdirs();
					new ConfigManagerMenu(pl, file, back, false, 0).delayShow();;
					pl.sendMessage(ChatColor.AQUA+"作成されました");
					return AnvilGUI.Response.close();
				})
				.text("フォルダー名を入れてください")
				.plugin(RezxisMCHosting.instance)
				.open((Player) e.getWhoClicked());
			}},2);
		return GUIAction.CLOSE;
	}
}
