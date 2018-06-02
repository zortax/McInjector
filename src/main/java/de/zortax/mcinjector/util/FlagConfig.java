package de.zortax.mcinjector.util;// Created by leo on 29.05.18

import com.google.gson.Gson;
import de.zortax.pra.network.config.Config;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;


public abstract class FlagConfig extends Config {

    transient private Gson gson = new Gson();

    public final void loadFlags(String[] args) {
        loadFlags(Arrays.asList(args));
    }

    public final void loadFlags(String string) {
        loadFlags(string.split(";"));
    }

    public final void loadFlags(List<String> args) {

        String currentFlag = null;
        Field currentSetting = null;
        for (String c : args) {
            if (c.startsWith("--")) {
                currentFlag = c.replaceFirst("--", "");
                try {
                    currentSetting = getClass().getDeclaredField(currentFlag);
                    if (currentSetting != null) {
                        if (currentSetting.getType() == boolean.class || currentSetting.getType() == Boolean.class) {
                            currentSetting.set(this, true);
                        }
                    }
                } catch (Exception ignored) {
                }
            } else {
                try {
                    if (currentFlag != null && currentSetting != null) {
                        if (currentSetting.getType() == String.class) {
                            currentSetting.set(this, c);
                        } else {
                            currentSetting.set(this, gson.fromJson(c, currentSetting.getType()));
                        }
                    }
                } catch (Exception ignored) {
                }
            }


        }

    }

}
