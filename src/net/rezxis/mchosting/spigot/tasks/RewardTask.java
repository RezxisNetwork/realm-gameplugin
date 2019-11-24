package net.rezxis.mchosting.spigot.tasks;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;
import net.rezxis.mchosting.databse.DBPlayer;
import net.rezxis.mchosting.spigot.RezxisMCHosting;

public class RewardTask extends BukkitRunnable {

	private boolean first = true;
	
	@Override
	public void run() {
		if (first) {
			first = false;
			return;
		}
		for (Player player : Bukkit.getOnlinePlayers()) {
			int coin = 50;
			DBPlayer dp = RezxisMCHosting.getPTable().get(player.getUniqueId());
			player.sendMessage(ChatColor.AQUA+"おつかれさまでした! "+ChatColor.LIGHT_PURPLE+coin+"枚"+ChatColor.AQUA+"のコインが手に入った");
			player.sendMessage(ChatColor.GREEN+"報酬箱を手に入れました！ロビーでチェストをクリックして、開けます！");
			dp.addCoin(coin);
			int[] box = dp.getBoxes();
			if (new Random().nextInt(100) <= 90) {
				box[1] += 1;
			} else {
				box[0] += 1;
			}
			dp.setBoxes(box);
			dp.update();
		}
	}
}
