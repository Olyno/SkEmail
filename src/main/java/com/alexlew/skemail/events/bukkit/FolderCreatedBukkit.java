package com.alexlew.skemail.events.bukkit;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import javax.mail.Folder;
import javax.mail.event.FolderEvent;

public class FolderCreatedBukkit extends Event {
	
	public static final HandlerList handlers = new HandlerList();
	
	private Folder folder;
	
	public FolderCreatedBukkit( FolderEvent folderevent ) {
		folder = folderevent.getFolder();
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public Folder getFolder() {
		return folder;
	}
}
