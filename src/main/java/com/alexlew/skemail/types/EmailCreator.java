package com.alexlew.skemail.types;

import com.alexlew.skemail.scopes.ScopeEmailCreator;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmailCreator {

    static {
        Classes.registerClass(new ClassInfo<EmailCreator>(EmailCreator.class, "emailcreator")
                .user("emailcreator")
                .name("emailcreator")
                .parser(new Parser<EmailCreator>() {

                    @Override
                    public String getVariableNamePattern() {
                        return ".+";
                    }

                    @Override
                    public EmailCreator parse(String arg0, ParseContext arg1) {
                        return null;
                    }

                    @Override
                    public String toString(EmailCreator arg0, int arg1) {
                        return arg0.getInfos();
                    }

                    @Override
                    public String toVariableNameString(EmailCreator arg0) {
                        return null;
                    }

                }));
    }

    public EmailCreator() { }

    public EmailCreator(EmailCreator builder)
    {
        if (builder != null)
        {
            ScopeEmailCreator.lastEmailCreator.body = builder.body;
            ScopeEmailCreator.lastEmailCreator.object = builder.object;
            ScopeEmailCreator.lastEmailCreator.author = builder.author;
            ScopeEmailCreator.lastEmailCreator.receivers = builder.receivers;
            ScopeEmailCreator.lastEmailCreator.attach_files = builder.attach_files;
        }
    }

    private String body;
    private String object;
    private String author;
    private String[] receivers;
    private String[] attach_files;


    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        ScopeEmailCreator.lastEmailCreator.body = body;
    }
    public String getObject() {
        return object;
    }
    public void setObject(String object) {
        ScopeEmailCreator.lastEmailCreator.object = object;
    }

    public String getInfos() {
        String infos = "Receivers:" + ScopeEmailCreator.lastEmailCreator.getReceivers().toString() +
                ";Author:" + ScopeEmailCreator.lastEmailCreator.getAuthor() +
                ";Body:" + ScopeEmailCreator.lastEmailCreator.getBody() +
                ";Object:" + ScopeEmailCreator.lastEmailCreator.getObject() +
                ";Attachments:" + ScopeEmailCreator.lastEmailCreator.getAttachments().toString();
        return infos;
    }

    public void setAttachment(String attach_file) {
        ScopeEmailCreator.lastEmailCreator.attach_files = new String[]{attach_file};
    }

    public String[] getAttachments() {
        return attach_files;
    }

    public void addAttachment(String attach_file) {
        if (ScopeEmailCreator.lastEmailCreator.attach_files != null) {
            String[] str_array = attach_files;
            List<String> list = new ArrayList<String>(Arrays.asList(str_array));
            if (ScopeEmailCreator.lastEmailCreator.attach_files.length > 0) {
                list.remove(attach_file);
            }
            list.add(attach_file);
            attach_files = list.toArray(new String[0]);
        } else {
            attach_files = new String[]{attach_file};
        }



    }
    public void removeAttachment(String attach_file) {
        if (ScopeEmailCreator.lastEmailCreator.attach_files != null) {
            if (ScopeEmailCreator.lastEmailCreator.attach_files.length > 0) {
                String[] str_array = attach_files;
                List<String> list = new ArrayList<String>(Arrays.asList(str_array));
                list.remove(attach_file);
                attach_files = list.toArray(new String[0]);
            }
        }


    }

    public void setReceiver(String receiver) {
        ScopeEmailCreator.lastEmailCreator.receivers = new String[]{receiver};
    }
    public String[] getReceivers() {
        return receivers;
    }
    public void addReceiver(String receiver) {
        if (ScopeEmailCreator.lastEmailCreator.receivers != null) {
            String[] str_array = receivers;
            List<String> list = new ArrayList<>(Arrays.asList(str_array));
            if (ScopeEmailCreator.lastEmailCreator.receivers.length > 0) {
                list.remove(receiver);list.add(receiver);
            }
            list.add(receiver);
            receivers = list.toArray(new String[0]);
        } else {
            receivers = new String[]{receiver};
        }

    }

    public void removeReceiver(String receiver) {
        if (ScopeEmailCreator.lastEmailCreator.receivers != null) {
            if (ScopeEmailCreator.lastEmailCreator.receivers.length > 0) {
                String[] str_array = receivers;
                List<String> list = new ArrayList<String>(Arrays.asList(str_array));
                list.remove(receiver);
                receivers = list.toArray(new String[0]);
            }
        }


    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor( String author ) {
        this.author = author;
    }
}
