package com.alexlew.skemail.types;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;

public class EmailService {

    static {
        Classes.registerClass(new ClassInfo<EmailService>(EmailService.class, "emailservice")
                .user("email ?service")
                .name("Email Service Type")
                .description("A email service like Gmail. Store smtp and imap, port and address.")
                .parser(new Parser<EmailService>() {

                    @Override
                    public String getVariableNamePattern() {
                        return ".+";
                    }

                    @Override
                    public EmailService parse( String arg0, ParseContext arg1) {
                        return null;
                    }

                    @Override
                    public String toString( EmailService arg0, int arg1) {
                        return null;
                    }

                    @Override
                    public String toVariableNameString( EmailService arg0) {
                        return null;
                    }

                }));
    }

    private String name = "default";
    private String smtp_address = "smtp.gmail.com";
    private String smtp_port = "465";
    private String imap_address = "imap.gmail.com";
    private String imap_port = "993";

    public static EmailService lastEmailService;

    public EmailService() { }


    public String getSmtp_address() {
        return smtp_address;
    }

    public void setSmtp_address( String smtp_address ) {
        this.smtp_address = smtp_address;
    }

    public String getSmtp_port() {
        return smtp_port;
    }

    public void setSmtp_port( Object smtp_port ) {
        String port = (String) smtp_port;
        this.smtp_port = port;
    }

    public String getImap_address() {
        return imap_address;
    }

    public void setImap_address( String imap_address ) {
        this.imap_address = imap_address;
    }

    public String getImap_port() {
        return imap_port;
    }

    public void setImap_port( Object imap_port ) {
        String port = (String) imap_port;
        this.imap_port = port;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }
}
