package com.alexlew.skemail.types;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;

public class EmailConnection {

    static {
        Classes.registerClass(new ClassInfo<EmailConnection>(EmailConnection.class, "emailconnection")
                .user("email ?connect(ion)?")
                .name("Email Connection Type")
                .description("Store username and password.")
                .examples("login to gmail service from mail \"myemail@gmail.com\" and password \"my password\"")
                .parser(new Parser<EmailConnection>() {

                    @Override
                    public String getVariableNamePattern() {
                        return ".+";
                    }

                    @Override
                    public EmailConnection parse(String arg0, ParseContext arg1) {
                        return null;
                    }

                    @Override
                    public String toString(EmailConnection arg0, int arg1) {
                        return null;
                    }

                    @Override
                    public String toVariableNameString(EmailConnection arg0) {
                        return null;
                    }

                }));
    }

    private String username;
    private String password;
    private EmailService service;

    public EmailConnection() { }

    public String getUsername() {
        return username;
    }

    public void setUsername( String username ) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword( String password ) {
        this.password = password;
    }

    public EmailService getService() {
        return service;
    }

    public void setService( EmailService service ) {
        this.service = service;
    }
}
