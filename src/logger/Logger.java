package logger;

import configuration.Configuration;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Logger implements ILogger {
    private File logFile;
    private FileWriter fileWriter;
    private BufferedWriter bufferedWriter;

    public Logger(MethodType methodType, AlgorithmType algorithmType) {
        if(!methodType.getType().equals("none")) {
            String fileName = Configuration.instance.logDirectory +
                    methodType.getType() +
                    "_" +
                    algorithmType.getType() +
                    "_" +
                    System.currentTimeMillis() / 1000L +
                    ".txt";
            logFile = new File(fileName);
            try {
                fileWriter = new FileWriter(logFile, true);
                bufferedWriter = new BufferedWriter(fileWriter);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
            bufferedWriter.write(new SimpleDateFormat("dd.MM HH:mm:ss.SSS").format(new Date(System.currentTimeMillis())) + " | " + message);
            bufferedWriter.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void info(String message) {
        log(Level.INFO.getName() + " | " + message);
    }

    @Override
    public void error(String message) {
        log(Level.ERROR.getName() + " | " + message);
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

    private void close() {
        try {
            bufferedWriter.flush();
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setLogFileType(AlgorithmType algorithmType, boolean error) {
        try {
            close();
            File tempFile;
            if(!error) tempFile = new File(logFile.getName().replace("none", algorithmType.getType()));
            else tempFile = new File("error_" + logFile.getName().replace("none", algorithmType.getType()));
            Files.move(logFile.toPath(), logFile.toPath().resolveSibling(tempFile.toPath()));
            logFile = tempFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
