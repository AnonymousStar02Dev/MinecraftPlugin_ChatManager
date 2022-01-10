package com.anonymousstar02.chatmanager.enums;

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
    OP_GUARD,
    OP_GUARD_PASSWORD,
    PERMISSION_NEEDED,
    SEND_TIME;

    public String toString() {
        String name = name();
        if(name.contains("_")) name = name.replace("_", "-");
        return name.toLowerCase();
    }

}
