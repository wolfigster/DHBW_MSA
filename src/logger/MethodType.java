package logger;

public enum MethodType {
    NONE("none"),
    ENCRYPT("encrypt"),
    DECRYPT("decrypt"),
    CRACK("crack")
    ;

    private final String type;

    MethodType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
