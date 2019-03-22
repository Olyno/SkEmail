package com.alexlew.skemail.events.bukkit;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import javax.mail.Message;
import javax.mail.event.MessageCountEvent;

public class MailRemovedBukkit extends Event {

	public static final HandlerList handlers = new HandlerList();

	private Message[] messages;

	public MailRemovedBukkit(MessageCountEvent event) {
		this.messages = event.getMessages();
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public Message[] getMessages() {
		return messages;
	}
}
