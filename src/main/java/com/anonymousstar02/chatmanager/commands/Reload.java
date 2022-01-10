package com.anonymousstar02.chatmanager.commands;

import com.anonymousstar02.chatmanager.ChatManager;
import com.anonymousstar02.chatmanager.Permission;
import com.anonymousstar02.chatmanager.TranslateColor;
import com.anonymousstar02.chatmanager.enums.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class Reload implements CommandExecutor, TranslateColor, Permission {

	private final ChatManager plugin;
	public Reload(ChatManager plugin) {
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender,Command cmd,String label,String[] args) {
		
		if(cmd.getName().equals("cm-reload")) {
			if(!hasPermissions(sender,plugin.getPermissionService(),"chatmanager.*","chatmanager.cmd.*","chatmanager.cmd.cmreload")) {
				sender.sendMessage(color(plugin.getMessagesConfig().getString(Message.PERMISSION_DENIED.toString())));
				return false;
			}
			
			if(args.length == 0) {
				plugin.registerConfigs();
				sender.sendMessage(color(plugin.getMessagesConfig().getString(Message.RELOAD.toString())));
				return true;
			}
		}
		
		return false;
	}
	
}
