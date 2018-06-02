package de.zortax.mcinjector.instrumentation.mcp;// Created by leo on 01.06.18

public class WrappedFunction {

    private WrappedClass owner;
    private String obfuscatedName;
    private String srgName;
    private String obfuscatedDescriptor;
    private String srgDescriptor;
    private String mcpName ;
    private boolean isStatic;
    private boolean isConstructor;

    public WrappedFunction(WrappedClass owner, String obfuscatedName, String srgName, String obfuscatedDescriptor, String srgDescriptor, boolean isStatic) {
        this.owner = owner;
        this.obfuscatedName = obfuscatedName;
        this.srgName = srgName;
        this.obfuscatedDescriptor = obfuscatedDescriptor;
        this.srgDescriptor = srgDescriptor;
        this.isStatic = isStatic;
        this.mcpName = null;
    }

    public WrappedClass getOwner() {
        return owner;
    }

    public String getObfuscatedName() {
        return obfuscatedName;
    }

    public String getSrgName() {
        return srgName;
    }

    public String getObfuscatedDescriptor() {
        return obfuscatedDescriptor;
    }

    public String getSrgDescriptor() {
        return srgDescriptor;
    }

    public String getMcpName() {
        if (mcpName == null)
            throw new IllegalStateException("MCP Name of this function not loaded yet.");
        return mcpName;
    }

    public void setMcpName(String mcpName) {
        this.mcpName = mcpName;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean isStatic) {
        this.isStatic = isStatic;
    }

    public boolean isConstructor() {
        return isConstructor;
    }

    public void setConstructor(boolean constructor) {
        isConstructor = constructor;
    }
}
