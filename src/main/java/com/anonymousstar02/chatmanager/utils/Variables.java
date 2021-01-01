package com.anonymousstar02.chatmanager.utils;

public class Variables {
	
	//Config Variables
	public enum Config{
		
		ANTI_CAPS,
		ANTI_FLOOD,
		ANTI_IP,
		ANTI_REPEAT,
		ANTI_SWEAR,
		ANTI_URL,
		COMMANDS_LIMITATION,
		LOCALE,
		MUTE_CHAT,
		NULL,
		OP_GUARD,
		OP_GUARD_PASSWORD,
		PERMISSION_NEEDED,
		SEND_TIME,
		TAB_COMPLETE;
		
		public String toString() {
			String name = name();
			if(name.contains("_")) name = name.replace("_", "-");
			return name.toLowerCase();
		}
		
	}
	
	//Message Variables
	public enum Message{
		
		ALREADY_DEOP,
		ALREADY_OP,
		CHAT_IS_MUTED,
		CLEARCHAT_CORRECT_USAGE,
		CLEARED_OWN_CHAT,
		CLEARED_PLAYER_CHAT,
		CLEARED_SERVER_CHAT,
		CM_ADDWORD_CORRECT_USAGE,
		DEOP_CORRECT_USAGE,
		DEOP_PASSWORD_SUCCESS,
		GLOBAL_CHAT_MUTED,
		GLOBAL_CHAT_UNMUTED,
		HAVE_MUTED_CHAT,
		HAVE_UNMUTED_CHAT,
		NO_CLEAR_OWN_CHAT,
		NO_CLEAR_PLAYER_CHAT,
		NO_CLEAR_SERVER_CHAT,
		NO_FLOOD,
		NO_REPEAT_MESSAGE,
		NO_SWEAR,
		NO_WRITE,
		NO_WRITE_IP,
		NO_WRITE_URL,
		NULL,
		OP_CORRECT_USAGE,
		OP_PASSWORD_ERROR,
		OP_PASSWORD_SUCCESS,
		OPOTHER_ALREADY_DEOP,
		OPOTHER_ALREADY_OP,
		OPOTHER_CORRECT_DEOP,
		OPOTHER_CORRECT_OP,
		OPOTHER_CORRECT_USAGE,
		PERMISSION_DENIED,
		PLAYER_CHAT_HAS_CLEARED,
		PLAYER_NOT_FOUND,
		RELOAD;
		
		public String toString() {
			String name = name();
			if(name.contains("_")) name = name.replace("_", "-");
			return name.toLowerCase();
		}
	}
	
	public enum Blacklists{
		WORDS,
		COMMANDS;
		
		public String toString() {
			return name().toLowerCase();
		}
	}

}
