package net.rezxis.mchosting.spigot.gui2.ranks;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.database.object.player.DBPlayer;
import net.rezxis.mchosting.database.object.player.DBPlayer.Rank;
import net.rezxis.mchosting.gui.GUIAction;
import net.rezxis.mchosting.gui.GUIItem;
import net.rezxis.mchosting.spigot.RezxisMCHosting;

public class EmeraldRankItem extends GUIItem {

	private static int price = 60000;
	private static Rank rank = Rank.EMERALD;
	
	public EmeraldRankItem() {
		super(getIcon());
	}
	
	private static ItemStack getIcon() {
		ItemStack is = new ItemStack(Material.EMERALD_BLOCK);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.AQUA+"EMERALD");
		ArrayList<String> lore = new ArrayList<>();
		lore.add(ChatColor.AQUA+"メモリー: "+rank.getMem());
		lore.add(ChatColor.AQUA+"最大プレイヤー: "+rank.getMaxPlayers());
		lore.add(ChatColor.AQUA+"価格: "+ChatColor.LIGHT_PURPLE+price+"Realm"+ChatColor.GOLD+"Coins");
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	@Override
	public GUIAction invClick(InventoryClickEvent e) {
		DBPlayer player = RezxisMCHosting.getPTable().get(e.getWhoClicked().getUniqueId());
		if (player.getCoin() < price) {
			e.getWhoClicked().sendMessage(ChatColor.RED+"コインが足りません。");
			return GUIAction.NO_ACTION;
		}
		if (player.getRank() == rank) {
			e.getWhoClicked().sendMessage(ChatColor.RED+"既にEMERALDを持っています。");
			return GUIAction.NO_ACTION;
		}
		player.setRank(rank);
		player.setCoin(player.getCoin()-price);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.WEEK_OF_MONTH, 1);
		player.setRankExpire(calendar.getTime());
		player.update();
		e.getWhoClicked().sendMessage(ChatColor.GREEN+"EMERALDを購入しました。");
		return GUIAction.UPDATE;
	}
}
