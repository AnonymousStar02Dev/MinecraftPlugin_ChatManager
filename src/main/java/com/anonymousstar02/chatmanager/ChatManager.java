package com.anonymousstar02.chatmanager;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

import com.anonymousstar02.chatmanager.commands.*;
import com.anonymousstar02.chatmanager.events.NotPermissionCommand;
import com.anonymousstar02.chatmanager.events.PlayerChatEvent;
import com.anonymousstar02.chatmanager.events.PlayerQuitEvent;
import com.anonymousstar02.chatmanager.utils.Variables;
import org.bukkit.plugin.java.JavaPlugin;
import org.simpleyaml.configuration.file.YamlFile;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;

public class ChatManager extends JavaPlugin{

    //Yaml file
    public YamlFile config;
    public YamlFile message;
    public YamlFile blacklist_words;
    public YamlFile whitelist_commands;

    //Yaml java file
    public File config_file;
    public File message_file;
    public File permissions_file;
    public File blacklist_words_file;
    public File whitelist_commands_file;

    //File name
    public String config_file_name = "config.yml";
    public String message_file_name = "message_{locale}.yml";
    public String permissions_file_name = "permissions.yml";
    public String blacklist_words_file_name = "blacklist_words.yml";
    public String whitelist_commands_file_name = "whitelist_commands.yml";

    //Vault permission
    public Permission permission;
    public Chat chat;

    //Event variables
    public HashMap<UUID,Long> cooldown;
    public HashMap<UUID,String> repeat;

    @Override
    public void onLoad() {}

    @Override
    public void onEnable() {
        permission = getServer().getServicesManager().getRegistration(Permission.class).getProvider();
        cooldown = new HashMap<>();
        repeat = new HashMap<>();
        registerConfigs();
        registerEvents();
        registerCommands();
    }

    //metodo che registra i comandi del plugin
    private void registerCommands() {
        this.getCommand("cm-reload").setExecutor(new Reload(this));
        this.getCommand("clearchat").setExecutor(new ClearChat(this));
        this.getCommand("mutechat").setExecutor(new Mute(this));
        this.getCommand("unmutechat").setExecutor(new Unmute(this));
        this.getCommand("opothers").setExecutor(new OpOther(this));
    }

    //metodo che registra i config del plugin
    public void registerConfigs() {
        try {
            //load config.yml file
            config_file = new File(this.getDataFolder(),config_file_name);
            if(!config_file.exists()) saveResource(config_file_name,false);
            config = new YamlFile(config_file);
            config.loadWithComments();

            //load message.yml file
            message_file = new File(this.getDataFolder(),message_file_name.replace("{locale}", config.getString(Variables.Config.LOCALE.toString())));
            if(!message_file.exists()) {
                switch(config.getString(Variables.Config.LOCALE.toString())) {
                    case "it":
                    case "en":
                        saveResource(message_file_name.replace("{locale}", config.getString(Variables.Config.LOCALE.toString())),false);
                        break;
                    default:
                        saveResource("message_en.yml",false);
                }
            }
            message = new YamlFile(message_file);
            message.loadWithComments();

            permissions_file = new File(this.getDataFolder(),permissions_file_name);
            if(!permissions_file.exists()) saveResource(permissions_file_name,false);

            blacklist_words_file = new File(this.getDataFolder(),blacklist_words_file_name);
            if(!blacklist_words_file.exists()) saveResource(blacklist_words_file_name,false);
            blacklist_words = new YamlFile(blacklist_words_file);
            blacklist_words.loadWithComments();

            whitelist_commands_file = new File(this.getDataFolder(),whitelist_commands_file_name);
            if(!whitelist_commands_file.exists()) saveResource(whitelist_commands_file_name,false);
            whitelist_commands = new YamlFile(whitelist_commands_file);
            whitelist_commands.loadWithComments();

        }catch(Exception e) {
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    //metodo che registra gli eventi del plugin
    public void registerEvents() {
        this.getServer().getPluginManager().registerEvents(new PlayerChatEvent(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerQuitEvent(this), this);
        this.getServer().getPluginManager().registerEvents(new NotPermissionCommand(this), this);
    }

}

