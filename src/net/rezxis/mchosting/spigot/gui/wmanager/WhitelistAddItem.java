package net.rezxis.mchosting.spigot.gui.wmanager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.gui.ExecutableItemStack;
import net.rezxis.mchosting.gui.GuiOpener;
import net.rezxis.mchosting.spigot.RezxisMCHosting;
import net.wesjd.anvilgui.AnvilGUI;

public class WhitelistAddItem extends ExecutableItemStack {

	public WhitelistAddItem() {
		super(getIcon());
	}
	
	private static ItemStack getIcon() {
		ItemStack is = new ItemStack(Material.ANVIL);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.GREEN+"Add player");
		ArrayList<String> list = new ArrayList<>();
		list.add(ChatColor.AQUA+"Click to Add player to whitelist");
		im.setLore(list);
		is.setItemMeta(im);
		return is;
	}

	@SuppressWarnings("deprecation")
	@Override
	public ExecReturn Execute(Player player, ClickType click) {
		Bukkit.getScheduler().scheduleAsyncDelayedTask(RezxisMCHosting.instance, new Runnable() {
			public void run() {
				new AnvilGUI.Builder()
				.onClose(pl -> {})
				.onComplete((pl,text) -> {
					if (text == null) {
						pl.sendMessage(ChatColor.RED+"正しいplayer名を入れてください。");
						return AnvilGUI.Response.close();
					}
					OfflinePlayer p = Bukkit.getOfflinePlayer(text);
					p.setWhitelisted(true);
					
					Bukkit.getScheduler().scheduleAsyncDelayedTask(RezxisMCHosting.instance, new Runnable() {
						public void run() {
							GuiOpener.open(pl, new WhiteListManagerGui());
						}
					}, 8);
					return AnvilGUI.Response.close();
				})
				.text("player名を入れてください")
				.plugin(RezxisMCHosting.instance)
				.open(player);
			}
			},2);
		return ExecReturn.CLOSE;
	}
}
