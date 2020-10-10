import java.io.File;

public interface IAlgorithmShift {
    String encrypt(String plainMessage, File keyFile);
    String decrypt(String cipher, File keyFile);
}