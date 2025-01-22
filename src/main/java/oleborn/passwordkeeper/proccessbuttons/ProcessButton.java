package oleborn.passwordkeeper.proccessbuttons;

import jakarta.annotation.Resource;
import oleborn.passwordkeeper.auth.AuthService;
import oleborn.passwordkeeper.encoding.EncodingService;
import oleborn.passwordkeeper.model.AuthUser;
import oleborn.passwordkeeper.model.DataInFile;
import oleborn.passwordkeeper.model.UserSession;
import oleborn.passwordkeeper.model.UserWithRole;
import oleborn.passwordkeeper.proccessfile.ProcessingFile;
import oleborn.passwordkeeper.util.RoleAuth;
import org.springframework.stereotype.Component;

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

    /**
     * Обрабатывает действие нажатия кнопки входа.
     * Если файл пользователя уже существует, выполняется попытка авторизации.
     * Если файл отсутствует, создается новый аккаунт и сохраняется в файл.
     *
     * @param authUser Объект {@link AuthUser}, содержащий имя пользователя и пароль.
     * @return Сообщение о результате операции:
     *         - "Вы авторизованы и можете начать работу." — если авторизация прошла успешно.
     *         - "Ваш пароль не верен." — если пароль не совпадает.
     *         - "Ваш аккаунт создан и сохранен в файле по пути C:/ProgramData/PasswordKeeper/%s.enc" — если аккаунт создан.
     */
    public String processingButtonEnter(AuthUser authUser) {
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
     *         - "Вы авторизованы и можете начать работу." — если авторизация прошла успешно.
     *         - "Ваш пароль не верен." — если пароль не совпадает.
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
     *         - "Ваш аккаунт создан и сохранен в файле по пути C:/ProgramData/PasswordKeeper/%s.enc".
     */
    private String handleNewUser(AuthUser authUser) {
        setUserSession(authUser);

        processingFile.updateFile(
                DataInFile.builder()
                        .username(authUser.getName())
                        .userPassword(authUser.getUserKey())
                        .build()
        );

        return "Ваш аккаунт создан и сохранен в файле по пути C:/ProgramData/PasswordKeeper/%s.enc".formatted(authUser.getName());
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
}

