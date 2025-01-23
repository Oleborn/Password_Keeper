package oleborn.passwordkeeper.model;

import java.util.Objects;

public class Credentials {
    private String loginUrl;
    private String passwordUrl;

    public Credentials(String login, String password) {
        this.loginUrl = login;
        this.passwordUrl = password;
    }

    public Credentials() {}

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getPasswordUrl() {
        return passwordUrl;
    }

    public void setPasswordUrl(String passwordUrl) {
        this.passwordUrl = passwordUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Credentials that = (Credentials) o;
        return Objects.equals(loginUrl, that.loginUrl) && Objects.equals(passwordUrl, that.passwordUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loginUrl, passwordUrl);
    }
}