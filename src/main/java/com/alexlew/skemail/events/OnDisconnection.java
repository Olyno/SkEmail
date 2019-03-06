package com.alexlew.skemail.events;

import com.alexlew.skemail.SkEmail;
import com.alexlew.skemail.events.bukkit.DisconnectionBukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnDisconnection implements Listener {
	
	public OnDisconnection( SkEmail plugin ) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onDisconnection( DisconnectionBukkit event ) {
	}
	
}