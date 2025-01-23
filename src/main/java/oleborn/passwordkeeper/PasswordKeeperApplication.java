package oleborn.passwordkeeper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
/*
Что добавим:

 - очищение текстового поля со временем
 - рассмотреть варианты удаления и редактирования логинов паролей
 - сделать текст в консоли как будто программа его набирает а не мгновенно добавлять

   ------------------------------------------------------------------
 - после авторизации блокировать поля логин и пароль для ввода, до нажатия кнопки релог - сделано
 - при релоге очищать все текстовые поля - сделано
 - не давать вводить урл и тд в поля до авторизации - сделано
 - сделать проверку на пустые поля через аннотации для всех полей - сделано
 - запретить введение одинаковых данных, в урл - сделано

 */
@SpringBootApplication
@EnableAspectJAutoProxy
public class PasswordKeeperApplication extends Application {

    private ConfigurableApplicationContext springContext;
    private Parent root;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        // Запуск Spring-контекста
        springContext = SpringApplication.run(PasswordKeeperApplication.class);
        // Загрузка FXML с использованием Spring-контекста
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/PasswordKeeper.fxml"));
        loader.setControllerFactory(springContext::getBean); // Использование Spring для создания контроллеров
        root = loader.load();
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Password Keeper");
        stage.setResizable(false);
        stage.setScene(new Scene(root, 800, 510));
        stage.show();
        // Обработчик события закрытия окна
        stage.setOnCloseRequest(event -> {
            System.exit(0); // Завершение JVM
        });
    }
}

