package logger;

import configuration.Configuration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Logger implements ILogger {
    private final File logFile;

    public Logger(MethodType methodType, AlgorithmType algorithmType) {
        String fileName = Configuration.instance.logDirectory +
                methodType.getType() +
                "_" +
                algorithmType.getType() +
                "_" +
                System.currentTimeMillis() / 1000L +
                ".txt";
        logFile = new File(fileName);

    }

    @Override
    public void log(String message) {
        if(!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileWriter fileWriter = new FileWriter(logFile);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(message);
            printWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getLastLogContent() {
        File lastLog = getLastLogFile();
        String content = readFile(lastLog);
        return content;
    }

    private File getLastLogFile() {
        File[] logFiles = new File(Configuration.instance.logDirectory).listFiles();
        long max = 0;
        File lastFile = null;

        if (logFiles != null) {
            for(File file : logFiles) {
                Matcher unixTimeMatcher = Pattern.compile("([0-9]+)").matcher(file.getName());
                while (unixTimeMatcher.find()) {
                    long unixTime = Long.parseLong(unixTimeMatcher.group());
                    if(unixTime > max) {
                        lastFile = file;
                        max = unixTime;
                    }
                }
            }
        }
        return lastFile;
    }


    private String readFile(File file) {
        StringBuilder fileContent = new StringBuilder();
        try {
            Files.lines(file.toPath(), StandardCharsets.UTF_8).forEach(l -> fileContent.append(l).append("\n"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent.toString();
    }

}
