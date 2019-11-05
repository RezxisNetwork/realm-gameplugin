package net.rezxis.mchosting.spigot.gui.plugins.config;

import java.io.File;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.gui.ExecutableItemStack;

public class DeleteThisDirectory extends ExecutableItemStack {

	private File file;
	
	public DeleteThisDirectory(File file) {
		super(getIcon());
		this.file = file;
	}
	
	private static ItemStack getIcon() {
		ItemStack is = new ItemStack(Material.BARRIER);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.RED+"Delete directory");
		is.setItemMeta(im);
		return is;
	}

	@Override
	public ExecReturn Execute(Player player, ClickType click) {
		this.file.delete();
		player.sendMessage(ChatColor.RED+"the directory was deleted.");
		return ExecReturn.CLOSE;
	}
}
