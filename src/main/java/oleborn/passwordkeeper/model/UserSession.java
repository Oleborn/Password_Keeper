package oleborn.passwordkeeper.model;

import org.springframework.stereotype.Component;

@Component
public class UserSession {
    private UserWithRole user;

    public UserWithRole getUser() {
        return user;
    }

    public void setUser(UserWithRole user) {
        this.user = user;
    }

    public void clearUser() {
        this.user = null;
    }
}