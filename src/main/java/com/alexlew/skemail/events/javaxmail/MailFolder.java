package com.alexlew.skemail.events.javaxmail;

import com.alexlew.skemail.events.bukkit.FolderCreatedBukkit;
import com.alexlew.skemail.events.bukkit.FolderDeletedBukkit;
import com.alexlew.skemail.events.bukkit.FolderRenamedBukkit;
import org.bukkit.Bukkit;

import javax.mail.event.FolderEvent;
import javax.mail.event.FolderListener;

public class MailFolder implements FolderListener {
	
	@Override
	public void folderCreated( FolderEvent e ) {
		Bukkit.getServer().getPluginManager().callEvent(new FolderCreatedBukkit(e));
	}
	
	@Override
	public void folderDeleted( FolderEvent e ) {
		Bukkit.getServer().getPluginManager().callEvent(new FolderDeletedBukkit(e));
	}
	
	@Override
	public void folderRenamed( FolderEvent e ) {
		Bukkit.getServer().getPluginManager().callEvent(new FolderRenamedBukkit(e));
	}
}
