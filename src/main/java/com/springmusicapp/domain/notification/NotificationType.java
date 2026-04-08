package com.springmusicapp.domain.notification;

public enum NotificationType {

    OFFER_RECEIVED("You have received a new contract offer from manager %s."),
    OFFER_REJECTED("Contract negotiations with the band '%s' have been rejected."),

    BAND_INVITATION("Musician %s has invited you to join the band '%s'."),
    BAND_KICKED("You have been removed from the band '%s'."),

    SYSTEM_ALERT("System Alert: %s");

    private final String messageTemplate;

    NotificationType(String messageTemplate) {
        this.messageTemplate = messageTemplate;
    }

    public String getMessageTemplate() {
        return messageTemplate;
    }
}