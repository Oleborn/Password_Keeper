package oleborn.passwordkeeper.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ConsoleEntry {
    private final StringProperty text;
    private final StringProperty actions;

    public ConsoleEntry(String text, String actions) {
        this.text = new SimpleStringProperty(text);
        this.actions = new SimpleStringProperty(actions);
    }

    public String getText() {
        return text.get();
    }

    public StringProperty textProperty() {
        return text;
    }

    public void setText(String text) {
        this.text.set(text);
    }

    public String getActions() {
        return actions.get();
    }

    public StringProperty actionsProperty() {
        return actions;
    }

    public void setActions(String actions) {
        this.actions.set(actions);
    }
}