package net.rezxis.mchosting.spigot.gui.plugins.config;

import java.io.File;
import java.io.IOException;

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
import net.wesjd.anvilgui.AnvilGUI;

public class CreateDirectory extends ExecutableItemStack {

	private File f;
	private GuiPresenter back;
	public CreateDirectory(GuiPresenter back,File f) {
		super(getIcon());
		this.f = f;
		this.back = back;
	}
	
	private static ItemStack getIcon() {
		ItemStack is = new ItemStack(Material.ANVIL);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.AQUA+"Create directory");
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
					String[] denied = new String[] {"/","\\"};
					for (String s : denied) {
						if (text.contains(s)) {
							pl.sendMessage(ChatColor.RED+"特殊記号は使えません");
							return AnvilGUI.Response.close();
						}
					}
			
					new File(f,text).mkdirs();
					Bukkit.getScheduler().scheduleAsyncDelayedTask(RezxisMCHosting.instance, new Runnable() {
						public void run() {
							GuiOpener.open(player, new ConfigManagerGui(back, f, false));
						}
					}, 2);
					pl.sendMessage(ChatColor.AQUA+"作成されました");
					return AnvilGUI.Response.close();
				})
				.text("フォルダー名を入れてください")
				.plugin(RezxisMCHosting.instance)
				.open(player);
			}},2);
		return ExecReturn.CLOSE;
	}
}
