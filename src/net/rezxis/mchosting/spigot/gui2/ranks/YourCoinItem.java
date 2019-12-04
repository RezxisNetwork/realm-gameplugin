package net.rezxis.mchosting.spigot.gui2.ranks;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.databse.DBPlayer;
import net.rezxis.mchosting.gui.GUIAction;
import net.rezxis.mchosting.gui.GUIItem;
import net.rezxis.mchosting.spigot.RezxisMCHosting;

public class YourCoinItem extends GUIItem {

	public YourCoinItem(Player player) {
		super(getIcon(player));
	}
	
	private static ItemStack getIcon(Player player) {
		DBPlayer dp = RezxisMCHosting.getPTable().get(player.getUniqueId());
		ItemStack is = new ItemStack(Material.GOLD_INGOT);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.LIGHT_PURPLE+"Realm"+ChatColor.GOLD+"Coins");
		ArrayList<String> lore = new ArrayList<>();
		lore.add(ChatColor.GRAY+"You have "+ChatColor.GOLD+dp.getCoin());
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	@Override
	public GUIAction invClick(InventoryClickEvent e) {
		e.getWhoClicked().sendMessage(ChatColor.LIGHT_PURPLE+"Coinは投票などからもらえます！");
		return GUIAction.NO_ACTION;
	}
}
