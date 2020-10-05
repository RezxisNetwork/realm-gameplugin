package net.rezxis.mchosting.spigot.gui.main;

import java.io.File;
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
import net.rezxis.mchosting.spigot.gui.plugins.config.ConfigManagerMenu;
import net.rezxis.mchosting.database.Tables;
import net.rezxis.mchosting.database.object.server.DBServerPluginLink;

public class OpenSkriptItem extends GUIItem {

	public OpenSkriptItem() {
		super(getIcon());
	}
	
	private static ItemStack getIcon() {
		ItemStack is = new ItemStack(Material.DIAMOND_ORE);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.GREEN+"Skript config");
		ArrayList<String> array = new ArrayList<>();
		array.add(ChatColor.RED+"Skriptが導入されていないと使えません。");
		im.setLore(array);
		is.setItemMeta(im);
		return is;
	}

	@Override
	public GUIAction invClick(InventoryClickEvent e) {
		DBServerPluginLink link = Tables.getSplTable().getLink(RezxisMCHosting.getDBServer(false).getId(), "Skript");
		if (link == null || !link.isLastEnabled())  {
			e.getWhoClicked().sendMessage(ChatColor.RED+"Skriptを導入してください。");
			return GUIAction.NO_ACTION;
		}
		File f = new File(RezxisMCHosting.instance.getDataFolder().getParentFile(),
				"Skript/scripts/");
		new ConfigManagerMenu((Player) e.getWhoClicked(),f,new RealmMenu((Player) e.getWhoClicked()), false, 0).delayShow();
		return GUIAction.CLOSE;
	}
}
