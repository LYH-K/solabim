package kr.co.chd.system.access;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface AccessService {
    public void login(Manager manager, HttpSession session);
    public void logout(HttpSession session);
    public boolean searchManager(Manager manager);
}
