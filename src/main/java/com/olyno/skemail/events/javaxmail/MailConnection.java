package com.olyno.skemail.events.javaxmail;

import com.olyno.skemail.events.bukkit.ConnectionBukkit;
import com.olyno.skemail.events.bukkit.DisconnectionBukkit;

import jakarta.mail.event.ConnectionEvent;
import jakarta.mail.event.ConnectionListener;

public class MailConnection implements ConnectionListener {

    @Override
    public void opened(ConnectionEvent e) {
        new ConnectionBukkit(e);
    }

    @Override
    public void disconnected(ConnectionEvent e) {
    }

    @Override
    public void closed(ConnectionEvent e) {
        new DisconnectionBukkit(e);
    }
}
