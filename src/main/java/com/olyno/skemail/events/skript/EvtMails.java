package com.olyno.skemail.events.skript;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;

import javax.mail.Folder;
import javax.mail.Message;

import com.olyno.skemail.events.bukkit.MailReceivedBukkit;
import com.olyno.skemail.events.bukkit.MailRemovedBukkit;

public class EvtMails {

    static {
        Skript.registerEvent("Email Received Event", SimpleEvent.class, MailReceivedBukkit.class,
            "[e]mail receive[d]"
        );

        EventValues.registerEventValue(MailReceivedBukkit.class, Message.class, new Getter<Message, MailReceivedBukkit>() {

            @Override
            public Message get(MailReceivedBukkit e) {
                return e.getMessage();
            }
        }, 0);

        EventValues.registerEventValue(MailReceivedBukkit.class, Folder.class, new Getter<Folder, MailReceivedBukkit>() {

            @Override
            public Folder get(MailReceivedBukkit e) {
                return e.getFolder();
            }
        }, 0);

        Skript.registerEvent("Email Removed Event", SimpleEvent.class, MailRemovedBukkit.class,
            "[e]mail (remove|delete)[d]"
        );

        EventValues.registerEventValue(MailRemovedBukkit.class, Message.class, new Getter<Message, MailRemovedBukkit>() {

            @Override
            public Message get(MailRemovedBukkit e) {
                return e.getMessages()[0];
            }
        }, 0);
    }

}
