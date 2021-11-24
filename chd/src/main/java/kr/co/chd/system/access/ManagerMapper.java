package kr.co.chd.system.access;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ManagerMapper {
    public int select(Manager manager);
}
