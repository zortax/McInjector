package de.zortax.mcinjector.instrumentation;// Created by leo on 29.05.18

import de.zortax.mcinjector.util.FlagConfig;

public class AgentConfig extends FlagConfig {

    public boolean log = true;
    public boolean verbose = false;
    public String mappingsPath = "mappings/";
    public String version = "1.8.9";
    public String release = "22";
    public String baseDir = "./";

}
