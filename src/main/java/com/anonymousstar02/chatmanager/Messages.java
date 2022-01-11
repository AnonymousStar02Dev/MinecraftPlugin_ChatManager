package com.anonymousstar02.chatmanager;

import java.io.IOException;
import java.net.InetAddress;
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
			try{
				if(InetAddress.getByName(InetAddress.getByName(m).getHostAddress()).isReachable(250)){
					return true;
				}
			}catch (IOException e){}
			m = m.replaceAll("4","a");
			m = m.replaceAll("3","e");
			m = m.replaceAll("1","i");
			m = m.replaceAll("0","o");
			m = m.replaceAll("5","s");
			m = m.replaceAll("6","g'");
			try{
				if(InetAddress.getByName(InetAddress.getByName(m).getHostAddress()).isReachable(250)){
					return true;
				}
			}catch (IOException e){  }
		}
		return false;
	}

}
