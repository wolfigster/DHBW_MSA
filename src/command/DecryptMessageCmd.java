package command;

import configuration.Configuration;
import cryptography.Cryptography;
import cryptography.KeyChecker;
import logger.AlgorithmType;
import logger.Logger;
import logger.MethodType;

import java.io.File;

public class DecryptMessageCmd implements ICommand {

    private final String message;
    private final String algorithmType;
    private final String keyfile;
    private final Cryptography cryptography = new Cryptography();

    public DecryptMessageCmd(String message, String algorithm, String keyfile) {
        System.out.println("DecryptMessageCmd was constructed");
        this.message = message;
        this.algorithmType = algorithm;
        this.keyfile = keyfile;
    }

    @Override
    public String execute() {
        System.out.println("Run command DecryptMessageCmd");
        Logger logger = new Logger(MethodType.DECRYPT, AlgorithmType.NONE);
        AlgorithmType _algorithmType;
        File _keyfile;

        logger.info("Run command DecryptMessageCmd");
        logger.info("Start initialization for decryption:");

        _algorithmType = AlgorithmType.getAlgorithm(algorithmType);
        if(_algorithmType != null) {
            logger.info("Algorithm successful recognized -> " + _algorithmType.getName());
        } else {
            logger.error("Couldn't find algorithm with name: \"" + algorithmType + "\"");
            logger.error("Stopping decryption...");
            logger.setLogFileType(AlgorithmType.NONE, true);
            System.out.println("Decryption failed");
            return "Couldn't find algorithm with name: \"" + algorithmType + "\"";
        }

        _keyfile = new File(Configuration.instance.keyfileDirectory + keyfile);
        if(_keyfile.exists()) {
            logger.info("Found keyfile: \"" + keyfile + "\"");
        } else {
            logger.error("Couldn't find keyfile: \"" + keyfile + "\"");
            logger.error("Stopping decryption...");
            logger.setLogFileType(_algorithmType, true);
            System.out.println("Decryption failed");
            return "Couldn't find keyfile: \"" + keyfile + "\"";
        }

        if(!KeyChecker.checkKeyFile(_keyfile, _algorithmType)) {
            return "Please check your keyfile \"" + keyfile + "\"";
        }

        logger.info("Start decryption:");
        String decryptedMsg = cryptography.decrypt(message, _algorithmType, _keyfile);
        logger.info("\"" + message + "\" decrypted to \"" + decryptedMsg + "\"");
        logger.setLogFileType(_algorithmType, false);
        return decryptedMsg;
    }
}