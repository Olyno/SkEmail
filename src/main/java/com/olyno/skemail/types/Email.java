package com.olyno.skemail.types;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;

import static com.olyno.skemail.Utils.getTextFromMessage;

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
                    public String toString(Message email, int arg1) {
                        try {
                            String result = getTextFromMessage(email);
                            return result.isEmpty() ? null : result;

                        } catch (MessagingException | IOException ignore) {
                            return null;
                        }
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
                    public InternetAddress parse(String internetAddress, ParseContext arg1) {
                        return null;
                    }

                    @Override
                    public String toString(InternetAddress internetAddress, int arg1) {
                        return internetAddress.getAddress();
                    }

                    @Override
                    public String toVariableNameString(InternetAddress internetAddress) {
                        return internetAddress.getAddress();
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
                    public String toString(Session session, int arg1) {
                        try {
                            return session.getTransport("smtp").getURLName().getUsername();
                        } catch (NoSuchProviderException ignore) {
                        }
                        return null;
                    }

                    @Override
                    public String toVariableNameString(Session session) {
                        try {
                            return session.getTransport("smtp").getURLName().getUsername();
                        } catch (NoSuchProviderException ignore) {
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
                    public String toString(Address address, int arg1) {
                        return address.toString();
                    }

                    @Override
                    public String toVariableNameString(Address address) {
                        return address.toString();
                    }

                }));

        // Folder
        Classes.registerClass(new ClassInfo<>(Folder.class, "folder")
                .defaultExpression(new EventValueExpression<>(Folder.class))
                .user("folder|dir|box")
                .name("Folder Type")
                .parser(new Parser<Folder>() {

                    @Override
                    public String getVariableNamePattern() {
                        return ".+";
                    }

                    @Override
                    public Folder parse(String folder, ParseContext arg1) {
                        return null;
                    }

                    @Override
                    public String toString(Folder folder, int arg1) {
                        return folder.getName();
                    }

                    @Override
                    public String toVariableNameString(Folder folder) {
                        return folder.getName();
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
                    public Message.RecipientType parse(String type, ParseContext arg1) {
                        return null;
                    }

                    @Override
                    public String toString(Message.RecipientType recipientType, int arg1) {
                        return recipientType.toString();
                    }

                    @Override
                    public String toVariableNameString(Message.RecipientType recipientType) {
                        return recipientType.toString();
                    }
                }));

    }
}
