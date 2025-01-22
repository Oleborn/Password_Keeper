package oleborn.passwordkeeper.controllers;

import jakarta.annotation.Resource;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import oleborn.passwordkeeper.model.AuthUser;
import oleborn.passwordkeeper.proccessbuttons.ProcessButton;
import org.springframework.stereotype.Component;

@Component
public class MainController {

    @Resource
    private ProcessButton processButton;

    @FXML
    private Button enterButton; // Связываем кнопку с fx:id="enterButton"

    @FXML
    private TextField loginField; // Поле для логина

    @FXML
    private TextField passwordField; // Поле для пароля

    @FXML
    private TextArea consoleArea; // Текстовая область для вывода результата

    // Метод, который будет вызван при нажатии на кнопку Enter
    @FXML
    private void handleEnterButtonAction() {
        String result = processButton.processingButtonEnter(
                new AuthUser(
                        loginField.getText(),
                        passwordField.getText()
                )
        );
        appendToConsole(result);
    }

    @FXML
    private void initialize() {
        // Назначаем кнопку "Enter" как кнопку по умолчанию
        enterButton.setDefaultButton(true);
    }


    // Метод для добавления текста в TextArea
    public void appendToConsole(String message) {
        consoleArea.appendText(message + "\n"); // Добавляем текст и перенос строки
    }
}
