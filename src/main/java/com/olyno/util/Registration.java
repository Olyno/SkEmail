package com.olyno.util;

public class Registration {

    private Class<?> clazz;
    private String[] syntaxes;

    public Registration(Class<?> cls, String... syntaxes) {
        clazz = cls;
        this.syntaxes = syntaxes;
    }

    public Registration(String... syntaxes) {
        this.syntaxes = syntaxes;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public String[] getSyntaxes() {
        return this.syntaxes;
    }

    public String getSyntax() {
        return this.syntaxes[0];
    }
}
