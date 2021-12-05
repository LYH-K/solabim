package kr.co.chd.system.management;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CropEnvirMapper {
    public int insert(CropEnvirInfo cropEnvirInfo);
}
