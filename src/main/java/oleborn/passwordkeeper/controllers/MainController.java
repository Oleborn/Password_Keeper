package oleborn.passwordkeeper.controllers;

import jakarta.annotation.Resource;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import oleborn.passwordkeeper.model.ConsoleEntry;
import oleborn.passwordkeeper.proccessbuttons.ProcessButton;
import oleborn.passwordkeeper.util.Messages;
import org.springframework.stereotype.Component;

import java.util.List;

import static oleborn.passwordkeeper.util.UtilsMethods.*;

@Component
public class MainController {

    @Resource
    private ProcessButton processButton;


    @FXML
    private TableView<ConsoleEntry> consoleTable;

    @FXML
    private TableColumn<ConsoleEntry, String> textColumn;

    @FXML
    private TableColumn<ConsoleEntry, String> actionsColumn;



    @FXML
    private AnchorPane rootPane; // Ссылка на корневой элемент из FXML

    @FXML
    private TextField loginField; // Поле для логина

    @FXML
    private TextField passwordField; // Поле для пароля

    @FXML
    private TextField urlField; // Поле для логина

    @FXML
    private TextField passwordUrlField; // Поле для пароля

    @FXML
    private TextField loginUrlField; // Поле для пароля

//    @FXML
//    private TextArea consoleArea; // Текстовая область для вывода результата

    @FXML
    private VBox consoleContainer; // Контейнер для строк

    @FXML
    private Button enterButton; // Связываем кнопку с fx:id="enterButton"

    @FXML
    private Button addedButton;

    @FXML
    private Button searchButton;

    @FXML
    public Button relogButton; // Текстовая область для выхода с аккаунта

    @FXML
    private Button deleteButton; // Текстовая область для выключения приложения

    @FXML
    private Button closeAppButton;

    @FXML
    private Button minimizeAppButton;

    private double xOffset = 0; // Смещение по X
    private double yOffset = 0; // Смещение по Y


    @FXML
    private void initialize() {

        addConsoleLine(Messages.START_MS.getMessage());

        handleFields(List.of(urlField, loginUrlField, passwordUrlField), true);
        handleButtons(List.of(addedButton, searchButton, relogButton, deleteButton), true);
        appendToConsole(Messages.START_MS.getMessage());

        mouseHandler();

    }


    @FXML
    private void handleEnterButtonAction() {
        clear();
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
        clear();

        appendToConsole(result);
    }

    // Метод для добавления текста в TextArea
    public void appendToConsole(String message) {
        consoleContainer.getChildren().clear();
//        //Text text = new Text(message + "\n");
//        consoleArea.appendText(message + "\n");

        //consoleArea.getChildren().add(text);
        //consoleArea.appendText(message + "\n"); // Добавляем текст и перенос строки

        // Создаем текстовое поле
        TextArea textArea = new TextArea(message);
        textArea.setPrefRowCount(100); // Одна строка по умолчанию
        textArea.setWrapText(true); // Включаем перенос текста
        textArea.setEditable(false); // Только для чтения

        // Размещаем текст, растягивающийся элемент и кнопки в HBox
        HBox lineBox = new HBox(10, textArea);
        lineBox.setStyle("-fx-alignment: center-left;");
        // Добавляем строку в контейнер
        consoleContainer.getChildren().add(lineBox);
    }

    public void handleAddedButtonAction() {
        clear();
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
        clear();

        String s = processButton.processingButtonSearch(
                urlField.getText(),
                loginUrlField.getText(),
                passwordUrlField.getText()
        );

        urlField.clear();
        loginUrlField.clear();
        passwordUrlField.clear();
        appendToConsole(s);
    }

    public void handleDeleteButtonAction() {
        clear();
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
        appendToConsole(result);
    }

    @FXML
    private void handleCloseButtonAction() {
        Stage stage = (Stage) closeAppButton.getScene().getWindow();
        stage.close();
        processButton.processingButtonExit();
    }

    @FXML
    private void handleMinimizeButtonAction() {
        Stage stage = (Stage) minimizeAppButton.getScene().getWindow();
        stage.setIconified(true);
    }

    private void mouseHandler() {
        // Обработчик нажатия мыши
        rootPane.setOnMousePressed(event -> {
            xOffset = event.getSceneX(); // Запоминаем начальное смещение по X
            yOffset = event.getSceneY(); // Запоминаем начальное смещение по Y
        });

        // Обработчик перемещения мыши
        rootPane.setOnMouseDragged(event -> {
            Stage stage = (Stage) rootPane.getScene().getWindow(); // Получаем текущее окно
            stage.setX(event.getScreenX() - xOffset); // Устанавливаем новую позицию окна по X
            stage.setY(event.getScreenY() - yOffset); // Устанавливаем новую позицию окна по Y
        });
    }

    private void clear(){
        //consoleArea.clear();
        consoleContainer.getChildren().clear();
    }

    // Метод для добавления строки текста с кнопками
    public void addConsoleLine(String text) {
        // Создаем текстовое поле
        TextArea textArea = new TextArea(text);
        textArea.setPrefRowCount(100); // Одна строка по умолчанию
        textArea.setWrapText(true); // Включаем перенос текста
        textArea.setEditable(false); // Только для чтения
        //textArea.setStyle("-fx-font-size: 14px; -fx-background-color: transparent; -fx-border-color: transparent;");

        // Создаем кнопки
        Button editButton = new Button("Редактировать");
        Button deleteButton = new Button("Удалить");

        // Создаем "растягивающийся" элемент (Region)
        Region spacer = new Region();

        // Размещаем текст, растягивающийся элемент и кнопки в HBox
        HBox lineBox = new HBox(10, textArea, spacer, editButton, deleteButton);
        lineBox.setStyle("-fx-alignment: center-left;");

        HBox.setHgrow(textArea, javafx.scene.layout.Priority.ALWAYS);

        // Добавляем строку в контейнер
        consoleContainer.getChildren().add(lineBox);
    }
}
