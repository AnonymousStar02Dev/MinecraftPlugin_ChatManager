package com.anonymousstar02.chatmanager.commands;

import com.anonymousstar02.chatmanager.ChatManager;
import com.anonymousstar02.chatmanager.utils.Permission;
import com.anonymousstar02.chatmanager.utils.TranslateColor;
import com.anonymousstar02.chatmanager.utils.enums.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OpOther implements CommandExecutor, Permission, TranslateColor {
	
	private final ChatManager plugin;
	public OpOther(ChatManager plugin) {
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender,Command cmd,String label,String[] args) {
		
		try {
			if(cmd.getName().equals("opothers")) {
				
				if(!sender.isOp()){
					sender.sendMessage(color(plugin.getMessagesConfig().getString(Message.PERMISSION_DENIED.toString())));
					return false;
				}
				
				if(args.length != 2) {
					sender.sendMessage(color(plugin.getMessagesConfig().getString(Message.OPOTHER_CORRECT_USAGE.toString())));
					return true;
				}

				String type = args[0];
				Player player = plugin.getServer().getPlayer(args[1]);
				if(type.equals("op") || type.equals("deop")) {

					if(player.isOnline()) {
						switch(type) {
						  case "op":
							  if(player.isOp()) {
								  sender.sendMessage(color(plugin.getMessagesConfig().getString(Message.OPOTHER_ALREADY_OP.toString()).replace("%player%", player.getName())));
							  }else {
								  sender.sendMessage(color(plugin.getMessagesConfig().getString(Message.OPOTHER_CORRECT_OP.toString()).replace("%player%", player.getName())));
								  player.setOp(true);
							  }
							  break;
						  case "deop":
							  if(!player.isOp()) {
								  sender.sendMessage(color(plugin.getMessagesConfig().getString(Message.OPOTHER_ALREADY_DEOP.toString()).replace("%player%", player.getName())));
							  }else {
								  sender.sendMessage(color(plugin.getMessagesConfig().getString(Message.OPOTHER_CORRECT_DEOP.toString()).replace("%player%", player.getName())));
								  player.setOp(false);
							  }
							  break;
						}
					}

				}else {
					sender.sendMessage(color(plugin.getMessagesConfig().getString(Message.OPOTHER_CORRECT_USAGE.toString())));
				}

			}
		}catch(Exception e) {
			sender.sendMessage(color(plugin.getMessagesConfig().getString(Message.PLAYER_NOT_FOUND.toString())));
		}
		
		return false;
	}

}
