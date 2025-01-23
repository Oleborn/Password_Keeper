package oleborn.passwordkeeper.model;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class DataInFile {
    private String username;
    private String userPassword;
    private HashMap<String, Set<Credentials>> data;

    // Приватный конструктор (используется только через Builder)
    private DataInFile() {}

    // Геттеры
    public String getUsername() {
        return username;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public HashMap<String, Set<Credentials>> getData() {
        return data;
    }

    // Статический метод для создания Builder
    public static Builder builder() {
        return new Builder();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setData(HashMap<String, Set<Credentials>> data) {
        this.data = data;
    }

    // Внутренний класс Builder
    public static class Builder {
        private String username;
        private String userPassword;
        private HashMap<String, Set<Credentials>> data = new HashMap<>();

        // Методы для установки полей
        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder userPassword(String userPassword) {
            this.userPassword = userPassword;
            return this;
        }

        public Builder data(HashMap<String, Set<Credentials>> data) {
            this.data = data;
            return this;
        }

        public Builder addDataEntry(String key, Set<Credentials> credentials) {
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
