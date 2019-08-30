package com.olyno.skemail.events.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import javax.mail.Folder;
import javax.mail.Message;

public class MailReceivedBukkit extends Event implements Listener {

    public static final HandlerList handlers = new HandlerList();

    private Message message;
    private Folder folder;

    public MailReceivedBukkit(Message message, Folder folder) {
        this.message = message;
        this.folder = folder;
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

    public Folder getFolder() {
        return folder;
    }
}
