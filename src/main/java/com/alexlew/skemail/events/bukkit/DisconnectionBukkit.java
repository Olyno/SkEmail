package com.alexlew.skemail.events.bukkit;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import javax.mail.event.ConnectionEvent;

public class DisconnectionBukkit extends Event {
	
	public static final HandlerList handlers = new HandlerList();
	
	public DisconnectionBukkit( ConnectionEvent disconnection ) {	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
}
