package logger;

public enum AlgorithmType {
    NONE("NONE", "none"),
    RSA("RSA", "rsa"),
    SHIFT("Shift", "shift")
    ;

    private final String name;
    private final String type;


    AlgorithmType(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public static AlgorithmType getAlgorithm(String name) {
        for(AlgorithmType algorithmType : values()) {
            if(algorithmType.getName().toLowerCase().equals(name.toLowerCase())) return algorithmType;
        }
        return null;
    }
}
