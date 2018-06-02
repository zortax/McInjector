package de.zortax.mcinjector.instrumentation;// Created by leo on 29.05.18

import de.zortax.mcinjector.instrumentation.mcp.McpDownloadManager;
import de.zortax.pra.network.config.Config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.Instrumentation;
import java.util.Arrays;
import java.util.Iterator;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class McAgent {

    private static Instrumentation instrumentation = null;
    private static AgentConfig config = null;
    private static Logger logger = null;

    public static void agentmain(String args, Instrumentation instrumentation) throws IOException {
        McAgent.instrumentation = instrumentation;
        String baseDir = "./";
        if (args.contains("--baseDir")) {
            for (Iterator<String> it = Arrays.asList(args.split(";")).iterator(); it.hasNext(); ) {
                if (it.next().equals("--baseDir") && it.hasNext()) {
                    baseDir = it.next();
                    break;
                }
            }
        }
        McAgent.config = Config.load(baseDir + "agent-config.json", AgentConfig.class);
        McAgent.config.loadFlags(args);
        try {
            File loggingConfig = new File(baseDir + "logging.properties");
            if (loggingConfig.exists() && loggingConfig.isFile()) {
                System.out.println("Found logging.properties...");
                LogManager.getLogManager().readConfiguration(new FileInputStream(loggingConfig));
            }
        } catch (Exception ignored) {}
        McAgent.logger = Logger.getLogger("McAgent");
        McAgent.info("Loading java agent...");
        McAgent.config.save();
        McpDownloadManager.downloadMappings(McAgent.config.version, McAgent.config.release);
    }

    public static Instrumentation getInstrumentation() {
        return instrumentation;
    }

    public static AgentConfig getConfig() {
        return config;
    }

    public static Logger getLogger() {
        return logger;
    }

    public static void debug(String msg) {
        if (config.verbose)
            logger.info(msg);
    }

    public static void info(String msg) {
        if (config.log)
            logger.info(msg);
    }

    public static void warn(String msg) {
        if (config.log)
            logger.warning(msg);
    }

}
