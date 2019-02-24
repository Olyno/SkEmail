package com.alexlew.skemail.events.javaxmail;

import com.alexlew.skemail.events.bukkit.ConnectionBukkit;
import com.alexlew.skemail.events.bukkit.DisconnectionBukkit;
import com.alexlew.skemail.events.bukkit.TransportBukkit;
import org.bukkit.Bukkit;

import javax.mail.event.ConnectionEvent;
import javax.mail.event.ConnectionListener;

public class MailConnection implements ConnectionListener {
	
	@Override
	public void opened( ConnectionEvent e ) {
		Bukkit.getServer().getPluginManager().callEvent(new ConnectionBukkit(e));
	}
	
	@Override
	public void disconnected( ConnectionEvent e ) {	}
	
	@Override
	public void closed( ConnectionEvent e ) {
		Bukkit.getServer().getPluginManager().callEvent(new DisconnectionBukkit(e));
	}
}
