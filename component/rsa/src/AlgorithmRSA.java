import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Base64;

public class AlgorithmRSA {

    private static AlgorithmRSA instance = new AlgorithmRSA();
    public Port port;

    private AlgorithmRSA() {
        port = new Port();
    }

    public static AlgorithmRSA getInstance() {
        return instance;
    }

    public class Port implements IAlgorithmRSA {
        @Override
        public String encrypt(String plainMessage, File keyFile) {
            return encryptRSA(plainMessage, keyFile);
        }

        @Override
        public String decrypt(String cipher, File keyFile) {
            return decryptRSA(cipher, keyFile);
        }
    }

    private String encryptRSA(String plainMessage, File keyFile) {
        Key key = getKeyFromFile(keyFile, true);

        return Base64.getEncoder().encodeToString(encryptIRSA(plainMessage, key));
    }

    private String decryptRSA(String cipher, File keyFile) {
        Key key = getKeyFromFile(keyFile, false);

        return decryptIRSA(Base64.getDecoder().decode(cipher), key);
    }

    private Key getKeyFromFile(File keyFile, boolean publicKey) {
        try {
            BigInteger n = null, de = null;
            for(String line : Files.readAllLines(keyFile.toPath(), StandardCharsets.UTF_8)) {
                if(line.contains("n")) n = new BigInteger(line.replaceAll("[^0-9]", ""));
                if(line.contains(publicKey ? "e" : "d")) de = new BigInteger(line.replaceAll("[^0-9]", ""));
            }

            return new Key(n, de);
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
        return null;
    }


    // 01_algorithms/02_rsa/01_base/01_example01 code
    private BigInteger crypt(BigInteger message, Key key) {
        return message.modPow(key.getDorE(), key.getN());
    }

    public byte[] encryptIRSA(String plainMessage, Key key) {
        byte[] bytes = plainMessage.getBytes(Charset.defaultCharset());
        return crypt(new BigInteger(bytes), key).toByteArray();
    }

    public String decryptIRSA(byte[] cipher, Key key) {
        byte[] msg = crypt(new BigInteger(cipher), key).toByteArray();
        System.out.println("decryptIRSA - bytes " + cipher + " - " + Arrays.toString(cipher));
        System.out.println("decryptIRSA - BigInteger " + new BigInteger(cipher));
        return new String(msg);
    }
}