package com.olyno.skemail.events.skript;

import com.olyno.skemail.events.bukkit.FolderCreatedBukkit;
import com.olyno.skemail.events.bukkit.FolderDeletedBukkit;
import com.olyno.skemail.events.bukkit.FolderRenamedBukkit;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import jakarta.mail.Folder;

public class EvtFolder {

    static {

        // Folder Created
        Skript.registerEvent("Mail Folder Created Event", SimpleEvent.class, FolderCreatedBukkit.class,
                "[[e]mail] folder created"
        );

        EventValues.registerEventValue(FolderCreatedBukkit.class, Folder.class, e -> e.getFolder());

        // Folder renamed
        Skript.registerEvent("Mail Folder Renamed Event", SimpleEvent.class, FolderRenamedBukkit.class,
                "[[e]mail] folder renamed"
        );

        EventValues.registerEventValue(FolderRenamedBukkit.class, Folder.class, e -> e.getFolder());

        // Folder deleted
        Skript.registerEvent("Mail Folder Deleted Event", SimpleEvent.class, FolderDeletedBukkit.class,
                "[[e]mail] folder deleted"
        );

        EventValues.registerEventValue(FolderDeletedBukkit.class, Folder.class, e -> e.getFolder());
    }

}
