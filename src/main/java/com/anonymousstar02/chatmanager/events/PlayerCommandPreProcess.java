package com.anonymousstar02.chatmanager.events;

import com.anonymousstar02.chatmanager.ChatManager;
import com.anonymousstar02.chatmanager.commands.api.Command;
import com.anonymousstar02.chatmanager.utils.Permission;
import com.anonymousstar02.chatmanager.utils.TranslateColor;
import com.anonymousstar02.chatmanager.utils.enums.Config;
import com.anonymousstar02.chatmanager.utils.enums.Message;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerCommandPreProcess implements Listener, TranslateColor, Permission {
	
	private final ChatManager plugin;
	public PlayerCommandPreProcess(ChatManager plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onCommandLimit(PlayerCommandPreprocessEvent event){
		String message = event.getMessage();
		if(plugin.getMainConfig().getBoolean(Config.COMMANDS_LIMITATION.toString())) {
			message = message.toLowerCase();
			if(plugin.getWhitelistCommands().isEmpty()) return;
			if(!hasPermissions(event.getPlayer(),plugin.getPermissionService(),"chatmanager.*","chatmanager.bypass.*","chatmanager.bypass.commandslimitation") || event.getPlayer().isOp()) {
				if(!plugin.getWhitelistCommands().isEmpty()) {
					for(String cmd : plugin.getWhitelistCommands()) {
						if(message.startsWith("/"+cmd)) {
							event.getPlayer().sendMessage(color(plugin.getMessagesConfig().getString(Message.PERMISSION_DENIED.toString())));
							event.setCancelled(true);
							break;
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onDeopCommand(PlayerCommandPreprocessEvent event){

		String message = event.getMessage();

		if(plugin.getMainConfig().getBoolean(Config.OP_GUARD.toString())) {
			Command command = new Command(message);

			if(command.getName().equals("deop") || command.getName().equals("minecraft:deop")) {

				if(!hasPermissions(event.getPlayer(),plugin.getPermissionService(),"minecraft.*","minecraft.command.*","minecraft.command.deop") && !event.getPlayer().isOp()) {
					event.getPlayer().sendMessage(color(plugin.getMessagesConfig().getString(Message.PERMISSION_DENIED.toString())));
				}else {

					if(!event.getPlayer().isOp()) {
						event.getPlayer().sendMessage(color(plugin.getMessagesConfig().getString(Message.ALREADY_DEOP.toString())));
					}else {
						if(command.getArgs().length == 0) {
							event.getPlayer().sendMessage(color(plugin.getMessagesConfig().getString(Message.DEOP_CORRECT_USAGE.toString())));
						}

						if(command.getArgs().length == 1) {
							if(command.getArgs()[0].equals(plugin.getMainConfig().getString(Config.OP_GUARD_PASSWORD.toString()))) {
								event.getPlayer().sendMessage(color(plugin.getMessagesConfig().getString(Message.DEOP_PASSWORD_SUCCESS.toString())));
								event.getPlayer().setOp(false);
							}else {
								event.getPlayer().sendMessage(color(plugin.getMessagesConfig().getString(Message.OP_PASSWORD_ERROR.toString())));
							}
						}
					}
				}
				event.setCancelled(true);
			}
		}

	}

	@EventHandler
	public void onOpCommand(PlayerCommandPreprocessEvent event) {
		
		String message = event.getMessage();
		
		if(plugin.getMainConfig().getBoolean(Config.OP_GUARD.toString())) {
			Command command = new Command(message);
			
			if(command.getName().equals("op") || command.getName().equals("minecraft:op")) {
				
				if(!hasPermissions(event.getPlayer(),plugin.getPermissionService(),"minecraft.*","minecraft.command.*","minecraft.command.op")) {
					event.getPlayer().sendMessage(color(plugin.getMessagesConfig().getString(Message.PERMISSION_DENIED.toString())));
				}else {
					if(event.getPlayer().isOp()) {
						event.getPlayer().sendMessage(color(plugin.getMessagesConfig().getString(Message.ALREADY_OP.toString())));
					}else {
						if(command.getArgs().length == 0) {
							event.getPlayer().sendMessage(color(plugin.getMessagesConfig().getString(Message.OP_CORRECT_USAGE.toString())));
						}
						
						if(command.getArgs().length == 1) {
							if(command.getArgs()[0].equals(plugin.getMainConfig().getString(Config.OP_GUARD_PASSWORD.toString()))) {
								event.getPlayer().sendMessage(color(plugin.getMessagesConfig().getString(Message.OP_PASSWORD_SUCCESS.toString())));
								event.getPlayer().setOp(true);
							}else {
								event.getPlayer().sendMessage(color(plugin.getMessagesConfig().getString(Message.OP_PASSWORD_ERROR.toString())));
							}
						}
					}
				}
				event.setCancelled(true);
			}
		}

	}
	
}
