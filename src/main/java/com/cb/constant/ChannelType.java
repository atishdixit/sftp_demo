package com.cb.constant;

public enum ChannelType {
    SFTP("sftp"),
    EXEC("exec"),
    SHELL("shell");

    ChannelType(String value) {
        this.value = value;
    }

    String value;

    public String getValue() {
        return value;
    }
}
