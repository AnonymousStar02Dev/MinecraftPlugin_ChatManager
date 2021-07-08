package com.anonymousstar02.chatmanager.commands.api;

public final class Command {
	
	/**
	 * 
	 * This class manage a command
	 * easly
	 * 
	 */
	
	public static String prefix = "/";
	private String[] args;
	private String name;
	
	public Command(String command) {
		this.name = command.replaceFirst(Command.prefix, "").split("\\s+")[0];
		command += " ";
		if(!command.replaceFirst(Command.prefix+this.name+" ", "").equals("")) 
			args = command.replaceFirst(Command.prefix+this.name+" ", "").split("\\s+");
		else args = new String[0];
	}
	
	public String[] getArgs() {
		return args;
	}
	
	public String getName() {
		return name;
	}
	
	public static boolean isCommand(String str) {
		return str.startsWith(Command.prefix);
	}

}
