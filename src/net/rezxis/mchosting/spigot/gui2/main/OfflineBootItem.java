package net.rezxis.mchosting.spigot.gui2.main;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.database.Tables;
import net.rezxis.mchosting.database.object.player.DBPlayer;
import net.rezxis.mchosting.gui.GUIAction;
import net.rezxis.mchosting.gui.GUIItem;
import net.rezxis.mchosting.spigot.RezxisMCHosting;

public class OfflineBootItem extends GUIItem {

	public OfflineBootItem() {
		super(getIcon());
	}
	
	private static ItemStack getIcon() {
		Material m = null;
		DBPlayer player = Tables.getPTable().get(RezxisMCHosting.getDBServer().getOwner());
		if (player.isOfflineBoot()) {
			m = Material.TORCH;
		} else {
			m = Material.LEVER;
		}
		ItemStack is = new ItemStack(m);
		ItemMeta im = is.getItemMeta();
		if (player.isOfflineBoot()) {
			im.setDisplayName(ChatColor.GREEN+"有効");
		} else {
			im.setDisplayName(ChatColor.RED+"無効");
		}
		ArrayList<String> lore = new ArrayList<>();
		lore.add(ChatColor.GREEN+"オーナーがオフラインでも起動できるようになります。");
		lore.add(ChatColor.RED+"Rankが必要です。");
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	@Override
	public GUIAction invClick(InventoryClickEvent e) {
		DBPlayer player = Tables.getPTable().get(RezxisMCHosting.getDBServer().getOwner());
		if (!player.getRank().getOfflineBoot()) {
			e.getWhoClicked().sendMessage(ChatColor.RED+"Rankが必要です。");
			return GUIAction.NO_ACTION;
		}
		player.setOfflineBoot(!player.isOfflineBoot());
		player.update();
		return GUIAction.UPDATE;
	}
}
