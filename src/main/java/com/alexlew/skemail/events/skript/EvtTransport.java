package com.alexlew.skemail.events.skript;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import com.alexlew.skemail.events.bukkit.TransportBukkit;

import javax.mail.Message;

public class EvtTransport {
	
	static {
		Skript.registerEvent("Email Sent Event", SimpleEvent.class, TransportBukkit.class,
				"[e]mail (sent|transport)"
		);
		
		EventValues.registerEventValue(TransportBukkit.class, Message.class, new Getter<Message, TransportBukkit>() {
			
			@Override
			public Message get( TransportBukkit e ) {
				return e.getMessage();
			}
		}, 0);
	}
	
}
