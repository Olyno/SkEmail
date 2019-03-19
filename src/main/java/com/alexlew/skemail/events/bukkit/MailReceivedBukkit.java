package com.alexlew.skemail.events.bukkit;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import javax.mail.Message;

public class MailReceivedBukkit extends Event {

	public static final HandlerList handlers = new HandlerList();

	private Message message;

	public MailReceivedBukkit(Message message) {
		this.message = message;
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
