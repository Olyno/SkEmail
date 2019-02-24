package com.alexlew.skemail.events.javaxmail;

import com.alexlew.skemail.events.bukkit.TransportBukkit;
import org.bukkit.Bukkit;

import javax.mail.event.TransportEvent;
import javax.mail.event.TransportListener;

public class MailTransport implements TransportListener {
	
	public MailTransport() {
	
	}
	
	@Override
	public void messageDelivered( TransportEvent e ) {
		Bukkit.getServer().getPluginManager().callEvent(new TransportBukkit(e));
	}
	
	@Override
	public void messageNotDelivered( TransportEvent e ) {
		Bukkit.getServer().getPluginManager().callEvent(new TransportBukkit(e));
	}
	
	@Override
	public void messagePartiallyDelivered( TransportEvent e ) {
		Bukkit.getServer().getPluginManager().callEvent(new TransportBukkit(e));
	}
}
