package com.anonymousstar02.chatmanager;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


	/*
	Old method to check url
	public static boolean isUrl(String str){
		return str.matches("(([a-zA-Z]+://)?)(((\\w|-)+\\.)+)(\\w+)((\\d|\\D)+)");
	}
	*/

	public static boolean containsUrl(String message,List<String> urls) {
		Matcher match = Pattern.compile("((\\w+\\.)+)(\\w+)").matcher(message);
		while(match.find()){
			String m = match.group();
			for(String url : urls){
				if(m.equals(url)) break;
			}
			if(Utils.validateUrl(m)) return true;
			if(Utils.validateUrl(Utils.replaceDigToLet(m))) return true;
		}
		return false;
	}

}
