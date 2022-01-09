package com.anonymousstar02.chatmanager.utils;

public class Utils {

	public static boolean isAlphabetChar(char ch) {
		switch(ch){
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				return true;
			default:
				return false;
		}
	}

	public static boolean isNumber(char ch) {
		switch(ch) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				return true;
			default:
				return false;
		}
	}

	public static String removeNoAlphabetChars(String message){
		char[] array = message.toCharArray();
		for(int x = 0;x < array.length;x++){
			if(!Utils.isAlphabetChar(array[x])){
				array[x] = ' ';
			}
		}
		return Utils.removeSpace(String.valueOf(array));
	}

	public static String removeNoIpChar(String message) {
		char[] chars = message.toCharArray();
		
		for(int x = 0;x < chars.length;x++) {
			if((!Utils.isNumber(chars[x])) && (!(chars[x] == '.')) && (!(chars[x] == ':'))) chars[x] = ' ';
		}
		return Utils.removeSpace(String.valueOf(chars));
	}
	
	public static String removeSpace(String str) {
		str = str.replaceAll(" ", "");
		return str;
	}
	
}