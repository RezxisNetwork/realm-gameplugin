package net.rezxis.mchosting.spigot.gui2.plugins;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.gui.GUIAction;
import net.rezxis.mchosting.gui.GUIItem;
import net.rezxis.mchosting.gui.GUIWindow;

public class NextPluginPage extends GUIItem {

	private int page;
	private GUIWindow back;
	
	public NextPluginPage(int page, GUIWindow back) {
		super(getIcon());
		this.page = page;
		this.back = back;
	}
	
	private static ItemStack getIcon() {
		ItemStack is = new ItemStack(Material.ARROW);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.LIGHT_PURPLE+"Next Page");
		is.setItemMeta(im);
		return is;
	}

	@Override
	public GUIAction invClick(InventoryClickEvent e) {
		new PluginManagerMenu((Player) e.getWhoClicked(),back,page+1).delayShow();
		return GUIAction.CLOSE;
	}
}
