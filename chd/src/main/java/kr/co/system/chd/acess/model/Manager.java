package kr.co.system.chd.acess.model;

import java.io.Serializable;

public class Manager implements Serializable {
    String id;
    String password;

    public Manager() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
