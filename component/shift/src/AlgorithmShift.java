import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class AlgorithmShift {

    private static AlgorithmShift instance = new AlgorithmShift();
    public Port port;

    private AlgorithmShift() {
        port = new Port();
    }

    public static AlgorithmShift getInstance() {
        return instance;
    }

    public class Port implements IAlgorithmShift {
        @Override
        public String encrypt(String plainMessage, File keyFile) {
            return encryptShift(plainMessage, keyFile);
        }

        @Override
        public String decrypt(String cipher, File keyFile) {
            return decryptShift(cipher, keyFile);
        }
    }

    private String encryptShift(String plainMessage, File keyFile) {
        int key = getKeyFromFile(keyFile);

        return encryptIShift(plainMessage, key);
    }

    private String decryptShift(String cipher, File keyFile) {
        int key = getKeyFromFile(keyFile);

        return decryptIShift(cipher, key);
    }

    private int getKeyFromFile(File keyFile) {
        try {
            for(String line : Files.readAllLines(keyFile.toPath(), StandardCharsets.UTF_8)) {
                if(line.contains("key")) return Integer.parseInt(line.replaceAll("[^0-9]", ""));
            }
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
        return 0;
    }


    // 01_algorithms/01_shift/01_base code
    public String encryptIShift(String plainText, int key) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < plainText.length(); i++) {
            char character = (char) (plainText.codePointAt(i) + key);
            stringBuilder.append(character);
        }

        return stringBuilder.toString();
    }

    public String decryptIShift(String cipherText, int key) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < cipherText.length(); i++) {
            char character = (char) (cipherText.codePointAt(i) - key);
            stringBuilder.append(character);
        }

        return stringBuilder.toString();
    }
}
