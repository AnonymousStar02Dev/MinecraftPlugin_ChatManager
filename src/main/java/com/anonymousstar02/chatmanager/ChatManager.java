package com.anonymousstar02.chatmanager;

import com.anonymousstar02.chatmanager.commands.*;
import com.anonymousstar02.chatmanager.events.PlayerChatEvent;
import com.anonymousstar02.chatmanager.events.PlayerCommandPreProcess;
import com.anonymousstar02.chatmanager.events.PlayerQuitEvent;
import com.anonymousstar02.chatmanager.enums.Config;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class ChatManager extends JavaPlugin{

    private Permission permission;
    private HashMap<UUID,Long> cooldown;
    private HashMap<UUID,String> repeat;
    private YamlFile config;
    private YamlFile message;
    private List<String> blocked_words;
    private List<String> whitelist_commands;
    private List<String> allowed_urls;

    public List<String> getAllowedUrls(){
        return allowed_urls;
    }

    public List<String> getBlockedWords(){
        return blocked_words;
    }

    public HashMap<UUID,Long> getCooldownMap() {
        return cooldown;
    }

    public YamlFile getMainConfig(){
        return config;
    }

    public YamlFile getMessagesConfig(){
        return message;
    }

    public Permission getPermissionService(){ return permission; }

    public HashMap<UUID,String> getRepeatMap(){
        return repeat;
    }

    public List<String> getWhitelistCommands(){
        return whitelist_commands;
    }

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

    @Override
    public void onEnable() {
        registerVaultsAPI();
        cooldown = new HashMap<>();
        repeat = new HashMap<>();
        registerConfigs();
        registerEvents();
        registerCommands();
    }

    @SuppressWarnings("")
    private void registerCommands() {
        PluginCommand cmd;

        cmd = this.getCommand("cm-reload");
        if(cmd != null) cmd.setExecutor(new Reload(this));

        cmd = this.getCommand("clearchat");
        if(cmd != null) cmd.setExecutor(new ClearChat(this));

        cmd = this.getCommand("mutechat");
        if(cmd != null) cmd.setExecutor(new MuteChat(this));

        cmd = this.getCommand("unmutechat");
        if(cmd != null) cmd.setExecutor(new UnMuteChat(this));

        cmd = this.getCommand("opothers");
        if(cmd != null) cmd.setExecutor(new OpOther(this));
    }

    public void registerConfigs() {
        try {
            YamlFile tmp;
            config = loadConfig("config.yml",this);
            message = loadMessages("message_{locale}.yml",config.getString(Config.LOCALE.toString()),this);

            tmp = loadConfig("blocked_words.yml", this);
            blocked_words = tmp.getStringList("words");

            tmp = loadConfig("whitelist_commands.yml", this);
            whitelist_commands = tmp.getStringList("commands");

            tmp = loadConfig("allowed_urls.yml", this);
            allowed_urls = tmp.getStringList("urls");

            File permissions_file = new File(this.getDataFolder(),"permissions.yml");
            if(!permissions_file.exists()) saveResource("permissions.yml",false);

        }catch(Exception e) {
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
        }
    }

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

