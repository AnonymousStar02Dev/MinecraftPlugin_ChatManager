package com.anonymousstar02.chatmanager.utils;

import java.util.List;

public class Messages {

	public static boolean containsIp(String message) {
		String tmp = "([01]?[0-9]{1,2}|2[0-4][0-9]|25[0-5])";
		String ip = tmp + "\\." + tmp + "\\." + tmp + "\\." + tmp;
		String port = "(:[0-9]{0,5}$)?";
		
		message = message.toLowerCase();
		ip = ip.toLowerCase();
		port = port.toLowerCase();
		
		return Utils.removeNoIpChar(message).matches(ip+port);
	}

	public static String containsSwearWords(String message, List<String> words){
		String msg_copy = Utils.removeNoAlphabetChars(message);
		msg_copy = msg_copy.toLowerCase();
		if(!words.isEmpty()){
			for(String word : words){
				if(msg_copy.contains(word)){
					return word;
				}
			}
		}
		return "null";
	}
	
	public static boolean containsUrl(String message,List<String> urls) {
		String[] words = message.split("\\s+");
		for(String word : words) {
			if(word.matches("(((http?|https|ftp|file)://)?(([Ww]){3}.)?([a-zA-Z0-9]+\\.)(([a-zA-Z0-9]+\\.){0,16})([a-zA-Z]+)(/(\\w|\\W|\\d|\\D)+){0,16})")) {
				if(!urls.isEmpty()) for(String url : urls) if(word.equals(url)) return false;
				return true;
			}
		}
		return false;
	}

}
