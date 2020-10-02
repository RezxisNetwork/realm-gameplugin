package net.rezxis.mchosting.spigot.gui2.main;

import java.util.ArrayList;

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
import net.rezxis.mchosting.spigot.gui2.plugins.config.ConfigManagerMenu;

public class ConfigItem extends GUIItem {

	private GUIWindow back;
	
	public ConfigItem(GUIWindow back) {
		super(getIcon());
		this.back = back;
	}
	
	private static ItemStack getIcon() {
		ItemStack is = new ItemStack(Material.PAPER);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.AQUA+"Configを変更");
		ArrayList<String> list = new ArrayList<String>();
		im.setLore(list);
		is.setItemMeta(im);
		return is;
	}

	@Override
	public GUIAction invClick(InventoryClickEvent e) {
		new ConfigManagerMenu((Player) e.getWhoClicked(), RezxisMCHosting.instance.getDataFolder().getParentFile(), back, true, 0).delayShow();
		return GUIAction.CLOSE;
	}
}
