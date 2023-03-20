package com.olyno.skemail.events.javaxmail;

import com.olyno.skemail.events.bukkit.TransportBukkit;

import jakarta.mail.event.TransportEvent;
import jakarta.mail.event.TransportListener;

public class MailTransport implements TransportListener {

    @Override
    public void messageDelivered(TransportEvent e) {
        new TransportBukkit(e);
    }

    @Override
    public void messageNotDelivered(TransportEvent e) {
        new TransportBukkit(e);
    }

    @Override
    public void messagePartiallyDelivered(TransportEvent e) {
        new TransportBukkit(e);
    }
}
