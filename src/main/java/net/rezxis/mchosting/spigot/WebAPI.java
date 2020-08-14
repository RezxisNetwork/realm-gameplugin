package net.rezxis.mchosting.spigot;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.io.IOUtils;
import org.bukkit.entity.Player;

import com.google.gson.Gson;

import net.rezxis.mchosting.network.packet.sync.SyncFileLog;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class WebAPI {

	private static String key = "YCNaCfoaEwTaVXLH";
	private static OkHttpClient client;
	
	static {
		client = new OkHttpClient.Builder().build();
	}
	
	public static void download(String url, File file, Player player) throws Exception {
		Response res = client.newCall(new Request.Builder().header("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.125 Safari/537.36")
				.url(url).get().build()).execute();
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			IOUtils.copy(res.body().byteStream(), fos);
			player.sendMessage(file.getName()+" was downloaded!");
			HashMap<String, String> map = new HashMap<>();
			map.put("download", player.getUniqueId().toString());
			map.put("url",url);
			map.put("file", file.getName());
			SyncFileLog packet = new SyncFileLog(map);
			RezxisMCHosting.getConn().send(new Gson().toJson(packet));
		} catch (Exception ex) {
			ex.printStackTrace();
			player.sendMessage("Something went to worng : "+ex.getMessage());
		} finally {
			IOUtils.closeQuietly(fos);
		}
	}
	
	public static void download(File file, String secret, String uuid) throws Exception {
		String url = "http://localhost/worlds/api.php?type=download&secretKey="+secret+"&uuid="+uuid;
		Response res = client.newCall(new Request.Builder().header("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.125 Safari/537.36")
				.url(url).get().build()).execute();
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			IOUtils.copy(res.body().byteStream(), fos);
		} catch (Exception ex) {
			ex.printStackTrace();
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
		Response response = client.newCall(new Request.Builder().url("https://paste.mcua.net/pbapi/paste").post(body).build()).execute();
		ret = response.body().string();
		return ret;
	}
}