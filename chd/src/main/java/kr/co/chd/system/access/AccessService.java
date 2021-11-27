package kr.co.chd.system.access;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

public interface AccessService {
    public void login(Manager manager, HttpSession session);

    public void logout(HttpSession session);

    public boolean searchManager(Manager manager);
}
