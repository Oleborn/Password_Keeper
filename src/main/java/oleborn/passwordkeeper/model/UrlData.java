package oleborn.passwordkeeper.model;

public class UrlData {
    private String url;
    private String loginUrl;
    private String passwordUrl;

    public UrlData(String url, String loginUrl, String passwordUrl) {
        this.url = url;
        this.loginUrl = loginUrl;
        this.passwordUrl = passwordUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

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
}
