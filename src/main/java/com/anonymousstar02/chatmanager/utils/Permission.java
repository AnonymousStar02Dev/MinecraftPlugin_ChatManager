package com.anonymousstar02.chatmanager.utils;

import com.anonymousstar02.chatmanager.ChatManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;



public interface Permission {
	
	public default boolean hasPermissions(CommandSender sender, ChatManager plugin, String... perms) {
		return hasPermissions((Player)sender,plugin,perms);
	}
	
	public default boolean hasPermissions(Player player,ChatManager plugin,String... perms) {
		for(String perm : perms) {
			if(plugin.permission.has(player, perm)) return true;
		}
		return false;
	}
	
}
