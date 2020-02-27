package net.rezxis.mchosting.spigot.gui2.main;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.gui.GUIAction;
import net.rezxis.mchosting.gui.GUIItem;

public class ResetOPItem extends GUIItem {

	public ResetOPItem() {
		super(getIcon());
	}

	private static ItemStack getIcon() {
		ItemStack is = new ItemStack(Material.LAVA_BUCKET);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.GREEN+"管理人をリセット");
		is.setItemMeta(im);
		return is;
	}

	@Override
	public GUIAction invClick(InventoryClickEvent e) {
		e.getWhoClicked().sendMessage(ChatColor.GREEN+"管理人をリセットしました。");
		for (OfflinePlayer p : Bukkit.getOperators()) {
			p.setOp(false);
		}
		e.getWhoClicked().setOp(true);
		return GUIAction.NO_ACTION;
	}
}
