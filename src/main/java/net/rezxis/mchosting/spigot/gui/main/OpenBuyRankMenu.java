package net.rezxis.mchosting.spigot.gui.main;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.gui.GUIAction;
import net.rezxis.mchosting.gui.GUIItem;
import net.rezxis.mchosting.spigot.gui.ranks.BuyRankMenu;

public class OpenBuyRankMenu extends GUIItem {

	public OpenBuyRankMenu() {
		super(getIcon());
	}
	
	private static ItemStack getIcon() {
		ItemStack is = new ItemStack(Material.POWERED_RAIL);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.GREEN+"Rankを購入");
		im.addEnchant(Enchantment.DURABILITY, 1, true);
		is.setItemMeta(im);
		return is;
	}

	@Override
	public GUIAction invClick(InventoryClickEvent e) {
		new BuyRankMenu((Player) e.getWhoClicked()).delayShow();
		return GUIAction.CLOSE;
	}
}
