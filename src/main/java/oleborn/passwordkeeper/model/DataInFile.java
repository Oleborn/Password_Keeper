package oleborn.passwordkeeper.model;

import java.util.HashMap;
import java.util.List;

public class DataInFile {
    private String username;
    private String userPassword;
    private HashMap<String, List<Credentials>> data;

    // Приватный конструктор (используется только через Builder)
    private DataInFile() {}

    // Геттеры
    public String getUsername() {
        return username;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public HashMap<String, List<Credentials>> getData() {
        return data;
    }

    // Статический метод для создания Builder
    public static Builder builder() {
        return new Builder();
    }

    // Внутренний класс Builder
    public static class Builder {
        private String username;
        private String userPassword;
        private HashMap<String, List<Credentials>> data = new HashMap<>();

        // Методы для установки полей
        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder userPassword(String userPassword) {
            this.userPassword = userPassword;
            return this;
        }

        public Builder data(HashMap<String, List<Credentials>> data) {
            this.data = data;
            return this;
        }

        public Builder addDataEntry(String key, List<Credentials> credentials) {
            this.data.put(key, credentials);
            return this;
        }

        // Метод для создания объекта DataInFile
        public DataInFile build() {
            DataInFile dataInFile = new DataInFile();
            dataInFile.username = this.username;
            dataInFile.userPassword = this.userPassword;
            dataInFile.data = this.data;
            return dataInFile;
        }
    }
}
