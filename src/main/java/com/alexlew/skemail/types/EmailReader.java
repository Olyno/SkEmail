package com.alexlew.skemail.types;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;

import javax.mail.Message;

public class EmailReader {

    static {
        Classes.registerClass(new ClassInfo<Message>(Message.class, "emailreader")
                .user("email ?reader")
                .name("Email Reader Type")
                .description("Will store data to read mails.")
                .parser(new Parser<Message>() {

                    @Override
                    public String getVariableNamePattern() {
                        return ".+";
                    }

                    @Override
                    public Message parse( String arg0, ParseContext arg1) {
                        return null;
                    }

                    @Override
                    public String toString( Message arg0, int arg1) {
                        return null;
                    }

                    @Override
                    public String toVariableNameString( Message arg0) {
                        return null;
                    }

                }));
    }

    /*public EmailReader() { }

    public EmailReader( Message builder )
    {
        if (builder != null)
        {
            try {
                this.body = builder.getContent().toString();
                this.subject = builder.getSubject();
                ToStringSubject = builder.getSubject();
                this.sender = builder.getFrom().toString();
                for (int i = 0; 0 + 1 < builder.getAllRecipients().length; i++) {
                    this.receivers[i] = builder.getAllRecipients()[i].toString();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    private String body;
    private String subject;
    private String sender;
    private String[] receivers;

    public String getBody() {
        return body;
    }
    public String getSubject() {
        return subject;
    }
    public String getSender() {
        return sender;
    }
    public String[] getReceivers() {
        return receivers;
    }*/
}
