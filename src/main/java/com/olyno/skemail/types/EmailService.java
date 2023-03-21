package com.olyno.skemail.types;

import java.util.HashMap;

import com.olyno.skemail.SkEmail;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;

public class EmailService {

    public static HashMap<String, EmailService> services = new HashMap<>();
    public static EmailService lastEmailService;

    static {
        Classes.registerClass(new ClassInfo<>(EmailService.class, "emailservice")
                .defaultExpression(new EventValueExpression<>(EmailService.class))
                .user("email ?service")
                .name("Email Service")
                .description("A email service like Gmail. Store smtp and imap, port and address.")
                .parser(new Parser<EmailService>() {

                    @Override
                    public EmailService parse(String arg0, ParseContext arg1) {
                        return null;
                    }

                    @Override
                    public String toString(EmailService service, int arg1) {
                        return service.getName();
                    }

                    @Override
                    public String toVariableNameString(EmailService service) {
                        return service.getName();
                    }

                }));
    }

    private String name = "default";
    private String smtp_address = "smtp.gmail.com";
    private String smtp_port = "465";
    private String imap_address = "imap.gmail.com";
    private String imap_port = "993";

    public EmailService() {
    }

    public EmailService(String name) {
        this.name = name;
    }


    public String getSmtp_address() {
        return smtp_address;
    }

    public void setSmtp_address(String smtp_address) {
        this.smtp_address = smtp_address;
    }

    public String getSmtp_port() {
        return smtp_port;
    }

    public void setSmtp_port(Object smtp_port) {
        if (smtp_port instanceof Integer) this.smtp_port = smtp_port.toString();
        else if (smtp_port instanceof String) this.smtp_port = (String) smtp_port;
        else SkEmail.error("This SMTP Port is not an integer or string, but a " + smtp_port.getClass().getName());
    }

    public String getImap_address() {
        return imap_address;
    }

    public void setImap_address(String imap_address) {
        this.imap_address = imap_address;
    }

    public String getImap_port() {
        return imap_port;
    }

    public void setImap_port(Object imap_port) {
        if (imap_port instanceof Integer) this.imap_port = imap_port.toString();
        else if (imap_port instanceof String) this.imap_port = (String) imap_port;
        else SkEmail.error("This IMAP Port is not an integer or string, but a " + smtp_port.getClass().getName());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
