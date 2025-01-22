package oleborn.passwordkeeper.model;


public class AuthUser {
    private String name;
    private String userKey;

    public AuthUser(String name, String userKey) {
        this.name = name;
        this.userKey = userKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }
}
