package com.anonymousstar02.chatmanager.commands;

import com.anonymousstar02.chatmanager.ChatManager;
import com.anonymousstar02.chatmanager.utils.Permission;
import com.anonymousstar02.chatmanager.utils.TranslateColor;
import com.anonymousstar02.chatmanager.utils.Variables;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class Reload implements CommandExecutor, TranslateColor, Permission {

	private ChatManager plugin;
	public Reload(ChatManager plugin) {
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender,Command cmd,String label,String[] args) {
		
		if(cmd.getName().equals("cm-reload")) {
			if(!hasPermissions(sender,plugin,"chatmanager.*","chatmanager.cmd.*","chatmanager.cmd.cmreload")) {
				sender.sendMessage(color(plugin.message.getString(Variables.Message.PERMISSION_DENIED.toString())));
				return false;
			}
			
			if(args.length == 0) {
				plugin.registerConfigs();
				sender.sendMessage(color(plugin.message.getString(Variables.Message.RELOAD.toString())));
				return true;
			}
		}
		
		return false;
	}
	
}
