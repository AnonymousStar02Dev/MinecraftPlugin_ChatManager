package com.anonymousstar02.chatmanager;

import com.anonymousstar02.chatmanager.enums.Message;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.simpleyaml.configuration.file.YamlFile;

import java.io.IOException;
import java.net.InetAddress;

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

	public static String replaceDigToLet(String str){
		str = str.replaceAll("4","a");
		str = str.replaceAll("3","e");
		str = str.replaceAll("1","i");
		str = str.replaceAll("0","o");
		str = str.replaceAll("5","s");
		str = str.replaceAll("6","g'");
		return str;
	}

	public static boolean validatePlayer(Player target, Player sender, YamlFile message){
		if(target != null) return true;
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message.getString(Message.PLAYER_NOT_FOUND.toString())));
		return false;
	}

	public static boolean validateUrl(String url){
		try{
			if(InetAddress.getByName(InetAddress.getByName(url).getHostAddress()).isReachable(250)){
				return true;
			}
		}catch (IOException e){  }
		finally{ return false; }
	}

}