package net.rezxis.mchosting.spigot.gui.plugins.config.file;

import java.io.File;
import java.util.Date;

import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.database.Tables;
import net.rezxis.mchosting.database.object.internal.DBFile;
import net.rezxis.mchosting.database.object.internal.DBFile.Type;
import net.rezxis.mchosting.database.object.server.DBServer.GameType;
import net.rezxis.mchosting.gui.GUIAction;
import net.rezxis.mchosting.gui.GUIItem;
import net.rezxis.mchosting.gui.GUIWindow;
import net.rezxis.mchosting.spigot.WebAPI;
import net.rezxis.mchosting.spigot.RezxisMCHosting;
import net.wesjd.anvilgui.AnvilGUI;

public class UploadFileItem extends GUIItem {

	private File file;
	private GUIWindow back;
	
	public UploadFileItem(File file, GUIWindow back) {
		super(getIcon());
		this.file = file;
		this.back = back;
	}

	private static ItemStack getIcon() {
		ItemStack is = new ItemStack(Material.WEB);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.AQUA+"アップロード");
		is.setItemMeta(im);
		return is;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public GUIAction invClick(InventoryClickEvent e) {
		if (RezxisMCHosting.getDBServer(false).getType() == GameType.NORMAL) {
			Bukkit.getScheduler().scheduleAsyncDelayedTask(RezxisMCHosting.instance, new Runnable() {
				public void run() {
				new AnvilGUI.Builder()
				.onClose(pl -> {})
				.onComplete((pl,text) -> {
					final String link;
					if (text == null) {
						return AnvilGUI.Response.text("URLを入れてください。");
					} else {
						if (text.contains("paste.mcua.net/raw/")) {
							link = text;
						} else {
							if (text.contains("paste.mcua.net/v/")) {
								link = text.replace("/v/", "/raw/");
							} else {
								pl.sendMessage(ChatColor.RED+"https://paste.mcua.net を使ってください。");
								return AnvilGUI.Response.close();
							}
						}
					}
					new Thread(()->{
					try {
						WebAPI.download(link, file, pl);
					} catch (Exception e) {
						e.printStackTrace();
						pl.sendMessage("uploadできませんでした");
					}
					}).start();
					back.delayShow();
					return AnvilGUI.Response.close();
				})
				.text("URLを入れてください。")
				.plugin(RezxisMCHosting.instance)
				.open((Player) e.getWhoClicked());
				}},2);
		} else {
			DBFile dfile = Tables.getFTable().get2(e.getWhoClicked().getUniqueId().toString(), Type.PLUGIN);
			if (dfile == null) {
				dfile = new DBFile(e.getWhoClicked().getUniqueId().toString(),
						RandomStringUtils.randomAlphabetic(10),
						false, new Date(), Type.PLUGIN);
				Tables.getFTable().insert(dfile);
			}
			final DBFile f = dfile;
			if (!dfile.isUploaded()) {
				String url = "https://world.rezxis.net/upload.php?uuid="+dfile.getUUID()+"&secretKey="+dfile.getSecret();
				e.getWhoClicked().sendMessage(ChatColor.YELLOW+"アップロードしてください。"+ChatColor.GREEN+url);
			} else {
				e.getWhoClicked().sendMessage(ChatColor.GREEN+"適応中。");
				Bukkit.getScheduler().runTaskAsynchronously(RezxisMCHosting.instance, new BukkitRunnable() {
					public void run() {
						try {
							WebAPI.download(file, f.getSecret(), f.getUUID());
						} catch (Exception ex) {
							ex.printStackTrace();
							e.getWhoClicked().sendMessage(ChatColor.RED+"エラーが発生しました。");
							e.getWhoClicked().sendMessage(ex.getMessage());
						}
					}
				});
			}
		}
		return GUIAction.CLOSE;
	}
}
