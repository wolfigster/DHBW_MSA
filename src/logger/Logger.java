package logger;

import configuration.Configuration;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Logger implements ILogger {
    private File logFile;
    private FileWriter fileWriter;
    private BufferedWriter bufferedWriter;

    public Logger(MethodType methodType, AlgorithmType algorithmType) {
        if(!methodType.getType().equals("none") && Configuration.instance.debug) {
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
        if(Configuration.instance.debug) {
            if (!logFile.exists()) {
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
        if(Configuration.instance.debug) {
            try {
                close();
                File tempFile;
                if (!error) tempFile = new File(logFile.getName().replace("none", algorithmType.getType()));
                else tempFile = new File("error_" + logFile.getName().replace("none", algorithmType.getType()));
                Files.move(logFile.toPath(), logFile.toPath().resolveSibling(tempFile.toPath()));
                logFile = tempFile;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public File getKeyFile(String encryptedMessage) {
        File[] logFiles = new File(Configuration.instance.logDirectory).listFiles();
        File _keyfile = null;

        if (logFiles != null) {
            for(File file : logFiles) {
                try {
                    for(String line : Files.readAllLines(file.toPath(), StandardCharsets.UTF_8)) {
                        if(line.matches(".* Found keyfile: \".*\"")) _keyfile = new File(Configuration.instance.keyfileDirectory + line.split("\"")[1]);
                        if(line.matches(".* encrypted to \"" + encryptedMessage + "\"")) {
                            return _keyfile;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public File getRandomRSAKeyFile() {
        File[] logFiles = new File(Configuration.instance.keyfileDirectory).listFiles();
        boolean n = false, e = false, d = false;

        if (logFiles != null) {
            List<File> fileList = Arrays.asList(logFiles);
            Collections.shuffle(fileList);
            fileList.toArray(logFiles);
            for(File file : logFiles) {
                try {
                    for(String line : Files.readAllLines(file.toPath(), StandardCharsets.UTF_8)) {
                        if(line.contains("\"n\":")) n = true;
                        if(line.contains("\"e\":")) e = true;
                        if(line.contains("\"d\":")) d = true;
                    }
                    if(n && e && d) return file;
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
        return null;
    }
}
