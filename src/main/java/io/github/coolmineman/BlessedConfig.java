package io.github.coolmineman;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;

public class BlessedConfig {
    private boolean isNewConfig;
    private File file;
    private FileWriter filewriter = null;
    private HashMap<String, String> configvalues = new HashMap<>();

    /**
     * Creates a new config
     */
    public BlessedConfig(Identifier configid) {
        file = new File(FabricLoader.getInstance().getConfigDirectory() + File.separator + configid.getNamespace() + File.separator + configid.getPath() + ".tsv");
        if (!file.exists()) {
            (new File(FabricLoader.getInstance().getConfigDirectory() + File.separator + configid.getNamespace())).mkdirs();
            isNewConfig = true;
            try {
                filewriter = new FileWriter(file);
                filewriter.write("config option\tValue\tOptional Comment\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            isNewConfig = false;
        }
    }

    public BlessedConfig addOption(String optionName, String defaultValue) {
        return this.addOption(optionName, defaultValue, null);
    }

    /**
     * Adds a config option
     * @param optionName Name of config option
     * @param defaultValue 
     * @return JQuerry style method chaining
     */
    public BlessedConfig addOption(String optionName, String defaultValue, String comment) {
        if (isNewConfig && filewriter != null) {
            configvalues.put(optionName, defaultValue);
            try {
                if (comment != null)
                    filewriter.write(optionName + "\t" + defaultValue + "\t" + comment + "\n");
                else
                    filewriter.write(optionName + "\t" + defaultValue + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    /**
     * Use after creating all your options
     * @return
     */
    public BlessedConfig done() {
        if (isNewConfig) {
            try {
                filewriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                //TSV header
                reader.readLine();
                String thisLine = null;
                while ((thisLine = reader.readLine()) != null) {
                    String[] parts = thisLine.split("\t");
                    configvalues.put(parts[0], parts[1]);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    /**
     * Gets value from config file
     * @param option Name of option
     * @return value
     */
    public String getOption(String option) {
        return configvalues.get(option);
    }
}