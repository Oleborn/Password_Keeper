package oleborn.passwordkeeper.exception;

import jakarta.annotation.Resource;
import oleborn.passwordkeeper.controllers.MainController;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class GlobalExceptionHandler {

    @Resource
    private MainController mainController;

    @AfterThrowing(pointcut = "execution(* oleborn.passwordkeeper.proccessfile.*.*(..))", throwing = "ex")
    public void handleException(RuntimeException ex) {
        mainController.appendToConsole(ex.getMessage());
    }
}
