package com.alexlew.skemail.events.bukkit;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import javax.mail.Message;
import javax.mail.event.TransportEvent;

public class TransportBukkit extends Event {
	
	public static final HandlerList handlers = new HandlerList();
	
	private Message message;
	
	public TransportBukkit( TransportEvent transport ) {
		this.message = transport.getMessage();
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public Message getMessage() {
		return message;
	}
}
