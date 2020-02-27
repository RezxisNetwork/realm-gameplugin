package net.rezxis.mchosting.spigot.gui2.plugins.config.file;

import java.io.File;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.gui.GUIAction;
import net.rezxis.mchosting.gui.GUIItem;
import net.rezxis.mchosting.gui.GUIWindow;

public class DeleteFileItem extends GUIItem {

	private File file;
	private GUIWindow back;
	
	public DeleteFileItem(File file, GUIWindow back) {
		super(getIcon());
		this.file = file;
		this.back = back;
	}
	
	private static ItemStack getIcon() {
		ItemStack is = new ItemStack(Material.BARRIER);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.RED+"Delete file");
		is.setItemMeta(im);
		return is;
	}

	@Override
	public GUIAction invClick(InventoryClickEvent e) {
		this.file.delete();
		e.getWhoClicked().sendMessage(ChatColor.RED+"the file was deleted.");
		back.delayShow();
		return GUIAction.NO_ACTION;
	}
}
