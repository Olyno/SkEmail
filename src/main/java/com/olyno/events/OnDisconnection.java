package com.olyno.events;

import com.olyno.SkEmail;
import com.olyno.events.bukkit.DisconnectionBukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnDisconnection implements Listener {

    public OnDisconnection(SkEmail plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onDisconnection(DisconnectionBukkit event) {
    }

}