package oleborn.passwordkeeper.model;
import oleborn.passwordkeeper.util.RoleAuth;

import javax.crypto.SecretKey;

public class UserWithRole{
    private String name;
    private SecretKey userKey;
    private RoleAuth roleAuth;

    public UserWithRole(String name, SecretKey userKey, RoleAuth roleAuth) {
        this.name = name;
        this.userKey = userKey;
        this.roleAuth = roleAuth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SecretKey getUserKey() {
        return userKey;
    }

    public void setUserKey(SecretKey userKey) {
        this.userKey = userKey;
    }

    public RoleAuth getRoleAuth() {
        return roleAuth;
    }

    public void setRoleAuth(RoleAuth roleAuth) {
        this.roleAuth = roleAuth;
    }
}
