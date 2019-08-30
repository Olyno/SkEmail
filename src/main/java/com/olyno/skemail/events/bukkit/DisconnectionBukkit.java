package com.olyno.skemail.events.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import javax.mail.event.ConnectionEvent;

public class DisconnectionBukkit extends Event implements Listener {

    public static final HandlerList handlers = new HandlerList();

    public DisconnectionBukkit(ConnectionEvent disconnection) {
        Bukkit.getServer().getPluginManager().callEvent(this);
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

}
