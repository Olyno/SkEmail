package com.olyno.skemail.events.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import javax.mail.Message;
import javax.mail.event.MessageCountEvent;

public class MailRemovedBukkit extends Event implements Listener {

    public static final HandlerList handlers = new HandlerList();

    private Message[] messages;

    public MailRemovedBukkit(MessageCountEvent event) {
        this.messages = event.getMessages();
        Bukkit.getServer().getPluginManager().callEvent(this);
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public Message[] getMessages() {
        return messages;
    }
}
