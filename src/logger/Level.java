package logger;

public enum Level {
    INFO("INFO", "info"),
    ERROR("ERROR", "error")
    ;

    private final String name;
    private final String type;


    Level(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
