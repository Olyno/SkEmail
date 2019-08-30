package com.olyno.skemail.events.skript;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import com.olyno.skemail.events.bukkit.ConnectionBukkit;
import com.olyno.skemail.events.bukkit.DisconnectionBukkit;

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
