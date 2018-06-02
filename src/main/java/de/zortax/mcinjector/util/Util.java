package de.zortax.mcinjector.util;// Created by leo on 31.05.18

import com.sun.tools.attach.*;

import java.io.IOException;
import java.util.Optional;

public class Util {

    public static void attachToVm(final String name, final String agentJar, final String agentArgs) throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException, IllegalArgumentException {
       Optional<VirtualMachineDescriptor> vmDesc = VirtualMachine.list().stream().filter(vm -> vm.displayName().startsWith(name)).findFirst();
       if (vmDesc.isPresent())
           VirtualMachine.attach(vmDesc.get()).loadAgent(agentJar, agentArgs);
       else
           throw new IllegalArgumentException("VM not found");
    }

    public static String lastPart(String str, String splitSeq) {
        return str.substring(str.lastIndexOf(splitSeq) + 1, str.length());
    }

    public static String firstPart(String str, String splitSeq) {
        return str.substring(0, str.lastIndexOf(splitSeq));
    }

}
