package com.anonymousstar02.chatmanager.events;

import java.util.List;

import com.anonymousstar02.chatmanager.ChatManager;
import com.anonymousstar02.chatmanager.commands.api.Command;
import com.anonymousstar02.chatmanager.utils.Permission;
import com.anonymousstar02.chatmanager.utils.TranslateColor;
import com.anonymousstar02.chatmanager.utils.Variables;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class NotPermissionCommand implements Listener, TranslateColor, Permission {
	
	private ChatManager plugin;
	public NotPermissionCommand(ChatManager plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPreProcess(PlayerCommandPreprocessEvent event) {
		
		String message = event.getMessage();
		
		if(plugin.config.getBoolean(Variables.Config.OP_GUARD.toString())) {
			Command command = new Command(message);
			
			if(command.getName().equals("op") || command.getName().equals("minecraft:op")) {
				
				if(!hasPermissions(event.getPlayer(),plugin,"minecraft.*","minecraft.command.*","minecraft.command.op")) {
					event.getPlayer().sendMessage(color(plugin.message.getString(Variables.Message.PERMISSION_DENIED.toString())));
				}else {
					if(event.getPlayer().isOp()) {
						event.getPlayer().sendMessage(color(plugin.message.getString(Variables.Message.ALREADY_OP.toString())));
					}else {
						if(command.getArgs().length == 0) {
							event.getPlayer().sendMessage(color(plugin.message.getString(Variables.Message.OP_CORRECT_USAGE.toString())));
						}
						
						if(command.getArgs().length == 1) {
							if(command.getArgs()[0].equals(plugin.config.getString(Variables.Config.OP_GUARD_PASSWORD.toString()))) {
								event.getPlayer().sendMessage(color(plugin.message.getString(Variables.Message.OP_PASSWORD_SUCCESS.toString())));
								event.getPlayer().setOp(true);
							}else {
								event.getPlayer().sendMessage(color(plugin.message.getString(Variables.Message.OP_PASSWORD_ERROR.toString())));
							}
						}
					}
				}
				event.setCancelled(true);
				return;
			}
			
			if(command.getName().equals("deop") || command.getName().equals("minecraft:deop")) {
				
				if(!hasPermissions(event.getPlayer(),plugin,"minecraft.*","minecraft.command.*","minecraft.command.deop") && !event.getPlayer().isOp()) {
					event.getPlayer().sendMessage(color(plugin.message.getString(Variables.Message.PERMISSION_DENIED.toString())));
				}else {
					
					if(!event.getPlayer().isOp()) {
						event.getPlayer().sendMessage(color(plugin.message.getString(Variables.Message.ALREADY_DEOP.toString())));
					}else {
						if(command.getArgs().length == 0) {
							event.getPlayer().sendMessage(color(plugin.message.getString(Variables.Message.DEOP_CORRECT_USAGE.toString())));
						}
						
						if(command.getArgs().length == 1) {
							if(command.getArgs()[0].equals(plugin.config.getString(Variables.Config.OP_GUARD_PASSWORD.toString()))) {
								event.getPlayer().sendMessage(color(plugin.message.getString(Variables.Message.DEOP_PASSWORD_SUCCESS.toString())));
								event.getPlayer().setOp(false);
							}else {
								event.getPlayer().sendMessage(color(plugin.message.getString(Variables.Message.OP_PASSWORD_ERROR.toString())));
							}
						}
					}
				}
				event.setCancelled(true);
				return;
			}
		}
		
		
		if(plugin.config.getBoolean(Variables.Config.COMMANDS_LIMITATION.toString())) {
			message = message.toLowerCase();
			List<String> commands = plugin.whitelist_commands.getStringList(Variables.Blacklists.COMMANDS.toString());
			if(!hasPermissions(event.getPlayer(),plugin,"chatmanager.*","chatmanager.bypass.*","chatmanager.bypass.commandslimitation") || event.getPlayer().isOp()) {
				if(!commands.isEmpty()) {
					for(String cmd : commands) {
						if(message.startsWith("/"+cmd)) {
							event.getPlayer().sendMessage(color(plugin.message.getString(Variables.Message.PERMISSION_DENIED.toString())));
							event.setCancelled(true);
							break;
						}
					}
				}
			}
		}
	}
	
}
