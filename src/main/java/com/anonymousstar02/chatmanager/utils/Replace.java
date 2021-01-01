package com.anonymousstar02.chatmanager.utils;

public class Replace {
	
	public static String removeDoubleChar(String message,String word) {
		String msg = message;
		String[] array = {"aa","bb","cc","dd","ee","ff","gg","hh","ii","jj","kk","ll","mm","nn","oo",
				"pp","qq","rr","ss","tt","uu","vv","ww","xx","yy","zz"};
		for(String arr : array) {
			if(!word.contains(arr)) {
				while(msg.contains(arr)) {
					msg = msg.replace(arr, "");
				}
			}
		}
		return msg;
	}
	
	public static String replaceNoAlphabetChar(String message) {
		char[] array = message.toCharArray();
		for(int x  = 0;x < array.length;x++) {
			if(!Replace.isAlphabetChar(array[x])) array[x] = ' ';
		}
		return Replace.removeSpace(String.valueOf(array));
	}
	
	public static String replaceNoIpChar(String message) {
		char[] chars = message.toCharArray();
		
		for(int x = 0;x < chars.length;x++) {
			if((!Replace.isNumber(chars[x])) && (!(chars[x] == '.')) && (!(chars[x] == ':'))) chars[x] = ' ';
		}
		return Replace.removeSpace(String.valueOf(chars));
	}
	
	public static String replaceNumberInAlphabetChar(String message) {
		char[] array = message.toCharArray();
		for(int x = 0;x < 0;x++) {
			switch(array[x]) {
				case '0':
					array[x] = 'o';
					break;
				case '1':
					array[x] = 'i';
					break;
				case '3':
					array[x] = 'e';
					break;
				case '4':
					array[x] = 'a';
					break;
				case '5':
					array[x] = 's';
					break;
				case '6':
					array[x] = 'g';
					break;
				case 7:
					array[x] = 't';
					break;
			}
		}
		return String.valueOf(array);
	}
	
	public static String removeSpace(String str) {
		str = str.replaceAll(" ", "");
		return str;
	}
	
	private static boolean isAlphabetChar(char ch) {
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
	
	private static boolean isNumber(char ch) {
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
	
}