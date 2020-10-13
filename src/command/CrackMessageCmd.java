package command;

import com.google.common.util.concurrent.SimpleTimeLimiter;
import com.google.common.util.concurrent.TimeLimiter;
import cryptography.Cryptography;
import logger.AlgorithmType;
import logger.Logger;
import logger.MethodType;

import java.io.File;
import java.util.concurrent.*;

public class CrackMessageCmd implements ICommand {

    private final String message;
    private final String algorithmType;
    private final Cryptography cryptography = new Cryptography();

    public CrackMessageCmd(String message, String algorithm) {
        System.out.println("CrackMessageCmd was constructed");
        this.message = message;
        this.algorithmType = algorithm;
    }

    @Override
    public String execute() {
        System.out.println("Run command CrackMessageCmd");
        Logger logger = new Logger(MethodType.CRACK, AlgorithmType.NONE);
        AlgorithmType _algorithmType;
        File _keyfile = null;

        logger.info("Run command CrackMessageCmd");
        logger.info("Start initialization for cracking:");

        _algorithmType = AlgorithmType.getAlgorithm(algorithmType);
        if(_algorithmType != null) {
            logger.info("Algorithm successful recognized -> " + _algorithmType.getName());
        } else {
            logger.error("Couldn't find algorithm with name: \"" + algorithmType + "\"");
            logger.error("Stopping decryption...");
            logger.setLogFileType(AlgorithmType.NONE, true);
            System.out.println("Cracking failed");
            return "Couldn't find algorithm with name: \"" + algorithmType + "\"";
        }

        // get key file change Logger class for this

        if(_algorithmType.equals(AlgorithmType.RSA)) _keyfile = logger.getKeyFile(message);
        if(_keyfile != null) {
            logger.info("Try to crack with key: \"" + _keyfile + "\"");
        } else {
            _keyfile = logger.getRandomRSAKeyFile();
        }

        logger.info("Start cracking:");

        String crackedMsg = null;
        TimeLimiter timeLimiter = SimpleTimeLimiter.create(Executors.newSingleThreadExecutor());
        try {
            File final_keyfile = _keyfile;
            String result = timeLimiter.callWithTimeout(new Callable<String>() {
                @Override
                public String call() {
                    String res = cryptography.crack(message, _algorithmType, final_keyfile);
                    return res;
                }
            }, 30L, TimeUnit.SECONDS);
            crackedMsg = result;
        } catch (TimeoutException | InterruptedException | ExecutionException e) {
            crackedMsg = "cracking encrypted message \"" + message + "\" failed";
            logger.error("cracking encrypted message \"" + message + "\" failed");
            logger.setLogFileType(_algorithmType, true);
        }

        if(crackedMsg != null && !crackedMsg.startsWith("cracking encrypted message")) {
            logger.info("Encrypted message: \"" + message + "\"");
            logger.info("Possible cracked messages: \"" + crackedMsg + "\"");
            logger.setLogFileType(_algorithmType, false);
            crackedMsg = "Possible cracked messages: \"" + crackedMsg + "\"";
        }
        return crackedMsg;
    }
}