package kr.co.system.chd.acess.service;

import kr.co.system.chd.acess.model.Manager;

public interface AcessService {
    public void login(Manager manager);
    public void logout();
    public boolean serachManager(Manager manager);
}
