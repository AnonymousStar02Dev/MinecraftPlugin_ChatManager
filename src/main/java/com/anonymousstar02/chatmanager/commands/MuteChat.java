package com.anonymousstar02.chatmanager.commands;

import java.io.IOException;

import com.anonymousstar02.chatmanager.ChatManager;
import com.anonymousstar02.chatmanager.Permission;
import com.anonymousstar02.chatmanager.TranslateColor;
import com.anonymousstar02.chatmanager.enums.Config;
import com.anonymousstar02.chatmanager.enums.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MuteChat implements CommandExecutor, TranslateColor, Permission {
	
	private final ChatManager plugin;
	public MuteChat(ChatManager plugin) {
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender,Command cmd,String label,String[] args) {
		
		if(cmd.getName().equals("mutechat")) {
			
			if(!hasPermissions(sender,plugin.getPermissionService(),"chatmanager.*","chatmanager.cmd.*","chatmanager.cmd.mutechat")) {
				sender.sendMessage(color(plugin.getMessagesConfig().getString(Message.PERMISSION_DENIED.toString())));
				return false;
			}
			
			if(args.length == 0) {
				try {
					plugin.getMainConfig().set(Config.MUTE_CHAT.toString(), true);
					plugin.getMainConfig().save(plugin.getMainConfig().getConfigurationFile());
				}catch(IOException e) {
					e.printStackTrace();
				}
				sender.sendMessage(color(plugin.getMessagesConfig().getString(Message.HAVE_MUTED_CHAT.toString())));
				plugin.getServer().broadcastMessage(color(plugin.getMessagesConfig().getString(Message.GLOBAL_CHAT_MUTED.toString()).replace("%player%", sender.getName())));
				return true;
			}
		}
		
		return false;
	}

}
