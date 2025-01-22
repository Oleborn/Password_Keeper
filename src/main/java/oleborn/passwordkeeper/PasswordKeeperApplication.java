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
    }

    @Override
    public void stop() {
        // Закрытие Spring-контекста при завершении приложения
        springContext.close();
    }
}

