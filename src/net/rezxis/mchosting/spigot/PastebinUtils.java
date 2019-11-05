package net.rezxis.mchosting.spigot;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PastebinUtils {

	private static String key = "b584fa19f7b98a7990da242d20afae3c";
	private static OkHttpClient client;
	
	static {
		client = new OkHttpClient.Builder().build();
	}
	
	public static void download(String url, File file, Player player) throws Exception {
		if (!url.contains("pastebin")) {
			player.sendMessage(ChatColor.RED+"you can use only pastebin to upload configs!");
			return;
		}
		Response res = client.newCall(new Request.Builder().url(url).get().build()).execute();
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			IOUtils.copy(res.body().byteStream(), fos);
			player.sendMessage(file.getName()+" was downloaded!");
		} catch (Exception ex) {
			ex.printStackTrace();
			player.sendMessage("Something went to worng : "+ex.getMessage());
		} finally {
			IOUtils.closeQuietly(fos);
		}
	}
	
	public static String upload(String data, String name) throws IOException {
		String ret = null;
		RequestBody body = new FormBody.Builder()
		.add("api_option", "paste")
		.add("api_dev_key", key)
		.add("api_paste_code", data)
		.add("api_paste_private", "1")
		.add("api_paste_name", name)
		.add("api_paste_expire_date","10M")
		.add("api_paste_format", "text").build();
		Response response = client.newCall(new Request.Builder().url("http://pastebin.com/api/api_post.php").post(body).build()).execute();
		ret = response.body().string();
		return ret;
	}
}