package oleborn.passwordkeeper.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.FIELD}) // Аннотация применяется к параметрам и полям
@Retention(RetentionPolicy.RUNTIME) // Аннотация доступна во время выполнения
public @interface NotNullCustom {
}