package com.alexlew.skemail.types;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.util.Date;

public class EmailReader {

    static {
        Classes.registerClass(new ClassInfo<EmailReader>(EmailReader.class, "emailreader")
                .user("emailreader")
                .name("emailreader")
                .parser(new Parser<EmailReader>() {

                    @Override
                    public String getVariableNamePattern() {
                        return ".+";
                    }

                    @Override
                    public EmailReader parse(String arg0, ParseContext arg1) {
                        return null;
                    }

                    @Override
                    public String toString(EmailReader arg0, int arg1) {
                        return lastEmailReader.object;
                    }

                    @Override
                    public String toVariableNameString(EmailReader arg0) {
                        return null;
                    }

                }));
    }

    public EmailReader() { }

    public EmailReader(EmailReader builder)
    {
        if (builder != null)
        {
            lastEmailReader.body = builder.body;
            lastEmailReader.author = builder.author;
            lastEmailReader.object = builder.object;
            lastEmailReader.sent_date = builder.sent_date;
            lastEmailReader.receivers = builder.receivers;
            lastEmailReader.attach_files = builder.attach_files;
        }
    }

    private String body;
    private String author;
    private String object;
    private Date sent_date;
    private String[] receivers;
    private String[] attach_files;

    public static EmailReader lastEmailReader;


    public String getBody() {
        return body;
    }
    public String getAuthor() {
        return author;
    }
    public String getObject() {
        return object;
    }

    public String getInfos() {
        String infos = "Receivers:" + lastEmailReader.getReceivers().toString();
        return infos;
    }
    public String[] getAttachments() {
        return attach_files;
    }
    public String[] getReceivers() {
        return receivers;
    }

}
