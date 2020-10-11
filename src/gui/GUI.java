package gui;

import command.CommandControl;
import command.ICommand;
import configuration.Configuration;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logger.AlgorithmType;
import logger.Logger;
import logger.MethodType;

public class GUI extends Application {

    private final Logger logger = new Logger(MethodType.NONE, AlgorithmType.NONE);
    private CommandControl commandControl;
    private TextArea commandLineArea;
    private TextArea outputArea;

    public void start(Stage primaryStage) {
        primaryStage.setTitle("MSA | Mosbach Security Agency");

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(15, 12, 15, 12));
        hBox.setSpacing(10);
        hBox.setStyle("-fx-background-color: #336699;");

        Button executeButton = new Button("Execute");
        executeButton.setPrefSize(100, 20);

        Button closeButton = new Button("Close");
        closeButton.setPrefSize(100, 20);

        executeButton.setOnAction(event -> keyPressed(KeyCode.F5));

        closeButton.setOnAction(actionEvent -> System.exit(0));

        commandLineArea = new TextArea();
        commandLineArea.setWrapText(true);

        outputArea = new TextArea();
        outputArea.setWrapText(true);
        outputArea.setEditable(false);

        hBox.getChildren().addAll(executeButton, closeButton);

        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(25, 25, 25, 25));
        vbox.getChildren().addAll(hBox, commandLineArea, outputArea);

        Scene scene = new Scene(vbox, 950, 500);
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (keyEvent -> keyPressed(keyEvent.getCode())));
        primaryStage.setScene(scene);
        primaryStage.show();
        commandControl = new CommandControl(outputArea);
    }

    private void keyPressed(KeyCode keyCode) {
        switch (keyCode) {
            case F3:
                // De-/Activate Debug-Mode
                System.out.println("De-/Activate Debug-Mode");
                Configuration.instance.debug = !Configuration.instance.debug;
                outputArea.setText(Configuration.instance.debug ? "Activated Debug-Mode" : "Deactivated Debug-Mode");
                break;
            case F5:
                // Execute command from InputArea of GUI
                System.out.println("Execute command from InputArea of GUI");
                ICommand command = commandControl.matchCommand(commandLineArea.getText());
                if(command != null) {
                    commandControl.executeCommand();
                } else {
                    outputArea.setText("No suitable command was found for the following entry:");
                    outputArea.appendText("\n" + commandLineArea.getText());
                }
                break;
            case F8:
                // Print newest Logs in OutputArea of GUI
                System.out.println("Print newest Logs in OutputArea of GUI");
                outputArea.setText(logger.getLastLogContent());
                break;
        }
    }
}