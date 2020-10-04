package logger;

public enum AlgorithmType {
    RSA("rsa"),
    SHIFT("shift")
    ;

    private final String type;


    AlgorithmType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
