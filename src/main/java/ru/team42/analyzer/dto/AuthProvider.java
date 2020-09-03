package ru.team42.analyzer.dto;

public enum AuthProvider {
    DEFAULT("default"), CHAT2DESK("chat2Desk");

    private String type;

    AuthProvider(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
