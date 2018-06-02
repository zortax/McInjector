package de.zortax.mcinjector.instrumentation.mcp;// Created by leo on 01.06.18

public enum ClassType {

    CLASS("class"),
    ENUM("enum"),
    INTERFACE("interface"),
    ANNOTATION("@interface");

    private String text;

    ClassType(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
