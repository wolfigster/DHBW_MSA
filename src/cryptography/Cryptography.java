package cryptography;

import configuration.Configuration;
import logger.AlgorithmType;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class Cryptography {

    private Object port;
    private Method method;

    public String decrypt(String message, AlgorithmType algorithmType, File keyfile) {
        String response = null;

        createMethod(algorithmType, "decrypt", false);
        response = crypt(message, keyfile);

        return response;
    }

    public String encrypt(String message, AlgorithmType algorithmType, File keyfile) {
        String response = null;

        createMethod(algorithmType, "encrypt", false);
        response = crypt(message, keyfile);

        return response;
    }

    public String crack(String message, AlgorithmType algorithmType, File keyfile) {
        String response = null;

        createMethod(algorithmType, "crack", true);
        response = crypt(message, keyfile);

        return response;
    }

    private void createMethod(AlgorithmType algorithmType, String method, boolean cracker) {
        // LHC
        Object instance;

        String crackerString = cracker ? "_cracker" : "";
        String algorithmString = cracker ? "Cracker" : "Algorithm";

        try {
            URL[] urls = {
                    new File(Configuration.instance.componentDirectory + algorithmType.getType() + crackerString + File.separator + "jar" + File.separator + algorithmType.getType() + crackerString + ".jar").toURI().toURL()
            };
            URLClassLoader urlClassLoader = new URLClassLoader(urls, Cryptography.class.getClassLoader());
            Class clazz = Class.forName(algorithmString + algorithmType.getName(), true, urlClassLoader);

            instance = clazz.getMethod("getInstance").invoke(null);
            this.port = clazz.getDeclaredField("port").get(instance);
            this.method = port.getClass().getMethod(method, String.class, File.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String crypt(String message, File keyfile) {
        String cryptMessage = null;
        try {
            Object obj = method.invoke(port, message, keyfile);
            cryptMessage = (String) obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cryptMessage;
    }
}