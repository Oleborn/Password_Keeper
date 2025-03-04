package oleborn.passwordkeeper.proccessbuttons;

import jakarta.annotation.Resource;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import oleborn.passwordkeeper.annotations.NotNullCustom;
import oleborn.passwordkeeper.auth.AuthService;
import oleborn.passwordkeeper.encoding.EncodingService;
import oleborn.passwordkeeper.model.*;
import oleborn.passwordkeeper.proccessfile.ProcessingFile;
import oleborn.passwordkeeper.util.RoleAuth;
import org.springframework.stereotype.Component;

import java.util.*;

import static oleborn.passwordkeeper.util.UtilsMethods.handleButton;

/**
 * Класс, отвечающий за обработку действий, связанных с кнопкой входа (авторизации/регистрации).
 * В зависимости от состояния файла пользователя (существует или нет), выполняет авторизацию
 * или создание нового аккаунта.
 */
@Component
public class ProcessButton {

    @Resource
    private AuthService authService;

    @Resource
    private ProcessingFile processingFile;

    @Resource
    private UserSession userSession;

    @Resource
    private EncodingService encodingService;

    private boolean isDeleteConfirmed = false; // Флаг для отслеживания подтверждения

    /**
     * Обрабатывает действие нажатия кнопки входа.
     * Если файл пользователя уже существует, выполняется попытка авторизации.
     * Если файл отсутствует, создается новый аккаунт и сохраняется в файл.
     *
     * @return Сообщение о результате операции:
     * - "Вы авторизованы и можете начать работу." — если авторизация прошла успешно.
     * - "Ваш пароль не верен." — если пароль не совпадает.
     * - "Ваш аккаунт создан и сохранен в файле по пути C:/ProgramData/PasswordKeeper/%s.enc" — если аккаунт создан.
     */
    public String processingButtonEnter(
            @NotNullCustom String login,
            @NotNullCustom String password
    ) {
        AuthUser authUser = new AuthUser(login, password);
        // Проверяем, существует ли файл
        boolean isFileExists = !processingFile.ensureDirectoryExists(authUser.getName());

        // Выбираем стратегию в зависимости от существования файла
        return isFileExists
                ? handleExistingUser(authUser)
                : handleNewUser(authUser);
    }

    /**
     * Обрабатывает авторизацию существующего пользователя.
     * Проверяет корректность пароля и устанавливает сессию пользователя.
     *
     * @param authUser Объект {@link AuthUser}, содержащий имя пользователя и пароль.
     * @return Сообщение о результате операции:
     * - "Вы авторизованы и можете начать работу." — если авторизация прошла успешно.
     * - "Ваш пароль не верен." — если пароль не совпадает.
     */
    private String handleExistingUser(AuthUser authUser) {
        authService.authUser(authUser);
        setUserSession(authUser);
        return "Вы авторизованы и можете начать работу.";
    }

    /**
     * Обрабатывает создание нового пользователя.
     * Устанавливает сессию пользователя и сохраняет данные в файл.
     *
     * @param authUser Объект {@link AuthUser}, содержащий имя пользователя и пароль.
     * @return Сообщение о результате операции:
     * - "Ваш аккаунт создан и сохранен в файле по пути C:/ProgramData/PasswordKeeper/%s.enc".
     */
    private String handleNewUser(AuthUser authUser) {
        setUserSession(authUser);

        processingFile.updateFile(
                DataInFile.builder()
                        .username(authUser.getName())
                        .userPassword(authUser.getUserKey())
                        .build()
        );

        return "Ваш аккаунт создан и сохранен в файле по пути C:/ProgramData/PasswordKeeper/%s.enc, и вы можете начать работу".formatted(authUser.getName());
    }

    /**
     * Устанавливает сессию пользователя.
     * Создает объект {@link UserWithRole} на основе данных пользователя и сохраняет его в {@link UserSession}.
     *
     * @param authUser Объект {@link AuthUser}, содержащий имя пользователя и пароль.
     */
    private void setUserSession(AuthUser authUser) {
        userSession.setUser(
                new UserWithRole(
                        authUser.getName(),
                        encodingService.generateKeyFromPassword(authUser.getUserKey()),
                        RoleAuth.AUTHENTICATED
                )
        );
    }


    public String processingButtonRelog() {
        userSession.clearUser();
        return "Вы вышли из своего аккаунта";
    }

    public void processingButtonExit() {
        System.exit(0);
    }

    public String processingButtonAdded(
            @NotNullCustom String url,
            @NotNullCustom String login,
            @NotNullCustom String password) {
        UrlData urlData = new UrlData(url, login, password);
        DataInFile dataInFile = processingFile.readFile(userSession.getUser().getName());
        HashMap<String, Set<Credentials>> data = dataInFile.getData();

        // Используем computeIfAbsent для добавления нового URL, если его нет
        Set<Credentials> credentialsSet = data.computeIfAbsent(
                urlData.getUrl(),
                k -> new HashSet<>()
        );

        // Добавляем новые учетные данные (дубликаты будут автоматически исключены)
        boolean add = credentialsSet.add(new Credentials(urlData.getLoginUrl(), urlData.getPasswordUrl()));

        // Обновляем файл
        processingFile.updateFile(dataInFile);

        if (add) {
            return """
                    Вы успешно внесли новые данные.
                    
                    Адрес сайта:    %s
                    Логин на сайте  %s
                    Пароль:         %s
                    """.formatted(
                    urlData.getUrl(),
                    urlData.getLoginUrl(),
                    urlData.getPasswordUrl()
            );
        } else {
            return """
                    Логин и пароль которые Вы пытаетесь внести для url %s, уже есть, добавление не удалось.
                    """.formatted(
                    urlData.getUrl()
            );
        }
    }

    public String processingButtonSearch(String url, String login, String password) {
        DataInFile dataInFile = processingFile.readFile(userSession.getUser().getName());
        HashMap<String, Set<Credentials>> data = dataInFile.getData();
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, Set<Credentials>> entry : data.entrySet()) {
            String key = entry.getKey();
            Set<Credentials> credentialsSet = entry.getValue();

            // Проверка на соответствие URL, если он указан
            if (url == null || url.isEmpty() || key.contains(url)) {
                for (Credentials cred : credentialsSet) {
                    // Проверка на соответствие логина и пароля, если они указаны
                    if ((login == null || login.isEmpty() || cred.getLoginUrl().contains(login)) &&
                            (password == null || password.isEmpty() || cred.getPasswordUrl().contains(password))) {
                        result.append("URL: ").append(key).append("\n");
                        result.append("Login: ").append(cred.getLoginUrl()).append("\n");
                        result.append("Password: ").append(cred.getPasswordUrl()).append("\n");
                        result.append("-------------------\n");
                    }
                }
            }
        }

        if (result.isEmpty()) {
            return "Увы, ничего из введенного вами не найдено.";
        }

        return result.toString();
    }

//    public List<HBox> processingButtonSearch(String url, String login, String password) {
//        DataInFile dataInFile = processingFile.readFile(userSession.getUser().getName());
//        HashMap<String, Set<Credentials>> data = dataInFile.getData();
//
//        List<HBox> hBoxes = new ArrayList<>();
//
//        for (Map.Entry<String, Set<Credentials>> entry : data.entrySet()) {
//            String key = entry.getKey();
//            Set<Credentials> credentialsSet = entry.getValue();
//
//            // Проверка на соответствие URL, если он указан
//            if (url == null || url.isEmpty() || key.contains(url)) {
//                for (Credentials cred : credentialsSet) {
//                    // Проверка на соответствие логина и пароля, если они указаны
//                    if ((login == null || login.isEmpty() || cred.getLoginUrl().contains(login)) &&
//                            (password == null || password.isEmpty() || cred.getPasswordUrl().contains(password))) {
//
//                        // Формируем текст для отображения
//                        String message =
//                                "URL: " + key + " "+
//                                "Login: " + cred.getLoginUrl() +" "+
//                                "Password: " + cred.getPasswordUrl()+" ";
//
//                        // Создаем HBox для размещения текста и кнопок
//                        HBox hbox = new HBox(5); // 5 - это отступ между элементами
//
//                        // Создаем текст
//                        Text textNode = new Text(message);
//
//                        // Создаем кнопку редактирования
//                        Button editButton = new Button("Редактировать");
//                        editButton.setStyle("-fx-font-size: 8px;");
//                        editButton.setOnAction(event -> {
//                            System.out.println("Редактировать: " + message);
//                            // Логика редактирования
//                            // Например, открыть диалоговое окно для редактирования
//                        });
//
//                        // Создаем кнопку удаления
//                        Button deleteButton = new Button("Удалить");
//                        deleteButton.setStyle("-fx-font-size: 8px;");
//                        deleteButton.setOnAction(event -> {
//
//                            // Логика удаления
//                            // Например, удалить запись из данных и обновить интерфейс
//                        });
//
//                        // Добавляем текст и кнопки в HBox
//                        hbox.getChildren().addAll(textNode, editButton, deleteButton);
//
//                        hBoxes.add(hbox);
//                    }
//                }
//            }
//        }
//        return hBoxes;
//    }

    public String processingButtonDelete(Button deleteButton) {
        if (!isDeleteConfirmed) {
            // Первое нажатие: выводим сообщение и устанавливаем флаг
            isDeleteConfirmed = true;
            return """
                    Вы уверены?
                    
                    Повторное нажатие удалит ваш аккаунт безвозвратно.
                    """;
        } else {
            // Второе нажатие: выполняем удаление и сбрасываем флаг
            processingFile.deleteFile(userSession.getUser().getName());
            userSession.clearUser();
            isDeleteConfirmed = false; // Сбрасываем флаг
            handleButton(deleteButton, true);
            return "Вы удалили свой аккаунт.";
        }
    }
}

