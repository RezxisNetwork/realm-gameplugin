package net.rezxis.mchosting.spigot.gui.plugin.versions;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.gui.GUIAction;
import net.rezxis.mchosting.gui.GUIItem;
import net.rezxis.mchosting.gui.GUIWindow;

public class BackVersionsPage extends GUIItem {

	private int page;
	private GUIWindow back;
	private String pname;
	
	public BackVersionsPage(int page,GUIWindow back, String name) {
		super(getIcon());
		this.pname = name;
		this.page = page;
		this.back = back;
	}
	
	private static ItemStack getIcon() {
		ItemStack is = new ItemStack(Material.ARROW);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.LIGHT_PURPLE+"Previous Page");
		is.setItemMeta(im);
		return is;
	}

	@Override
	public GUIAction invClick(InventoryClickEvent e) {
		new VersionSelectorMenu((Player) e.getWhoClicked(),back,pname,page-1).delayShow();
		return GUIAction.CLOSE;
	}
}
