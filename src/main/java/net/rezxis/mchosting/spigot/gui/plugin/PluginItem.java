package net.rezxis.mchosting.spigot.gui.plugin;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.gui.GUIAction;
import net.rezxis.mchosting.gui.GUIItem;
import net.rezxis.mchosting.gui.GUIWindow;
import net.rezxis.mchosting.spigot.gui.plugin.versions.VersionSelectorMenu;

public class PluginItem extends GUIItem {

	private String pname;
	private GUIWindow back;
	
	public PluginItem(String pname, GUIWindow back, boolean enabled) {
		super(getIcon(pname, enabled));
		this.pname = pname;
		this.back = back;
	}
	
	private static ItemStack getIcon(String name, boolean enabled) {
		ItemStack is = new ItemStack(Material.CHEST);
		ItemMeta im = is.getItemMeta();
		ChatColor c;
		if (enabled) {
			c = ChatColor.GREEN;
		} else {
			c = ChatColor.RED;
		}
		im.setDisplayName(c+name);
		ArrayList<String> lore = new ArrayList<>();
		lore.add(c+"状態 : "+(enabled ? "有効" : "無効"));
		lore.add(ChatColor.AQUA+"クリックで詳細を表示。");
		im.setLore(lore);
		is.setItemMeta(im);
		if (enabled) {
			is.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		}
		return is;
	}

	@Override
	public GUIAction invClick(InventoryClickEvent e) {
		new VersionSelectorMenu((Player)e.getWhoClicked(),back,pname,0).delayShow();
		return GUIAction.CLOSE;
	}
}