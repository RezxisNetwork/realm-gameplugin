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

public class ChangeVoteListener extends GUIItem {

	public ChangeVoteListener() {
		super(getIcon());
	}
	
	private static ItemStack getIcon() {
		ItemStack is = new ItemStack(Material.GOLD_ORE);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.GREEN+"投票時に実行されるコマンド");
		ArrayList<String> lore = new ArrayList<>();
		lore.add(ChatColor.GREEN+RezxisMCHosting.getDBServer(false).getVoteCmd());
		lore.add(ChatColor.GREEN+"投票時に実行されるコマンドを変更します。");
		lore.add(ChatColor.RED+"cancelで変更前に戻します。");
		lore.add(ChatColor.RED+"removeで削除します。");
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	@Override
	public GUIAction invClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		p.sendMessage(ChatColor.YELLOW+"Chatに購入時に実行されるcommandを入れてください。 /を入れなくでください。");
		p.sendMessage(ChatColor.YELLOW+"[player]で投票者を引用できます。 cancelでキャンセルできます。 removeで削除します。");
		ServerListener.vcmd.add(e.getWhoClicked().getUniqueId());
		return GUIAction.CLOSE;
	}
}
