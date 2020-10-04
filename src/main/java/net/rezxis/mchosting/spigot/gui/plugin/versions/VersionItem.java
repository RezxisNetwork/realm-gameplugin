package net.rezxis.mchosting.spigot.gui.plugin.versions;

import java.util.ArrayList;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.database.Tables;
import net.rezxis.mchosting.database.object.server.DBPlugin;
import net.rezxis.mchosting.database.object.server.DBServerPluginLink;
import net.rezxis.mchosting.gui.GUIAction;
import net.rezxis.mchosting.gui.GUIItem;
import net.rezxis.mchosting.spigot.RezxisMCHosting;

public class VersionItem extends GUIItem {

	private DBPlugin plugin;
	
	public VersionItem(DBPlugin plugin) {
		super(getIcon(plugin));
		this.plugin = plugin;
	}
	
	@SuppressWarnings("deprecation")
	private static ItemStack getIcon(DBPlugin plugin) {
		ItemStack is;
		ChatColor c;
		String act = "";
		String now = "";
		DBServerPluginLink link = Tables.getSplTable().getLink(RezxisMCHosting.getDBServer(false).getId(), plugin.getName());
		if (link != null) {
			if (link.getPlugin() == plugin.getId()) {
				if (link.isEnabled()) {
					if (link.isLastEnabled()) {
						is = new ItemStack(Material.INK_SACK,1 , DyeColor.LIME.getDyeData());
						c = ChatColor.GREEN;
						now = ChatColor.GREEN+"有効";
						act = "無効化";
					} else {
						is = new ItemStack(Material.INK_SACK,1 , DyeColor.ORANGE.getDyeData());
						c = ChatColor.GOLD;
						now = ChatColor.GREEN+"有効";
						act = "無効化取り消し";
					}
				} else {
					if (!link.isLastEnabled()) {
						is = new ItemStack(Material.INK_SACK,1 , DyeColor.RED.getDyeData());
						c = ChatColor.RED;
						now = ChatColor.RED+"無効";
						act = "有効化";
					} else {
						is = new ItemStack(Material.INK_SACK,1 , DyeColor.ORANGE.getDyeData());
						c = ChatColor.GOLD;
						now = ChatColor.RED+"無効";
						act = "有効化取り消し";
					}
				}
			} else {
				is = new ItemStack(Material.INK_SACK,1 , DyeColor.RED.getDyeData());
				c = ChatColor.RED;
				now = ChatColor.RED+"無効";
				act = "有効化";
			}
		} else {
			is = new ItemStack(Material.INK_SACK,1 , DyeColor.RED.getDyeData());
			c = ChatColor.RED;
			now = ChatColor.RED+"無効";
			act = "有効化";
		}
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(c+plugin.getName());
		ArrayList<String> list = new ArrayList<>();
		list.add(ChatColor.AQUA+"依存関係-"+plugin.getDepends().size());
		for (String s : plugin.getDepends()) {
			list.add(ChatColor.AQUA+s);
		}
		list.add(ChatColor.AQUA+"--------------------------");
		list.add(ChatColor.AQUA+"Version : "+plugin.getVersion());
		list.add(ChatColor.AQUA+"状態 : "+now);
		list.add(ChatColor.AQUA+"クリックで"+act);
		im.setLore(list);
		is.setItemMeta(im);
		return is;
	}

	@Override
	public GUIAction invClick(InventoryClickEvent e) {
		DBServerPluginLink link = Tables.getSplTable().getLink(RezxisMCHosting.getDBServer(false).getId(), plugin.getName());
		if (link != null) {
			if (link.getPlugin() == plugin.getId()) {
				if (link.isEnabled()) {
					if (link.isLastEnabled()) {
						link.setEnabled(false);
						e.getWhoClicked().sendMessage(ChatColor.AQUA+"変更を反映するには再起動してください。");
					} else {
						link.setEnabled(true);
					}
				} else {
					if (!link.isLastEnabled()) {
						link.setEnabled(true);
						e.getWhoClicked().sendMessage(ChatColor.AQUA+"変更を反映するには再起動してください。");
					} else {
						link.setEnabled(false);
					}
				}
			} else {
				link.setEnabled(true);
				e.getWhoClicked().sendMessage(ChatColor.AQUA+"変更を反映するには再起動してください。");
			}
			link.setPlugin(plugin.getId());
			link.update();
		} else {
			link = new DBServerPluginLink(-1, RezxisMCHosting.getDBServer(false).getId(), plugin.getName(), plugin.getId(), true, false);
			Tables.getSplTable().insert(link);
		}
		return GUIAction.UPDATE;
	}
}
