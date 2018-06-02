package de.zortax.mcinjector.instrumentation.mcp;// Created by leo on 31.05.18

import de.zortax.mcinjector.instrumentation.AgentConfig;
import de.zortax.mcinjector.instrumentation.McAgent;

import java.io.File;

public class McpParser {

    private File fieldsFile;
    private File methodsFile;
    private File paramsFile;
    private File exceptorFile;
    private File joinedExcFile;
    private File joinedSrgFie;
    private File staticMethodsFile;

    public McpParser() {
        AgentConfig c = McAgent.getConfig();
        String currentMappings = c.baseDir + c.mappingsPath + c.release + "-" + c.version + "/";
        this.fieldsFile = new File(currentMappings + "mcp/fields.csv");
        this.methodsFile = new File(currentMappings + "mcp/methods.csv");
        this.paramsFile = new File(currentMappings + "mcp/params.csv");
        this.exceptorFile = new File(currentMappings + "srg/exceptor.json");
        this.joinedExcFile = new File(currentMappings + "srg/joined.exc");
        this.joinedSrgFie = new File(currentMappings + "srg/joined.srg");
        this.staticMethodsFile = new File(currentMappings + "srg/static_methods.txt");

        if (!doFilesExist()) {
            McAgent.warn("Couldn't find all needed files. Please delete \"" + currentMappings + "\" and restart the agent with a valid release and version!");
            throw new IllegalStateException("Couldn't find all mapping files.");
        }

    }

    public void parse() {
        if (!doFilesExist())
            throw new IllegalStateException("Couldn't find all mapping files.");



    }

    public boolean doFilesExist() {
        return fieldsFile.exists() && fieldsFile.isFile()
                && methodsFile.exists() && methodsFile.isFile()
                && paramsFile.exists() && paramsFile.isFile()
                && exceptorFile.exists() && exceptorFile.isFile()
                && joinedExcFile.exists() && joinedExcFile.isFile()
                && joinedSrgFie.exists() && joinedSrgFie.isFile()
                && staticMethodsFile.exists() && staticMethodsFile.isFile();
    }

}
