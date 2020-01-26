package net.rezxis.mchosting.spigot.gui2.gamerules;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.gui.GUIAction;
import net.rezxis.mchosting.gui.GUIItem;

public class GameruleItem extends GUIItem {

	private String key;
	
	public GameruleItem(Player player, String key) {
		super(getIcon(player, key));
		this.key = key;
	}
	
	private static ItemStack getIcon(Player player, String key) {
		ItemStack is = null;
		boolean bool = Boolean.valueOf(player.getWorld().getGameRuleValue(key));
		if (bool) {
			is = new ItemStack(Material.WATER_BUCKET);
		} else {
			is = new ItemStack(Material.BUCKET);
		}
		ItemMeta im = is.getItemMeta();
		ArrayList<String> lore = new ArrayList<>();
		if (bool) {
			im.setDisplayName(ChatColor.GREEN+key);
			lore.add(ChatColor.RED+"クリックで無効化");
		} else {
			im.setDisplayName(ChatColor.RED+key);
			lore.add(ChatColor.GREEN+"クリックで有効化");
		}
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	@Override
	public GUIAction invClick(InventoryClickEvent e) {
		e.getWhoClicked().getWorld().setGameRuleValue(key, String.valueOf(!Boolean.valueOf(e.getWhoClicked().getWorld().getGameRuleValue(key))));
		return GUIAction.UPDATE;
	}
}
