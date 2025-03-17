package com.olyno.skemail.events.skript;

import com.olyno.skemail.events.bukkit.TransportBukkit;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import jakarta.mail.Message;

public class EvtTransport {

    static {
        Skript.registerEvent("Email Sent Event", SimpleEvent.class, TransportBukkit.class,
                "[e]mail (sent|transport)"
        );

        EventValues.registerEventValue(TransportBukkit.class, Message.class, e -> e.getMessage());
    }

}
