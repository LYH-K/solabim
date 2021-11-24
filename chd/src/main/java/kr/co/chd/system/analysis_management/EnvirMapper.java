package kr.co.chd.system.analysis_management;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EnvirMapper {
    public int insert(EnvirInfo envirInfo);
    public void request(EnvirInfo envirInfo);
}
