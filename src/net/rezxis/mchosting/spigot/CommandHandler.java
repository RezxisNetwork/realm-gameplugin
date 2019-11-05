package net.rezxis.mchosting.spigot;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.gui.GuiOpener;
import net.rezxis.mchosting.spigot.gui.main.MainGui;

public class CommandHandler {

	public static boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("hub") || cmd.getName().equalsIgnoreCase("lobby")) {
			RezxisMCHosting.instance.hub((Player)sender);
		}
		if (cmd.getName().equalsIgnoreCase("realm") || cmd.getName().equalsIgnoreCase("sa")) {
			if (((Player)sender).getUniqueId().toString().equals(RezxisMCHosting.instance.me.getOwner().toString())) {
				GuiOpener.open((Player)sender, new MainGui());
			} else {
				sender.sendMessage(ChatColor.RED+"This command can use only server owner");
			}
		}
		return true;
	}

}