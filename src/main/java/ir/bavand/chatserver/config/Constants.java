package ir.bavand.chatserver.config;

public enum Constants {
    DEFAULT_MESSAGE_SUBSCRIBER_DESTINATION("textmessages");


    private String value;
    Constants(String value) {
        this.value=value;
    }

    public String getValue() {
        return value;
    }
}
