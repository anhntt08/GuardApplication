package safeobject.model;

import java.io.Serializable;

public class UserDTO implements Serializable {
    private int id;
    private String username;
    private String password;
    private boolean enable;
    private RoleDTO roles;

    public UserDTO() {
    }

    public UserDTO(int id, String username, String password, boolean enable, RoleDTO role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.enable = true;
        this.roles = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public RoleDTO getRole() {
        return roles;
    }

    public void setRole(RoleDTO role) {
        this.roles = role;
    }
}
