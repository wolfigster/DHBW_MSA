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

        createMethod(algorithmType, "decrypt");
        response = crypt(message, keyfile);

        return response;
    }

    public String encrypt(String message, AlgorithmType algorithmType, File keyfile) {
        String response = null;

        createMethod(algorithmType, "encrypt");
        response = crypt(message, keyfile);

        return response;
    }

    private void createMethod(AlgorithmType algorithmType, String method) {
        // LHC
        Object instance;
        try {
            URL[] urls = {
                    new File(Configuration.instance.componentDirectory + algorithmType.getType() + File.separator + "jar" + File.separator + algorithmType.getType() + ".jar").toURI().toURL()
            };
            URLClassLoader urlClassLoader = new URLClassLoader(urls, Cryptography.class.getClassLoader());
            Class clazz = Class.forName("Algorithm" + algorithmType.getName(), true, urlClassLoader);

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