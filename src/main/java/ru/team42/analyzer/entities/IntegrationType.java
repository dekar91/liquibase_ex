package ru.team42.analyzer.entities;

public enum IntegrationType {
    YA_METRIKA("ya"), GOOGLE("google");

    private String type;

    IntegrationType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
