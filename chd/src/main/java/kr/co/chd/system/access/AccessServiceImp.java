package kr.co.chd.system.access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccessServiceImp implements AccessService {
    @Autowired
    private ManagerMapper managerMapper;

    @Override
    public void login(Manager manager) {
        if(managerMapper.selectAll(manager) == 1){
            System.out.print(manager.getId());
        } else {
            System.out.print("bad");
        }
    }

    @Override
    public void logout() {

    }

    @Override
    public boolean serachManager(Manager manager) {
        return false;
    }
}
