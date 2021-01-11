package com.anonymousstar02.chatmanager.utils;

import com.anonymousstar02.chatmanager.ChatManager;

import java.util.Locale;

public class Message {

	public static boolean containsIp(String message) {
		String tmp = "([01]?[0-9]{1,2}|2[0-4][0-9]|25[0-5])";
		String ip = tmp + "\\." + tmp + "\\." + tmp + "\\." + tmp;
		String port = "(:[0-9]{0,5}$)?";
		
		message = message.toLowerCase();
		tmp = tmp.toLowerCase();
		ip = ip.toLowerCase();
		port = port.toLowerCase();
		
		if((Utils.removeNoIpChar(message).matches(ip+port))){
			return true;
		}
		return false;
	}

	public static String containsSwearWords(String message, ChatManager plugin){
		String msg_copy = Utils.removeNoAlphabetChars(message);
		msg_copy = msg_copy.toLowerCase();

		if(!plugin.words_list.isEmpty()){

			for(String word : plugin.words_list){
				if(msg_copy.contains(word)){
					return word;
				}
			}
		}

		return "null";
	}
	
	public static boolean containsUrl(String message) {
		String[] words = message.split("\\s+");
		for(String word : words) {
			if(word.matches("(((http?|https|ftp|file)://)?((W|w){3}.)?([a-zA-Z0-9]+\\.)(([a-zA-Z0-9]+\\.){0,16})([a-zA-Z]+)(/(\\w|\\W|\\d|\\D)+){0,16})")) {
				return true;
			}
		}
		return false;
	}

}
