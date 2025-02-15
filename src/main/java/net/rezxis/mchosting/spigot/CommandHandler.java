package net.rezxis.mchosting.spigot;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.database.Tables;
import net.rezxis.mchosting.database.object.server.DBServer;
import net.rezxis.mchosting.spigot.gui.main.RealmMenu;
import net.rezxis.mchosting.spigot.gui.shop.buy.RealmShopMenu;

public class CommandHandler {

	public static boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player player = (Player)sender;
		if (cmd.getName().equalsIgnoreCase("realm") || cmd.getName().equalsIgnoreCase("sa")) {
			DBServer ds = RezxisMCHosting.getDBServer(false);
			if (player.getUniqueId().toString().equals(ds.getOwner().toString())) {
				new RealmMenu(player).delayShow();
			} else {
				if (Tables.getSiTable().getShopItems(ds.getId()).size() == 0) {
					player.sendMessage(ChatColor.RED+"Shopにアイテムが売られていません！");
					return true;
				}
				new RealmShopMenu(player,1,false).delayShow();
			}
		}
		return true;
	}
}