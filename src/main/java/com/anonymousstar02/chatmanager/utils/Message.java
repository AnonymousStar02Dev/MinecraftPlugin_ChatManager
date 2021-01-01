package com.anonymousstar02.chatmanager.utils;

import com.anonymousstar02.chatmanager.ChatManager;

import java.util.List;

public class Message {
	
	public static boolean isIp(String message) {
		String tmp = "([01]?[0-9]{1,2}|2[0-4][0-9]|25[0-5])";
		String ip = tmp + "\\." + tmp + "\\." + tmp + "\\." + tmp;
		String port = "(:[0-9]{0,5}$)?";
		
		message = message.toLowerCase();
		tmp = tmp.toLowerCase();
		ip = ip.toLowerCase();
		port = port.toLowerCase();
		
		if((Replace.replaceNoIpChar(message).matches(ip+port))){
			return true;
		}
		return false;
	}
	
	public static boolean isUrl(String message) {
		String[] words = message.split("\\s+");
		for(String word : words) {
			if(word.matches("(((http?|https|ftp|file)://)?((W|w){3}.)?([a-zA-Z0-9]+\\.)(([a-zA-Z0-9]+\\.){0,16})([a-zA-Z]+)(/(\\w|\\W|\\d|\\D)+){0,16})")) {
				return true;
			}
		}
		return false;
	}
	
	public static String isSpam(String message, ChatManager plugin) {
		String check = "null";
		message = message.toLowerCase();
		List<String> words = plugin.blacklist_words.getStringList(Variables.Blacklists.WORDS.toString());
		if(!words.isEmpty()) {
			for(String tmp : words) {
				message = Replace.removeDoubleChar(message, tmp.toLowerCase());
				if((Replace.replaceNoAlphabetChar(message).contains(tmp.toLowerCase())) 
						|| (Replace.replaceNumberInAlphabetChar(message).contains(tmp.toLowerCase()))){
						check = tmp;
						break;
				}
			}
		}
		return check;
	}

}
