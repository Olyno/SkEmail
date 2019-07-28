package com.olyno.types;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import com.olyno.SkEmail;
import com.olyno.effects.EffConnection;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;

public class Email {

    static {

        // Email
        Classes.registerClass(new ClassInfo<>(Message.class, "email")
                .defaultExpression(new EventValueExpression<>(Message.class))
                .user("e?mail")
                .name("Email")
                .parser(new Parser<Message>() {

                    @Override
                    public String getVariableNamePattern() {
                        return ".+";
                    }

                    @Override
                    public Message parse(String arg0, ParseContext arg1) {
                        return null;
                    }

                    @Override
                    public String toString(Message arg0, int arg1) {
                        try {
                            StringBuilder back = new StringBuilder(arg0.getSubject() + "\n");
                            if (arg0.getSize() > 0 && arg0.getContent() != null) {
                                Multipart multipart = (MimeMultipart) arg0.getContent();
                                if (multipart.getCount() > 0) {
                                    for (int i = 0; i < multipart.getCount(); i++) {
                                        back.append(multipart.getBodyPart(i).getContent()).append("\n");
                                    }
                                }
                            }

                            return back.toString();

                        } catch (MessagingException | IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    public String toVariableNameString(Message arg0) {
                        return null;
                    }

                }));

        // Internet Address
        Classes.registerClass(new ClassInfo<>(InternetAddress.class, "internetaddress")
                .defaultExpression(new EventValueExpression<>(InternetAddress.class))
                .user("e?mail( |-|_)internet( |-|_)address")
                .name("Internet Address Type")
                .parser(new Parser<InternetAddress>() {

                    @Override
                    public String getVariableNamePattern() {
                        return ".+";
                    }

                    @Override
                    public InternetAddress parse(String arg0, ParseContext arg1) {
                        try {
                            InternetAddress address = new InternetAddress(arg0);
                            address.validate();
                            return address;
                        } catch (AddressException e) {
                            SkEmail.error("This email address is incorrect: " + arg0);
                        }
                        return null;
                    }

                    @Override
                    public String toString(InternetAddress arg0, int arg1) {
                        return arg0.getAddress();
                    }

                    @Override
                    public String toVariableNameString(InternetAddress arg0) {
                        return arg0.getAddress();
                    }

                }));


        // Session
        Classes.registerClass(new ClassInfo<>(Session.class, "session")
                .defaultExpression(new EventValueExpression<>(Session.class))
                .user("e?mail( |-|_)session")
                .name("Session Type")
                .parser(new Parser<Session>() {

                    @Override
                    public String getVariableNamePattern() {
                        return ".+";
                    }

                    @Override
                    public Session parse(String arg0, ParseContext arg1) {
                        return null;
                    }

                    @Override
                    public String toString(Session arg0, int arg1) {
                        try {
                            return arg0.getTransport("smtp").getURLName().getUsername();
                        } catch (NoSuchProviderException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    public String toVariableNameString(Session arg0) {
                        try {
                            return arg0.getTransport("smtp").getURLName().getUsername();
                        } catch (NoSuchProviderException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                }));

        // Address
        Classes.registerClass(new ClassInfo<>(Address.class, "address")
                .defaultExpression(new EventValueExpression<>(Address.class))
                .user("e?mail( |-|_)address")
                .name("Address Type")
                .parser(new Parser<Address>() {

                    @Override
                    public String getVariableNamePattern() {
                        return ".+";
                    }

                    @Override
                    public Address parse(String arg0, ParseContext arg1) {
                        return null;
                    }

                    @Override
                    public String toString(Address arg0, int arg1) {
                        return null;
                    }

                    @Override
                    public String toVariableNameString(Address arg0) {
                        return null;
                    }

                }));

        // Folder
        Classes.registerClass(new ClassInfo<>(Folder.class, "folder")
                .defaultExpression(new EventValueExpression<>(Folder.class))
                .user("(folder|dir|box)")
                .name("Folder Type")
                .parser(new Parser<Folder>() {

                    @Override
                    public String getVariableNamePattern() {
                        return ".+";
                    }

                    @Override
                    public Folder parse(String arg0, ParseContext arg1) {
                        try {
                            return EffConnection.lastSession.getStore("imap").getFolder(arg0);
                        } catch (MessagingException e) {
                            return null;
                        }
                    }

                    @Override
                    public String toString(Folder arg0, int arg1) {
                        return arg0.getName();
                    }

                    @Override
                    public String toVariableNameString(Folder arg0) {
                        return arg0.getName();
                    }
                }));

        // Recipient Type
        Classes.registerClass(new ClassInfo<>(Message.RecipientType.class, "recipienttype")
                .defaultExpression(new EventValueExpression<>(Message.RecipientType.class))
                .user("recipient( |-)?(type)?")
                .name("Recipient Type")
                .parser(new Parser<Message.RecipientType>() {

                    @Override
                    public String getVariableNamePattern() {
                        return ".+";
                    }

                    @Override
                    public Message.RecipientType parse(String arg0, ParseContext arg1) {
                        if (arg0.toLowerCase().equals("to")) return Message.RecipientType.TO;
                        if (arg0.toLowerCase().equals("cc")) return Message.RecipientType.CC;
                        if (arg0.toLowerCase().equals("bcc")) return Message.RecipientType.BCC;
                        return null;
                    }

                    @Override
                    public String toString(Message.RecipientType arg0, int arg1) {
                        return arg0.toString();
                    }

                    @Override
                    public String toVariableNameString(Message.RecipientType arg0) {
                        return arg0.toString();
                    }
                }));

    }
}
