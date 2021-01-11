package com.anonymousstar02.chatmanager.commands;

import java.io.IOException;

import com.anonymousstar02.chatmanager.ChatManager;
import com.anonymousstar02.chatmanager.utils.Permission;
import com.anonymousstar02.chatmanager.utils.TranslateColor;
import com.anonymousstar02.chatmanager.utils.Variables;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MuteChat implements CommandExecutor, TranslateColor, Permission {
	
	private ChatManager plugin;
	public MuteChat(ChatManager plugin) {
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender,Command cmd,String label,String[] args) {
		
		if(cmd.getName().equals("mutechat")) {
			
			if(!hasPermissions(sender,plugin,"chatmanager.*","chatmanager.cmd.*","chatmanager.cmd.mutechat")) {
				sender.sendMessage(color(plugin.message.getString(Variables.Message.PERMISSION_DENIED.toString())));
				return false;
			}
			
			if(args.length == 0) {
				try {
					plugin.config.set(Variables.Config.MUTE_CHAT.toString(), true);
					plugin.config.save(plugin.config_file);
				}catch(IOException e) {
					e.printStackTrace();
				}
				sender.sendMessage(color(plugin.message.getString(Variables.Message.HAVE_MUTED_CHAT.toString())));
				plugin.getServer().broadcastMessage(color(plugin.message.getString(Variables.Message.GLOBAL_CHAT_MUTED.toString()).replace("%player%", sender.getName())));
				return true;
			}
		}
		
		return false;
	}

}
