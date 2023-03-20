package com.olyno.skemail.events.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import jakarta.mail.Message;
import jakarta.mail.event.TransportEvent;

public class TransportBukkit extends Event implements Listener {

    public static final HandlerList handlers = new HandlerList();

    private Message message;

    public TransportBukkit(TransportEvent transport) {
        this.message = transport.getMessage();
        Bukkit.getServer().getPluginManager().callEvent(this);
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public Message getMessage() {
        return message;
    }
}
