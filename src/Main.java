import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import java.util.Random;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("mypassword");

        Font font = Font.font("San Francisco", 16);

        Label label = new Label("Password Generator");
        label.setFont(font);

        HBox labelBox = new HBox(label);
        labelBox.setAlignment(Pos.CENTER);


        VBox root = new VBox(labelBox);
        root.setSpacing(0);
        root.setAlignment(Pos.CENTER);


        TextField passwordField = new TextField();
        passwordField.setFont(font);
        passwordField.setPromptText("Generated Password");
        passwordField.setEditable(false);
        passwordField.setMinWidth(350);
        passwordField.setMaxWidth(420);

        CheckBox uppercaseCheckBox = new CheckBox("Uppercase");
        CheckBox lowercaseCheckBox = new CheckBox("Lowercase");
        CheckBox numbersCheckBox = new CheckBox("Numbers");
        CheckBox specialCharsCheckBox = new CheckBox("Special Characters");

        Slider passwordLengthSlider = new Slider(8, 40, 14);
        passwordLengthSlider.setPrefWidth(200);
        Label passwordLengthLabel = new Label("Password Length: " + (int) passwordLengthSlider.getValue());

        Button generateButton = new Button("Generate Password");
        Button copyButton = new Button("Copy to Clipboard");

        Label errorLabel = new Label();
        errorLabel.setTextFill(Color.RED);

        generateButton.setOnAction(e -> {
            boolean isAtLeastOneSelected =
                    uppercaseCheckBox.isSelected() ||
                            lowercaseCheckBox.isSelected() ||
                            numbersCheckBox.isSelected() ||
                            specialCharsCheckBox.isSelected();

            if (!isAtLeastOneSelected) {
                passwordField.setText("");
                passwordField.setPromptText("Generated Password");
                errorLabel.setText("Choose at least 1 option to generate a password");
            } else {
                errorLabel.setText("");
                String password = generatePassword(
                        (int) passwordLengthSlider.getValue(),
                        uppercaseCheckBox.isSelected(),
                        lowercaseCheckBox.isSelected(),
                        numbersCheckBox.isSelected(),
                        specialCharsCheckBox.isSelected()
                );
                passwordField.setText(password);
                passwordField.setPromptText("");
            }
        });

        copyButton.setOnAction(e -> {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(passwordField.getText());
            clipboard.setContent(content);
        });

        passwordLengthSlider.valueProperty().addListener((observable, oldValue, newValue) -> passwordLengthLabel.setText("Password Length: " + newValue.intValue()));

        HBox optionsBox = new HBox(10, uppercaseCheckBox, lowercaseCheckBox, numbersCheckBox, specialCharsCheckBox);
        HBox lengthBox = new HBox(5, passwordLengthSlider, passwordLengthLabel);
        HBox buttonBox = new HBox(5, generateButton, copyButton);

        optionsBox.setAlignment(Pos.CENTER);
        lengthBox.setAlignment(Pos.CENTER);
        buttonBox.setAlignment(Pos.CENTER);

        VBox controls = new VBox(optionsBox, lengthBox, buttonBox, errorLabel);
        controls.setSpacing(10);
        controls.setAlignment(Pos.CENTER);
        passwordField.setAlignment(Pos.CENTER);

        VBox layout = new VBox(root, passwordField, controls);
        layout.setSpacing(20);
        layout.setAlignment(Pos.CENTER);

        uppercaseCheckBox.setSelected(true);
        lowercaseCheckBox.setSelected(true);
        numbersCheckBox.setSelected(true);

        Scene scene = new Scene(layout, 500, 400);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static String generatePassword(int length, boolean useUppercase, boolean useLowercase, boolean useNumbers, boolean useSpecialCharacters) {
        String uppercaseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowercaseChars = "abcdefghijklmnopqrstuvwxyz";
        String numberChars = "0123456789";
        String specialChars = "!@#$&*?_-";

        StringBuilder allChars = new StringBuilder();
        if (useUppercase) allChars.append(uppercaseChars);
        if (useLowercase) allChars.append(lowercaseChars);
        if (useNumbers) allChars.append(numberChars);
        if (useSpecialCharacters) allChars.append(specialChars);

        Random random = new Random();
        StringBuilder passwordBuilder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(allChars.length());
            char randomChar = allChars.charAt(randomIndex);
            passwordBuilder.append(randomChar);
        }

        return passwordBuilder.toString();
    }
}