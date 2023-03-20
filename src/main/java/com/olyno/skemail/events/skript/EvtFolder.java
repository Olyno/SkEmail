package com.olyno.skemail.events.skript;

import com.olyno.skemail.events.bukkit.FolderCreatedBukkit;
import com.olyno.skemail.events.bukkit.FolderDeletedBukkit;
import com.olyno.skemail.events.bukkit.FolderRenamedBukkit;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import jakarta.mail.Folder;

public class EvtFolder {

    static {

        // Folder Created
        Skript.registerEvent("Mail Folder Created Event", SimpleEvent.class, FolderCreatedBukkit.class,
                "[[e]mail] folder created"
        );

        EventValues.registerEventValue(FolderCreatedBukkit.class, Folder.class, new Getter<Folder, FolderCreatedBukkit>() {

            @Override
            public Folder get(FolderCreatedBukkit e) {
                return e.getFolder();
            }
        }, 0);

        // Folder renamed
        Skript.registerEvent("Mail Folder Renamed Event", SimpleEvent.class, FolderRenamedBukkit.class,
                "[[e]mail] folder renamed"
        );

        EventValues.registerEventValue(FolderRenamedBukkit.class, Folder.class, new Getter<Folder, FolderRenamedBukkit>() {

            @Override
            public Folder get(FolderRenamedBukkit e) {
                return e.getFolder();
            }
        }, 0);

        // Folder deleted
        Skript.registerEvent("Mail Folder Deleted Event", SimpleEvent.class, FolderDeletedBukkit.class,
                "[[e]mail] folder deleted"
        );

        EventValues.registerEventValue(FolderDeletedBukkit.class, Folder.class, new Getter<Folder, FolderDeletedBukkit>() {

            @Override
            public Folder get(FolderDeletedBukkit e) {
                return e.getFolder();
            }
        }, 0);
    }

}
