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
import net.rezxis.mchosting.spigot.RezxisMCHosting;
import net.rezxis.mchosting.spigot.ServerListener;

public class TextureItem extends GUIItem {

	public TextureItem() {
		super(getIcon());
	}
	
	private static ItemStack getIcon() {
		ItemStack is = new ItemStack(Material.GOLD_ORE);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.GREEN+"テクスチャ");
		ArrayList<String> lore = new ArrayList<>();
		lore.add(ChatColor.GREEN+RezxisMCHosting.getDBServer(false).getVoteCmd());
		lore.add(ChatColor.GREEN+"テクスチャを変更します。");
		lore.add(ChatColor.RED+"cancelで変更前に戻します。");
		lore.add(ChatColor.RED+"removeで削除します。");
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	@Override
	public GUIAction invClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		p.sendMessage(ChatColor.YELLOW+"テクスチャを設定します。cancelでキャンセルできます。 removeで削除します。");
		ServerListener.texture.add(e.getWhoClicked().getUniqueId());
		return GUIAction.CLOSE;
	}
}
