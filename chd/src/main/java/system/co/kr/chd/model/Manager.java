package system.co.kr.chd.model;

import java.io.Serializable;

public class Manager implements Serializable {
    private String id;
    private String pw;

    public Manager(){
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

}
