package com.olyno.skemail.events.javaxmail;

import com.olyno.skemail.events.bukkit.ConnectionBukkit;
import com.olyno.skemail.events.bukkit.DisconnectionBukkit;

import javax.mail.event.ConnectionEvent;
import javax.mail.event.ConnectionListener;

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
