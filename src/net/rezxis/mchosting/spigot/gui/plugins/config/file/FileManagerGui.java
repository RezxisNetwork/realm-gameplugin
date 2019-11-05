package net.rezxis.mchosting.spigot.gui.plugins.config.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import net.rezxis.mchosting.gui.BackItem;
import net.rezxis.mchosting.gui.EmptyItem;
import net.rezxis.mchosting.gui.ExecutableItemStack;
import net.rezxis.mchosting.gui.GuiPresenter;

public class FileManagerGui implements GuiPresenter {

	private File file;
	private GuiPresenter back;
	
	public FileManagerGui(File file, GuiPresenter back) {
		this.file = file;
		this.back = back;
	}
	
	@Override
	public List<ExecutableItemStack> getOptions(Player player) {
		ArrayList<ExecutableItemStack> items = new ArrayList<>();
		items.add(new BackItem(back));
		items.add(new FileInfoItem(file));
		items.add(new EmptyItem());
		items.add(new DeleteThisFile(back,file));
		items.add(new DownloadThisFile(this,file));
		items.add(new UploadThisFile(this,file));
		return items;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return "Manage File";
	}

	@Override
	public String getEmptyMessage() {
		// TODO Auto-generated method stub
		return "Something went to worng";
	}

}
