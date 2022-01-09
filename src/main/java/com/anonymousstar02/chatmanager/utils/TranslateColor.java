package com.anonymousstar02.chatmanager.utils;

import org.bukkit.ChatColor;

public interface TranslateColor {
	
	default String color(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}
	
}
