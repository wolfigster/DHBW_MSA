package cryptography;

import logger.AlgorithmType;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class KeyChecker {

    public static boolean checkKeyFile(File keyfile, AlgorithmType algorithmType) {
        try {
            boolean n = false, e = false, d = false, key = false;
            for(String line : Files.readAllLines(keyfile.toPath(), StandardCharsets.UTF_8)) {
                if(line.contains("\"n\":")) n = true;
                if(line.contains("\"e\":")) e = true;
                if(line.contains("\"d\":")) d = true;
                if(line.contains("\"key\":")) key = true;
            }
            if(algorithmType.equals(AlgorithmType.RSA)) {
                if(n && e && d && !key) return true;
            } else if(algorithmType.equals(AlgorithmType.SHIFT)) {
                if(!n && !e && !d && key) return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
