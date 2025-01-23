package oleborn.passwordkeeper.aspects;

import jakarta.annotation.Resource;
import oleborn.passwordkeeper.annotations.NotNullCustom;
import oleborn.passwordkeeper.controllers.MainController;
import oleborn.passwordkeeper.exception.NullParameterException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;

@Aspect
@Component
public class NotNullCustomAspect {

    @Resource
    private MainController mainController;

    @Before("@annotation(oleborn.passwordkeeper.annotations.NotNullCustom) || execution(* *(.., @oleborn.passwordkeeper.annotations.NotNullCustom (*), ..))")
    public void checkNotNullParameters(JoinPoint joinPoint) {
        // Получаем сигнатуру метода
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Parameter[] parameters = signature.getMethod().getParameters();
        Object[] args = joinPoint.getArgs();

        // Проверяем каждый параметр
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].isAnnotationPresent(NotNullCustom.class)) {
                // Проверка на null и пустую строку
                if (args[i] == null || (args[i] instanceof String && ((String) args[i]).isEmpty())) {
                    mainController.appendToConsole (
                            "Поле " + parameters[i].getName() + " не может быть пустым.");
                    throw new IllegalArgumentException();
                }
                // Если параметр — это объект, проверяем его поля
                checkNotNullFields(args[i]);
            }
        }
    }

    private void checkNotNullFields(Object object) {
        // Получаем все поля объекта
        for (Field field : object.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(NotNullCustom.class)) {
                field.setAccessible(true); // Разрешаем доступ к приватным полям
                try {
                    Object value = field.get(object);
                    // Проверка на null и пустую строку
                    if (value == null || (value instanceof String && ((String) value).isEmpty())) {
                        mainController.appendToConsole (
                                "Поле '" + field.getName() + "' в объекте " + object.getClass().getSimpleName() + " не может быть null или пустой строкой."
                        );
                        throw new IllegalArgumentException();
                    }
                } catch (IllegalAccessException e) {
                    mainController.appendToConsole ("Ошибка при проверке поля: " + field.getName());
                    throw new IllegalArgumentException();
                }
            }
        }
    }
}