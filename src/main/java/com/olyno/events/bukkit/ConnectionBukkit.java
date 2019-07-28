package com.olyno.events.bukkit;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import javax.mail.event.ConnectionEvent;

public class ConnectionBukkit extends Event {

    public static final HandlerList handlers = new HandlerList();

    public ConnectionBukkit(ConnectionEvent connection) {
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

}
