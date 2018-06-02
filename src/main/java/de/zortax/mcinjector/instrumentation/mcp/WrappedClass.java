package de.zortax.mcinjector.instrumentation.mcp;// Created by leo on 01.06.18

import de.zortax.mcinjector.util.Util;

import java.util.ArrayList;

public class WrappedClass {

    private String obfuscatedName;
    private String mcpName;
    private String mcpSimpleName;
    private ClassType type = null;

    private ArrayList<WrappedClass> innerClasses;

    private Class classObject = null;

    private boolean innerClass;
    private WrappedClass innerClassOwner;

    public WrappedClass(String obfuscatedName, String mcpName) {
        this(obfuscatedName, mcpName, false);
    }

    public WrappedClass(String obfuscatedName, String mcpName, boolean innerClass) {
        this.obfuscatedName = obfuscatedName;
        this.mcpName = mcpName.replaceAll("/", ".");
        this.mcpSimpleName = Util.lastPart(mcpName, "/");
        this.innerClass = innerClass;
        this.innerClasses = new ArrayList<>();
        this.innerClassOwner = null;
    }

    public void setClassObject(Class clazz) {
        this.classObject = clazz;

        if (clazz.isInterface())
            type = ClassType.INTERFACE;
        else if (clazz.isEnum())
            type = ClassType.ENUM;
        else if (clazz.isAnnotation())
            type = ClassType.ANNOTATION;
        else
            type = ClassType.CLASS;

    }

    public String getObfuscatedName() {
        return obfuscatedName;
    }

    public String getMcpName() {
        return mcpName;
    }

    public String getMcpSimpleName() {
        return mcpSimpleName;
    }

    public ClassType getType() {
        if (type == null)
            throw new IllegalStateException("Class type not loaded yet.");
        return type;
    }

    public Class getClassObject() {
        if (classObject == null)
            throw new IllegalStateException("Class object not loaded yet.");
        return classObject;
    }

    public boolean isInnerClass() {
        return innerClass;
    }

    public WrappedClass getInnerClassOwner() {
        if (!innerClass)
            throw new IllegalStateException("This is not an inner class.");
        if (innerClassOwner == null)
            throw new IllegalStateException("Owner of this inner class not loaded.");
        return innerClassOwner;
    }

    public void setInnerClass(boolean innerClass, WrappedClass owner) {
        if (owner != null)
            this.innerClassOwner = owner;
        this.innerClass = innerClass;
    }

    public void setInnerClass(boolean innerClass) {
        setInnerClass(innerClass, null);
    }

    public ArrayList<WrappedClass> getInnerClasses() {
        return innerClasses;
    }

}
