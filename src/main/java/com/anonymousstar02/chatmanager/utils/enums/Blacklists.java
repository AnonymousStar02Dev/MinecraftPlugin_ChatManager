package com.anonymousstar02.chatmanager.utils.enums;

public enum Blacklists{
    WORDS,
    COMMANDS,
    URLs;

    public String toString() {
        return name().toLowerCase();
    }
}
