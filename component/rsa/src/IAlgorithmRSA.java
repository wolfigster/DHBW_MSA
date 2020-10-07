import java.io.File;

public interface IAlgorithmRSA {
    String encrypt(String plainMessage, File keyFile);
    String decrypt(String cipher, File keyFile);
}