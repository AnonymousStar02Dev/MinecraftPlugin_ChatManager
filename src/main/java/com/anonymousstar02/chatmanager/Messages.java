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
		message = Utils.removeNoAlphabetChars(message);
		message.toLowerCase();
		for(String word : words){
			if(message.contains(word)) return word;
		}
		return "null";
	}

	public static boolean isUrl(String str){
		return str.matches("(([a-zA-Z]+://)?)(((\\w|-)+\\.)+)(\\w+)((\\d|\\D)+)");
	}

	public static boolean containsUrl(String[] array,List<String> urls) {
		for(String str : array){
			if(Messages.isUrl(str)){
				for(String url : urls){
					if(str.equals(url)) return false;
				}
				return true;
			}
		}
		return false;
	}

}
