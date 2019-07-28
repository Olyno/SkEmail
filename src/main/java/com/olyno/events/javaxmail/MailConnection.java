package com.olyno.events.javaxmail;

import com.olyno.events.bukkit.ConnectionBukkit;
import com.olyno.events.bukkit.DisconnectionBukkit;
import org.bukkit.Bukkit;

import javax.mail.event.ConnectionEvent;
import javax.mail.event.ConnectionListener;

public class MailConnection implements ConnectionListener {

    @Override
    public void opened(ConnectionEvent e) {
        Bukkit.getServer().getPluginManager().callEvent(new ConnectionBukkit(e));
    }

    @Override
    public void disconnected(ConnectionEvent e) {
    }

    @Override
    public void closed(ConnectionEvent e) {
        Bukkit.getServer().getPluginManager().callEvent(new DisconnectionBukkit(e));
    }
}
