package net.rezxis.mchosting.spigot.gui.plugins.config.file;

import java.io.File;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.gui.ExecutableItemStack;

public class FileInfoItem extends ExecutableItemStack {

	private static ItemStack getIcon(File file) {
		ItemStack is = new ItemStack(Material.PAPER);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.AQUA+file.getName());
		return is;
	}
	
	public FileInfoItem(File file) {
		super(getIcon(file));
		// TODO Auto-generated constructor stub
	}

	@Override
	public ExecReturn Execute(Player player, ClickType click) {
		return ExecReturn.NO_ACTION;
	}

}
