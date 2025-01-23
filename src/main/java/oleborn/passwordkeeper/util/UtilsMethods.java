package oleborn.passwordkeeper.util;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.util.List;

public class UtilsMethods {

    public static void clearField(TextField field) {
        field.clear();
    }

    public static TextField handleField(TextField field, boolean command) {
        field.setDisable(command);
        if (command) {
            field.clear();
        }
        return field;
    }

    public static Button handleButton(Button button, boolean command) {
        button.setDisable(command);
        return button;
    }

    public static List<TextField> handleFields(List<TextField> textFields, boolean command) {
        for (TextField textField : textFields) {
            textField.setDisable(command);
            if (command) {
                textField.clear();
            }
        }
        return textFields;
    }

    public static List<Button> handleButtons(List<Button> buttons, boolean command) {
        for (Button button : buttons) {
            button.setDisable(command);
        }
        return buttons;
    }
}
