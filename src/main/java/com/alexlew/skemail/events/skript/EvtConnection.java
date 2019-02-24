package com.alexlew.skemail.events.skript;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import com.alexlew.skemail.events.bukkit.ConnectionBukkit;
import com.alexlew.skemail.events.bukkit.DisconnectionBukkit;
import com.alexlew.skemail.events.bukkit.TransportBukkit;

import javax.mail.Message;

public class EvtConnection {
	
	static {
		Skript.registerEvent("Mail Connection Event", SimpleEvent.class, ConnectionBukkit.class,
				"[e]mail connect(ed|ion)"
		);
		
		Skript.registerEvent("Mail Disconnection Event", SimpleEvent.class, DisconnectionBukkit.class,
				"[e]mail disconnect(ed|ion)"
		);
	}
	
}
