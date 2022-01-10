package com.anonymousstar02.chatmanager;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public interface Permission {
	
	default boolean hasPermissions(CommandSender sender, net.milkbowl.vault.permission.Permission permission, String... perms) {
		return hasPermissions((Player)sender,permission,perms);
	}
	
	default boolean hasPermissions(Player player, net.milkbowl.vault.permission.Permission permission, String... perms) {
		for(String perm : perms) {
			if(permission.has(player, perm)) return true;
		}
		return false;
	}
	
}
