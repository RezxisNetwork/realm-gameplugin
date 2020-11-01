package net.rezxis.mchosting.spigot.gui.main;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.database.object.server.DBServer;
import net.rezxis.mchosting.gui.GUIAction;
import net.rezxis.mchosting.gui.GUIItem;
import net.rezxis.mchosting.spigot.RezxisMCHosting;
import net.rezxis.mchosting.spigot.ServerListener;

public class DirectConnectItem extends GUIItem {

	public DirectConnectItem() {
		super(getIcon());
	}
	
	private static ItemStack getIcon() {
		ItemStack is = new ItemStack(Material.FENCE_GATE);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.GREEN+"直アドレス");
		ArrayList<String> lore = new ArrayList<>();
		lore.add(ChatColor.GREEN+RezxisMCHosting.getDBServer(false).getVoteCmd());
		String s = RezxisMCHosting.getDBServer(true).getDirect();
		if (s != null && !s.isEmpty()) {
			lore.add(ChatColor.GREEN+"現在のアドレス : "+s+".direct.rezxis.net");
		}
		lore.add(ChatColor.GREEN+"接続に使用できるアドレスを指定できます。");
		lore.add(ChatColor.GREEN+"<指定する名前>.direct.rezxis.net で接続できるようになります。");
		lore.add(ChatColor.RED+"シフト + クリックで無効化されます。");
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	@Override
	public GUIAction invClick(InventoryClickEvent e) {
		if (e.isShiftClick()) {
			DBServer server = RezxisMCHosting.getDBServer(true);
			server.setDirect("");
			server.update();
			e.getWhoClicked().sendMessage(ChatColor.YELLOW+"変更を適応するには再起動が必要です。");
			return GUIAction.CLOSE;
		}
		Player p = (Player) e.getWhoClicked();
		p.sendMessage(ChatColor.YELLOW+"<指定する名前>.direct.rezxis.net で接続できるようになります。");
		p.sendMessage(ChatColor.YELLOW+"変更後再起動が必要です。");
		ServerListener.direct.add(e.getWhoClicked().getUniqueId());
		return GUIAction.CLOSE;
	}
}
