package com.anonymousstar02.chatmanager;

public enum Configs {

    CONFIG,PERMISSIONS,BLOCKED_WORDS,WHITELIST_COMMANDS,ALLOWED_URLS,MESSAGES;

    public String toString(){
        if(name().equals(Configs.MESSAGES.name())){
            return "message_{locale}.yml";
        }
        return name().toLowerCase()+".yml";
    }

}
