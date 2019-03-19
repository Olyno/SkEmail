package com.alexlew.skemail.events;

import com.alexlew.skemail.SkEmail;
import com.alexlew.skemail.events.bukkit.MailReceivedBukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import javax.mail.Message;

public class OnMailReceived implements Listener {

	public OnMailReceived(SkEmail plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onTransport(MailReceivedBukkit event) {
		Message message = event.getMessage();
	}

}