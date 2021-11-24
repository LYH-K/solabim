package kr.co.system.chd.acess.persistence;

import org.apache.catalina.Manager;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ManagerMapper {
    public int select(Manager manager);
}
