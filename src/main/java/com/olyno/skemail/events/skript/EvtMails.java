package com.olyno.skemail.events.skript;

import com.olyno.skemail.events.bukkit.MailReceivedBukkit;
import com.olyno.skemail.events.bukkit.MailRemovedBukkit;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import jakarta.mail.Folder;
import jakarta.mail.Message;

public class EvtMails {

    static {
        Skript.registerEvent("Email Received Event", SimpleEvent.class, MailReceivedBukkit.class,
            "[e]mail receive[d]"
        );

        EventValues.registerEventValue(MailReceivedBukkit.class, Message.class, e -> e.getMessage());
        EventValues.registerEventValue(MailReceivedBukkit.class, Folder.class, e -> e.getFolder());

        Skript.registerEvent("Email Removed Event", SimpleEvent.class, MailRemovedBukkit.class,
            "[e]mail (remove|delete)[d]"
        );

        EventValues.registerEventValue(MailRemovedBukkit.class, Message.class, e -> e.getMessages()[0]);
    }

}
