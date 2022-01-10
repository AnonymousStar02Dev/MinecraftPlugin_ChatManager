package com.anonymousstar02.chatmanager;

import com.anonymousstar02.chatmanager.enums.Message;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.simpleyaml.configuration.file.YamlFile;

public class Utils{

	public static String removeNoAlphabetChars(String message){
		char[] array = message.toCharArray();
		for(char ch : array){
			if(!Character.isLetter(ch)){
				message = message.replaceAll(String.valueOf(ch),"");
			}
		}
		return message;
	}

	public static String removeNoIpChar(String message) {
		char[] chars = message.toCharArray();
		
		for(char ch : chars) {
			if((!Character.isDigit(ch)) && (!(ch == '.')) && (!(ch == ':'))){
				if(message.contains(String.valueOf(ch))){
					message = message.replaceAll(String.valueOf(ch),"");
				}
			}
		}
		return message;
	}

	public static boolean validatePlayer(Player target, Player sender, YamlFile message){
		if(target != null) return true;
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message.getString(Message.PLAYER_NOT_FOUND.toString())));
		return false;
	}

}