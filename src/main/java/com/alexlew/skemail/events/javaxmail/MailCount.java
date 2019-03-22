package com.alexlew.skemail.events.javaxmail;

import com.alexlew.skemail.events.bukkit.MailAddedBukkit;
import com.alexlew.skemail.events.bukkit.MailRemovedBukkit;
import org.bukkit.Bukkit;

import javax.mail.event.MessageCountEvent;
import javax.mail.event.MessageCountListener;

public class MailCount implements MessageCountListener {

	@Override
	public void messagesAdded(MessageCountEvent e) {
		Bukkit.getServer().getPluginManager().callEvent(new MailAddedBukkit(e));
	}

	@Override
	public void messagesRemoved(MessageCountEvent e) {
		Bukkit.getServer().getPluginManager().callEvent(new MailRemovedBukkit(e));
	}
}
