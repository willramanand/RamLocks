package com.gmail.willramanand.RamLocks.lang;

public enum Lang {

    /* =========================== PLUGIN SETUP/END RELATED MESSAGES START =============================== */

    // ENABLE MESSAGES
    ENABLE_START("{gold}==={aqua} ENABLE START {gold}==="),
    ENABLE_COMPLETE("{gold}=== {aqua}ENABLE {darkgreen}COMPLETE {gold}({s}Took {h}{start-time} ms{gold}) ==="),

    // DISABLE MESSAGES
    DISABLE_START("{gold}=== {w}DISABLE {aqua}START {gold}==="),
    DISABLE_COMPLETE("{gold}=== {w}DISABLE {green}COMPLETE {gold}==="),

    // GENERAL MESSAGES
    NOT_CONTAINER_OWNER("{w}You are not the owner of this container!"),
    BROKEN_LOCKED_CONTAINER("{w}You have broken one of your locked container!"),

    // BASE COMMAND MESSAGES
    COMMAND_DISABLED("{w}This command is not enabled!"),
    COMMAND_USAGE("{w}Usage: {usage}"),
    COMMAND_TOO_MANY_ARGS("{w}Too many args! Usage: {usage}"),
    COMMAND_PLAYER_ONLY("{w}Only a player can execute this command!"),
    COMMAND_NO_PERMISSION("{w}You do not have permission for this command!"),

    // LOCK COMMAND MESSAGES
    NON_LOCKABLE_BLOCK("{w}You are not looking at a lockable block!"),
    CANNOT_LOCK_ALREADY_LOCKED_SELF("{w}You cannot lock a container you have already locked!"),
    CANNOT_LOCK_ALREADY_LOCKED_OTHER("{w}You cannot lock a container another person has already locked!"),
    LOCKED_CONTAINER("{s}The container at {h}{x-location}{s}, {h}{y-location}{s}, {h}{z-location}{s} has been locked!"),

    // UNLOCK COMMAND MESSAGES
    CANNOT_UNLOCK_NOT_LOCKED("{w}You cannot unlock a container that is not locked!"),
    CANNOT_UNLOCK_ALREADY_LOCKED_OTHER("{w}You cannot unlock a container another person has already locked!"),
    UNLOCKED_CONTAINER("{s}The container at {h}{x-location}{s}, {h}{y-location}{s}, {h}{z-location}{s} has been unlocked!")

    /* =========================== PLUGIN SETUP/END RELATED MESSAGES END =============================== */
    ;

    private String def;

    Lang(String def) {
        this.def = def;
    }

    public String getDefault() {
        return def;
    }
}
