package com.alexlew.skemail.events;

import com.alexlew.skemail.SkEmail;
import com.alexlew.skemail.events.bukkit.TransportBukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import javax.mail.Message;

public class OnTransport implements Listener {
	
	public OnTransport( SkEmail plugin ) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onTransport( TransportBukkit event ) {
		Message message = event.getMessage();
	}
	
}