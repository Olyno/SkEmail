package com.olyno.skemail.events.javaxmail;

import com.olyno.skemail.events.bukkit.MailAddedBukkit;
import com.olyno.skemail.events.bukkit.MailRemovedBukkit;

import jakarta.mail.event.MessageCountEvent;
import jakarta.mail.event.MessageCountListener;

public class MailCount implements MessageCountListener {

    @Override
    public void messagesAdded(MessageCountEvent e) {
        new MailAddedBukkit(e);
    }

    @Override
    public void messagesRemoved(MessageCountEvent e) {
        new MailRemovedBukkit(e);
    }
}
