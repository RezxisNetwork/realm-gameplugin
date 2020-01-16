package net.rezxis.mchosting.spigot;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.database.object.server.DBServer;
import net.rezxis.mchosting.spigot.gui2.main.RealmMenu;
import net.rezxis.mchosting.spigot.gui2.shop.buy.RealmShopMenu;

public class CommandHandler {

	public static boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player player = (Player)sender;
		if (cmd.getName().equalsIgnoreCase("realm") || cmd.getName().equalsIgnoreCase("sa")) {
			DBServer ds = RezxisMCHosting.getDBServer();
			if (player.getUniqueId().toString().equals(ds.getOwner().toString())) {
				new RealmMenu(player).delayShow();
			} else {
				if (ds.getShop().getItems().size() == 0) {
					player.sendMessage(ChatColor.RED+"Shopにアイテムが売られていません！");
					return true;
				}
				new RealmShopMenu(player,1,false).delayShow();
			}
		}
		return true;
	}
}