package oleborn.passwordkeeper.auth;

import oleborn.passwordkeeper.model.AuthUser;

public interface AuthService {
    void authUser(AuthUser user);
    /*
    Вообще каждый user хранится в отдельном файле, который в определенном месте системы
    имя файла - логин
    Логика авторизации:
        - человек вводит логин и пароль
        - приложение проверяет есть ли файл с таким именем
        - если есть декодирует, проверяет пароль если совпадает, в логах выход приветствие
        - устанавливается роль авторизован, держится до конца работы приложения или до нажатия Relog
        - если нет, то создает файл, кодирует пароль и записывает в файл
     */
}
