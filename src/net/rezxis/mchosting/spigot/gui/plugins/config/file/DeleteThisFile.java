package net.rezxis.mchosting.spigot.gui.plugins.config.file;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.gui.ExecutableItemStack;
import net.rezxis.mchosting.gui.GuiOpener;
import net.rezxis.mchosting.gui.GuiPresenter;
import net.rezxis.mchosting.gui.ItemExecution.ExecReturn;
import net.rezxis.mchosting.spigot.RezxisMCHosting;

public class DeleteThisFile extends ExecutableItemStack {

	private File file;
	private GuiPresenter back;
	
	public DeleteThisFile(GuiPresenter back,File file) {
		super(getIcon());
		this.file = file;
		this.back = back;
	}
	
	private static ItemStack getIcon() {
		ItemStack is = new ItemStack(Material.BARRIER);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.RED+"Delete file");
		is.setItemMeta(im);
		return is;
	}

	@Override
	public ExecReturn Execute(Player player, ClickType click) {
		this.file.delete();
		player.sendMessage(ChatColor.RED+"the file was deleted.");
		Bukkit.getScheduler().scheduleAsyncDelayedTask(RezxisMCHosting.instance	, new Runnable() {
			public void run() {
				GuiOpener.open(player, back);
			}
		}, 2);
		return ExecReturn.CLOSE;
	}
}
