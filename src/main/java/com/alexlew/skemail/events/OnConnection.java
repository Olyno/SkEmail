package com.alexlew.skemail.events;

import com.alexlew.skemail.SkEmail;
import com.alexlew.skemail.events.bukkit.ConnectionBukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnConnection implements Listener {
	
	public OnConnection( SkEmail plugin ) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onConnection( ConnectionBukkit event ) {

	}
	
}