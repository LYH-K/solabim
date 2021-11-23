package kr.co.system.chd.acess.persistence;

import org.apache.ibatis.annotations.Mapper;
import kr.co.system.chd.acess.model.Manager;

@Mapper
public interface ManagerMapper {
    public int select(Manager manager);
}
