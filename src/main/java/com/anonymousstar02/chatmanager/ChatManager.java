package com.anonymousstar02.chatmanager;

import com.anonymousstar02.chatmanager.commands.*;
import com.anonymousstar02.chatmanager.configuration.ConfigLoader;
import com.anonymousstar02.chatmanager.configuration.Configs;
import com.anonymousstar02.chatmanager.events.PlayerChatEvent;
import com.anonymousstar02.chatmanager.events.PlayerCommandPreProcess;
import com.anonymousstar02.chatmanager.events.PlayerQuitEvent;
import com.anonymousstar02.chatmanager.utils.enums.Config;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.simpleyaml.configuration.file.YamlFile;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

public final class ChatManager extends JavaPlugin{

    //Yaml file
    private YamlFile config;
    private YamlFile message;
    private YamlFile blacklist_words;
    private YamlFile whitelist_commands;
    private YamlFile allowed_urls;

    //Vault permission
    private Permission permission;

    //Event variables
    private HashMap<UUID,Long> cooldown;
    private HashMap<UUID,String> repeat;

    public YamlFile getMainConfig(){
        return config;
    }

    public YamlFile getMessagesConfig(){
        return message;
    }

    public YamlFile getBlacklistWordsConfig(){
        return blacklist_words;
    }

    public YamlFile getWhitelistCommandsConfig(){
        return whitelist_commands;
    }

    public YamlFile getAllowedUrlsConfig(){
        return allowed_urls;
    }

    public Permission getPermissionService(){
        return permission;
    }

    public HashMap<UUID,Long> getCooldownMap() {
        return cooldown;
    }

    public HashMap<UUID,String> getRepeatMap(){
        return repeat;
    }

    @Override
    public void onEnable() {
        registerVaultsAPI();
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
        this.getCommand("mutechat").setExecutor(new MuteChat(this));
        this.getCommand("unmutechat").setExecutor(new UnMuteChat(this));
        this.getCommand("opothers").setExecutor(new OpOther(this));
    }

    //metodo che registra i config del plugin
    public void registerConfigs() {
        try {
            config = ConfigLoader.loadConfig(Configs.CONFIG.toString(),this);
            message = ConfigLoader.loadMessages(Configs.MESSAGES.toString(),config.getString(Config.LOCALE.toString()),this);
            blacklist_words = ConfigLoader.loadConfig(Configs.BLOCKED_WORDS.toString(), this);
            whitelist_commands = ConfigLoader.loadConfig(Configs.WHITELIST_COMMANDS.toString(), this);
            allowed_urls = ConfigLoader.loadConfig(Configs.ALLOWED_URLS.toString(), this);

            File permissions_file = new File(this.getDataFolder(),Configs.PERMISSIONS.toString());
            if(!permissions_file.exists()) saveResource(Configs.PERMISSIONS.toString(),false);

        }catch(Exception e) {
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    //metodo che registra gli eventi del plugin
    private void registerEvents() {
        this.getServer().getPluginManager().registerEvents(new PlayerChatEvent(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerQuitEvent(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerCommandPreProcess(this), this);
    }

    private void registerVaultsAPI(){
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        if(rsp == null) this.getServer().getPluginManager().disablePlugin(this);
        else permission = rsp.getProvider();
    }

}

