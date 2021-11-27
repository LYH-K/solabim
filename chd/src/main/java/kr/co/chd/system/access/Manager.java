package kr.co.chd.system.access;

import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class Manager implements Serializable {
    private String id;
    private String password;

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
