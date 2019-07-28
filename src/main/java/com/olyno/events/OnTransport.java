package com.olyno.events;

import com.olyno.SkEmail;
import com.olyno.events.bukkit.TransportBukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import javax.mail.Message;

public class OnTransport implements Listener {

    public OnTransport(SkEmail plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onTransport(TransportBukkit event) {
        Message message = event.getMessage();
    }

}