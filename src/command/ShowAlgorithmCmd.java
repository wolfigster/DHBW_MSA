package command;

import configuration.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ShowAlgorithmCmd implements ICommand {

    public ShowAlgorithmCmd() {
        System.out.println("ShowAlgorithmCmd was constructed");
    }

    @Override
    public String execute() {
        System.out.println("Run command ShowAlgorithmCmd");
        StringBuilder response = new StringBuilder();
        response.append("Following algorithms are available:");
        Path componentDir = Paths.get(Configuration.instance.componentDirectory);
        try (Stream<Path> stream = Files.walk(componentDir)) {
              List<String> collect = stream.map(String::valueOf).filter(f -> f.endsWith(".jar") && !f.contains("cracker")).collect(Collectors.toList());collect.forEach(f -> response.append("\n- ").append(Paths.get(f).getFileName().toString().replace(".jar", "").toUpperCase()));
           } catch (IOException e) {
            e.printStackTrace();
        }
        return response.toString();
    }
}
