package kr.co.chd.system.access;

import org.springframework.stereotype.Service;

public interface AccessService {
    public void login(Manager manager);
    public void logout();
    public boolean serachManager(Manager manager);
}
