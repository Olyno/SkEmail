package com.alexlew.skemail.types;

import com.alexlew.skemail.effects.EffConnection;
import com.alexlew.skemail.scopes.ScopeEmail;

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
                        return EffConnection.username;
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
            ScopeEmail.lastEmailCreator.body = builder.body;
            ScopeEmail.lastEmailCreator.object = builder.object;
            ScopeEmail.lastEmailCreator.receivers = builder.receivers;
            ScopeEmail.lastEmailCreator.attach_files = builder.attach_files;
        }
    }

    private String body;
    private String object;
    private String[] receivers;
    private String[] attach_files;


    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        ScopeEmail.lastEmailCreator.body = body;
    }
    public String getObject() {
        return object;
    }
    public void setObject(String object) {
        ScopeEmail.lastEmailCreator.object = object;
    }

    public String getInfos() {
        String infos = "Receivers:" + ScopeEmail.lastEmailCreator.getReceivers().toString() +
                ";Body:" + ScopeEmail.lastEmailCreator.getBody() +
                ";Object:" + ScopeEmail.lastEmailCreator.getObject() +
                ";Attachments:" + ScopeEmail.lastEmailCreator.getAttachments().toString();
        return infos;
    }

    public void setAttachment(String attach_file) {
        ScopeEmail.lastEmailCreator.attach_files = new String[]{attach_file};
    }

    public String[] getAttachments() {
        return attach_files;
    }
    public void addAttachment(String attach_file) {
        if (ScopeEmail.lastEmailCreator.attach_files != null) {
            String[] str_array = attach_files;
            List<String> list = new ArrayList<String>(Arrays.asList(str_array));
            if (ScopeEmail.lastEmailCreator.attach_files.length > 0) {
                list.remove(attach_file);
            }
            list.add(attach_file);
            attach_files = list.toArray(new String[0]);
        } else {
            attach_files = new String[]{attach_file};
        }



    }
    public void removeAttachment(String attach_file) {
        if (ScopeEmail.lastEmailCreator.attach_files != null) {
            if (ScopeEmail.lastEmailCreator.attach_files.length > 0) {
                String[] str_array = attach_files;
                List<String> list = new ArrayList<String>(Arrays.asList(str_array));
                list.remove(attach_file);
                attach_files = list.toArray(new String[0]);
            }
        }


    }

    public void setReceiver(String receiver) {
        ScopeEmail.lastEmailCreator.receivers = new String[]{receiver};
    }
    public String[] getReceivers() {
        return receivers;
    }
    public void addReceiver(String receiver) {
        if (ScopeEmail.lastEmailCreator.receivers != null) {
            String[] str_array = receivers;
            List<String> list = new ArrayList<>(Arrays.asList(str_array));
            if (ScopeEmail.lastEmailCreator.receivers.length > 0) {
                list.remove(receiver);list.add(receiver);
            }
            list.add(receiver);
            receivers = list.toArray(new String[0]);
        } else {
            receivers = new String[]{receiver};
        }

    }

    public void removeReceiver(String receiver) {
        if (ScopeEmail.lastEmailCreator.receivers != null) {
            if (ScopeEmail.lastEmailCreator.receivers.length > 0) {
                String[] str_array = receivers;
                List<String> list = new ArrayList<String>(Arrays.asList(str_array));
                list.remove(receiver);
                receivers = list.toArray(new String[0]);
            }
        }


    }

}
