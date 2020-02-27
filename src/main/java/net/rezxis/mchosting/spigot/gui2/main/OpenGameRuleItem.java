package net.rezxis.mchosting.spigot.gui2.main;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.gui.GUIAction;
import net.rezxis.mchosting.gui.GUIItem;
import net.rezxis.mchosting.gui.GUIWindow;
import net.rezxis.mchosting.spigot.gui2.gamerules.GameruleManager;

public class OpenGameRuleItem extends GUIItem {

	private GUIWindow back;
	
	public OpenGameRuleItem(GUIWindow back) {
		super(getIcon());
		this.back = back;
	}
	
	private static ItemStack getIcon() {
		ItemStack is = new ItemStack(Material.COMMAND);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.GREEN+"ゲームルールを変えます");
		is.setItemMeta(im);
		return is;
	}

	@Override
	public GUIAction invClick(InventoryClickEvent e) {
		new GameruleManager((Player) e.getWhoClicked(), back).delayShow();
		return GUIAction.CLOSE;
	}
}
