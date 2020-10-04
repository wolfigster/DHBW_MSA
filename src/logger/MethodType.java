package logger;

public enum MethodType {
    ENCRYPT("encrypt"),
    DECRYPT("decrypt")
    ;

    private final String type;

    MethodType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
