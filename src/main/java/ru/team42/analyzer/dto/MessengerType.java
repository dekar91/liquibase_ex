package ru.team42.analyzer.dto;

/**
 * MSupported messengers types
 */
public enum MessengerType {
    VK("vk"), TELEGRAM("telegram"), WHATS_UP("whats_up");

    private String type;

    MessengerType(String type) {
        this.type = type;
    }

}



