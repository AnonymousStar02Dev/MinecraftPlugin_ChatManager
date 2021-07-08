package com.anonymousstar02.chatmanager.configuration;

import org.bukkit.plugin.java.JavaPlugin;
import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.File;
import java.io.IOException;

public final class ConfigLoader {

    public static YamlFile loadConfig(String name, JavaPlugin plugin) throws IOException, InvalidConfigurationException {
        File file = new File(plugin.getDataFolder(),name);
        if(!file.exists()) plugin.saveResource(name,false);
        YamlFile yfile = new YamlFile(file);
        yfile.loadWithComments();
        return yfile;
    }

    public static YamlFile loadMessages(String name,String language,JavaPlugin plugin) throws IOException, InvalidConfigurationException {
        String file_name = name.replace("{locale}", language);
        File file = new File(plugin.getDataFolder(),file_name);
        if(!file.exists()) {
            switch(language) {
                case "it":
                case "en":
                    plugin.saveResource(file_name,false);
                    break;
                default:
                    plugin.saveResource("message_en.yml",false);
            }
        }
        YamlFile yfile = new YamlFile(file);
        yfile.loadWithComments();
        return yfile;
    }

}
