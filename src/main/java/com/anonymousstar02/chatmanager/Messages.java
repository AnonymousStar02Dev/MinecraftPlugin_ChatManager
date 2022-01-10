package com.anonymousstar02.chatmanager;

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
		if(message.matches("(([a-zA-Z]+://)?)(((\\w|\\d)+\\.)+)((\\w|\\d)+)((\\w|\\W|\\d|\\D)+)")) {
			if(!urls.isEmpty()) {
				for(String url : urls) {
					if(message.contains(url)) return false;
				}
			}
			return true;
		}
		return false;
	}

}
