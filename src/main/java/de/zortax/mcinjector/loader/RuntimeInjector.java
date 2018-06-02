package de.zortax.mcinjector.loader;// Created by leo on 29.05.18

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import de.zortax.mcinjector.util.Util;
import de.zortax.pra.network.config.Config;

import java.io.File;
import java.io.IOException;

public class RuntimeInjector {

    public static void main(String[] args) {
        LoaderConfig config = Config.load("loader-config.json", LoaderConfig.class);
        config.loadFlags(args);
        config.save();
        File agent = new File(config.agentPath);
        if (agent.exists()) {
            System.out.println("Trying to attach to Virtual Machine \"" + config.vmName + "\"...");
            try {
                Util.attachToVm(config.vmName, agent.getAbsolutePath(), config.agentArgs);
            } catch (IOException | AttachNotSupportedException | AgentLoadException | AgentInitializationException e) {
                System.out.println("Couldn't attach to Virtual Machine...");
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                System.out.println("Couldn't find a running VM named " + config.vmName + "... Exiting.");
            }
        } else
            System.out.println("Couldn't find agent JAR (" + config.agentPath + ")... Exiting.");
    }

}
