package oleborn.passwordkeeper.util;

public enum UtilsEnum {
    
    FILEPATH("C:/ProgramData/PasswordKeeper/%s.enc");



    private final String string;

    UtilsEnum(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }
}