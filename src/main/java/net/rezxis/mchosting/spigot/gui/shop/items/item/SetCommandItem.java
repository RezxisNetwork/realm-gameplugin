package net.rezxis.mchosting.spigot.gui.shop.items.item;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.database.object.server.DBShopItem;
import net.rezxis.mchosting.database.object.server.DBShopItembase;
import net.rezxis.mchosting.gui.GUIAction;
import net.rezxis.mchosting.gui.GUIItem;
import net.rezxis.mchosting.spigot.ServerListener;

public class SetCommandItem extends GUIItem {

	private DBShopItem item;
	
	public SetCommandItem(DBShopItem item) {
		super(getIcon(item));
		this.item = item;
	}
	
	private static ItemStack getIcon(DBShopItem item) {
		ItemStack is = new ItemStack(Material.COMMAND);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.AQUA+"コマンドを設定");
		ArrayList<String> lore = new ArrayList<>();
		lore.add(ChatColor.GRAY+"購入されたとき実行されるコマンドを設定します。");
		lore.add(ChatColor.GRAY+item.getCmd());
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	@Override
	public GUIAction invClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		p.sendMessage(ChatColor.YELLOW+"Chatに購入時に実行されるcommandを入れてください。 /を入れなくでください。");
		p.sendMessage(ChatColor.YELLOW+"[player]で購入者を引用できます。 cancelでキャンセルできます。");
		ServerListener.cmd.put(e.getWhoClicked().getUniqueId(), item);
		return GUIAction.CLOSE;
	}
}
