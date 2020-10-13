package command;

import configuration.Configuration;
import cryptography.Cryptography;
import logger.AlgorithmType;
import logger.Logger;
import logger.MethodType;

import java.io.File;

public class EncryptMessageCmd implements ICommand {

    private final String message;
    private final String algorithmType;
    private final String keyfile;
    private final Cryptography cryptography = new Cryptography();

    public EncryptMessageCmd(String message, String algorithm, String keyfile) {
        System.out.println("EncryptMessageCmd was constructed");
        this.message = message;
        this.algorithmType = algorithm;
        this.keyfile = keyfile;
    }

    @Override
    public String execute() {
        System.out.println("Run command EncryptMessageCmd");
        Logger logger = new Logger(MethodType.ENCRYPT, AlgorithmType.NONE);
        AlgorithmType _algorithmType;
        File _keyfile;

        logger.info("Run command EncryptMessageCmd");
        logger.info("Start initialization for encryption:");

        _algorithmType = AlgorithmType.getAlgorithm(algorithmType);
        if(_algorithmType != null) {
            logger.info("Algorithm successful recognized -> " + _algorithmType.getName());
        } else {
            logger.error("Couldn't find algorithm with name: \"" + algorithmType + "\"");
            logger.error("Stopping encryption...");
            logger.setLogFileType(AlgorithmType.NONE, true);
            System.out.println("Encryption failed");
            return "Couldn't find algorithm with name: \"" + algorithmType + "\"";
        }

        _keyfile = new File(Configuration.instance.keyfileDirectory + keyfile);
        if(_keyfile.exists()) {
            logger.info("Found keyfile: \"" + keyfile + "\"");
        } else {
            logger.error("Couldn't find keyfile: \"" + keyfile + "\"");
            logger.error("Stopping encryption...");
            logger.setLogFileType(_algorithmType, true);
            System.out.println("Encryption failed");
            return "Couldn't find keyfile: \"" + keyfile + "\"";

        }

        logger.info("Start encryption:");
        String encryptedMsg = cryptography.encrypt(message, _algorithmType, _keyfile);
        logger.info("\"" + message + "\" encrypted to \"" + encryptedMsg + "\"");
        logger.setLogFileType(_algorithmType, false);
        return encryptedMsg;
    }
}