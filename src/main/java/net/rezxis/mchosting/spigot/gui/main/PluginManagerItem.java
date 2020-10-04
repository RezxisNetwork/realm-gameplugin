package net.rezxis.mchosting.spigot.gui.main;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.gui.GUIAction;
import net.rezxis.mchosting.gui.GUIItem;
import net.rezxis.mchosting.gui.GUIWindow;
import net.rezxis.mchosting.spigot.gui.plugin.PluginSelectorMenu;

public class PluginManagerItem extends GUIItem {

	private GUIWindow back;
	
	public PluginManagerItem(GUIWindow back) {
		super(getIcon());
		this.back = back;
	}
	
	private static ItemStack getIcon() {
		ItemStack is = new ItemStack(Material.CHEST);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.GREEN+"プラグインを管理");
		is.setItemMeta(im);
		return is;
	}

	@Override
	public GUIAction invClick(InventoryClickEvent e) {
		new PluginSelectorMenu((Player)e.getWhoClicked(),back,0);
		return GUIAction.CLOSE;
	}
}
