package com.olyno.events.javaxmail;

import com.olyno.events.bukkit.MailAddedBukkit;
import com.olyno.events.bukkit.MailRemovedBukkit;
import org.bukkit.Bukkit;

import javax.mail.event.MessageCountEvent;
import javax.mail.event.MessageCountListener;

public class MailCount implements MessageCountListener {

    @Override
    public void messagesAdded(MessageCountEvent e) {
        Bukkit.getServer().getPluginManager().callEvent(new MailAddedBukkit(e));
    }

    @Override
    public void messagesRemoved(MessageCountEvent e) {
        Bukkit.getServer().getPluginManager().callEvent(new MailRemovedBukkit(e));
    }
}
