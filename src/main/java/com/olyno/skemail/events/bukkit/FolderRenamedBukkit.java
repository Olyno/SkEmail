package com.olyno.skemail.events.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import jakarta.mail.Folder;
import jakarta.mail.event.FolderEvent;

public class FolderRenamedBukkit extends Event implements Listener {

    public static final HandlerList handlers = new HandlerList();

    private Folder folder;

    public FolderRenamedBukkit(FolderEvent folderevent) {
        this.folder = folderevent.getFolder();
        Bukkit.getServer().getPluginManager().callEvent(this);
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public Folder getFolder() {
        return folder;
    }
}
