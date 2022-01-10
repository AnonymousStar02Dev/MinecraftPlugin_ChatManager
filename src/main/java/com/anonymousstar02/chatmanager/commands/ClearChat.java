package com.anonymousstar02.chatmanager.commands;

import com.anonymousstar02.chatmanager.ChatManager;
import com.anonymousstar02.chatmanager.Permission;
import com.anonymousstar02.chatmanager.TranslateColor;
import com.anonymousstar02.chatmanager.Utils;
import com.anonymousstar02.chatmanager.enums.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearChat implements CommandExecutor, TranslateColor, Permission {

	private final ChatManager plugin;
	public ClearChat(ChatManager plugin) {
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equals("clearchat")) {

			if(!hasPermissions(sender,plugin.getPermissionService(),"chatmanager.*","chatmanager.cmd.*","chatmanager.cmd.clearchat")) {
				sender.sendMessage(color(plugin.getMessagesConfig().getString(Message.PERMISSION_DENIED.toString())));
				return false;
			}

			if(args.length != 1) {
				sender.sendMessage(color(plugin.getMessagesConfig().getString(Message.CLEARCHAT_CORRECT_USAGE.toString())));
				return true;
			}

			if(args[0].equalsIgnoreCase("server")){
				if(hasPermissions(sender,plugin.getPermissionService(),"chatmanager.*","chatmanager.cmd.*","chatmanager.cmd.clearchat.*","chatmanager.cmd.clearchat.server")) {
					for(int x = 0;x < 100;x++) plugin.getServer().broadcastMessage("");
					sender.sendMessage(color(plugin.getMessagesConfig().getString(Message.CLEARED_SERVER_CHAT.toString()).replace("%player%", sender.getName())));
				}else {
					sender.sendMessage(color(plugin.getMessagesConfig().getString(Message.NO_CLEAR_SERVER_CHAT.toString())));
				}
			}else if(args[0].equalsIgnoreCase(sender.getName())) {
				if(hasPermissions(sender,plugin.getPermissionService(),"chatmanager.*","chatmanager.cmd.*","chatmanager.cmd.clearchat.*","chatmanager.cmd.clearchat.me")) {
					for(int x = 0;x < 100;x++) sender.sendMessage("");
					sender.sendMessage(color(plugin.getMessagesConfig().getString(Message.CLEARED_OWN_CHAT.toString())));
				}else {
					sender.sendMessage(color(plugin.getMessagesConfig().getString(Message.NO_CLEAR_OWN_CHAT.toString())));
				}
			}else {
				Player player = Bukkit.getServer().getPlayer(args[0]);
				if(Utils.validatePlayer(player, (Player) sender,plugin.getMessagesConfig())){
					if(hasPermissions(sender,plugin.getPermissionService(),"chatmanager.*","chatmanager.cmd.*","chatmanager.cmd.clearchat.*","chatmanager.cmd.clearchat.others")) {
						for(int x = 0;x < 100;x++) player.sendMessage("");
						sender.sendMessage(color(plugin.getMessagesConfig().getString(Message.CLEARED_PLAYER_CHAT.toString()).replace("%player%", player.getName())));
						player.sendMessage(color(plugin.getMessagesConfig().getString(Message.PLAYER_CHAT_HAS_CLEARED.toString()).replace("%player%", sender.getName())));
					}else {
						sender.sendMessage(color(plugin.getMessagesConfig().getString(Message.NO_CLEAR_PLAYER_CHAT.toString())));
					}
				}
			}

		}
		
		return false;
	}

}
