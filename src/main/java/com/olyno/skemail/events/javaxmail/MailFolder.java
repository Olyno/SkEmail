package com.olyno.skemail.events.javaxmail;

import com.olyno.skemail.events.bukkit.FolderCreatedBukkit;
import com.olyno.skemail.events.bukkit.FolderDeletedBukkit;
import com.olyno.skemail.events.bukkit.FolderRenamedBukkit;

import javax.mail.event.FolderEvent;
import javax.mail.event.FolderListener;

public class MailFolder implements FolderListener {

    @Override
    public void folderCreated(FolderEvent e) {
        new FolderCreatedBukkit(e);
    }

    @Override
    public void folderDeleted(FolderEvent e) {
        new FolderDeletedBukkit(e);
    }

    @Override
    public void folderRenamed(FolderEvent e) {
        new FolderRenamedBukkit(e);
    }
}
