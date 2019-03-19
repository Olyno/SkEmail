package com.alexlew.skemail.events.skript;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import com.alexlew.skemail.events.bukkit.MailReceivedBukkit;
import org.apache.commons.lang.StringUtils;
import org.bukkit.event.Event;

// TODO Finish this event.
public class EvtMailReceived extends SkriptEvent {

	static {
		Skript.registerEvent("Mail Received", EvtMailReceived.class, MailReceivedBukkit.class, "[e]mail received in [(box|folder|dir)] %string%");
	}

	Literal<String> box;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parseResult) {
		box = (Literal<String>) args[0];
		return true;
	}

	@Override
	public boolean check(Event e) {
		return !StringUtils.isBlank(box.getSingle(e));
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "Mail Received in box " + box.toString(e, debug);
	}
}
