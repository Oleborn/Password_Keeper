package oleborn.passwordkeeper.controllers;

import jakarta.annotation.Resource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import oleborn.passwordkeeper.proccessbuttons.ProcessButton;
import org.springframework.stereotype.Component;

import java.util.List;

import static oleborn.passwordkeeper.util.UtilsMethods.*;

@Component
public class MainController {

    @Resource
    private ProcessButton processButton;

    @FXML
    private Button enterButton; // Связываем кнопку с fx:id="enterButton"

    @FXML
    private Button addedButton; // Связываем кнопку с fx:id="enterButton"

    @FXML
    private Button searchButton; // Связываем кнопку с fx:id="enterButton"

    @FXML
    private TextField loginField; // Поле для логина

    @FXML
    private TextField passwordField; // Поле для пароля

    @FXML
    private TextFlow consoleArea; // Текстовая область для вывода результата

    @FXML
    public Button relogButton; // Текстовая область для выхода с аккаунта

    @FXML
    private Button deleteButton; // Текстовая область для выключения приложения

    @FXML
    private TextField urlField; // Поле для логина

    @FXML
    private TextField passwordUrlField; // Поле для пароля

    @FXML
    private TextField loginUrlField; // Поле для пароля


    @FXML
    private void initialize() {

        handleFields(List.of(urlField, loginUrlField, passwordUrlField), true);
        handleButtons(List.of(addedButton, searchButton, relogButton, deleteButton), true);
    }


    @FXML
    private void handleEnterButtonAction() {
        String result = processButton.processingButtonEnter(
                loginField.getText(),
                passwordField.getText()
        );

        handleButton(enterButton, true);
        handleFields(List.of(loginField, passwordField), true);
        handleFields(List.of(urlField, loginUrlField, passwordUrlField), false);
        handleButtons(List.of(addedButton, searchButton, relogButton, deleteButton), false);

        appendToConsole(result);
    }

    @FXML
    private void handleRelogButtonAction() {
        String result = processButton.processingButtonRelog();

        handleButton(enterButton, false);
        handleFields(List.of(loginField, passwordField), false);
        handleFields(List.of(urlField, loginUrlField, passwordUrlField), true);
        handleButtons(List.of(addedButton, searchButton, relogButton), true);
        loginField.clear();
        passwordField.clear();
        loginUrlField.clear();
        passwordUrlField.clear();
        urlField.clear();
        consoleArea.getChildren().clear();

        appendToConsole(result);
    }

    @FXML
    private void handleExitButtonAction() {
        processButton.processingButtonExit();
    }


    // Метод для добавления текста в TextArea
    public void appendToConsole(String message) {
        Text text = new Text(message+ "\n");
        consoleArea.getChildren().add(text);
        //consoleArea.appendText(message + "\n"); // Добавляем текст и перенос строки
    }

    public void handleAddedButtonAction() {
        String result = processButton.processingButtonAdded(
                urlField.getText(),
                loginUrlField.getText(),
                passwordUrlField.getText()
        );
        urlField.clear();
        loginUrlField.clear();
        passwordUrlField.clear();
        appendToConsole(result);
    }

    public void handleSearchButtonAction() {
        String result = processButton.processingButtonSearch(
                urlField.getText(),
                loginUrlField.getText(),
                passwordUrlField.getText()
        );
        urlField.clear();
        loginUrlField.clear();
        passwordUrlField.clear();
        consoleArea.getChildren().clear();
        appendToConsole(result);
    }

    public void handleDeleteButtonAction() {
        String result = processButton.processingButtonDelete(deleteButton);

        handleButton(enterButton, false);
        handleFields(List.of(loginField, passwordField), false);
        handleFields(List.of(urlField, loginUrlField, passwordUrlField), true);
        handleButtons(List.of(addedButton, searchButton, relogButton), true);
        loginField.clear();
        passwordField.clear();
        loginUrlField.clear();
        passwordUrlField.clear();
        urlField.clear();
        consoleArea.getChildren().clear();

        appendToConsole(result);
    }
}
