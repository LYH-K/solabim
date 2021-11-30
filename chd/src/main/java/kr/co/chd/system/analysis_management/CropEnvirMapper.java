package kr.co.chd.system.analysis_management;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CropEnvirMapper {
    public int insert(CropEnvirInfo cropEnvirInfo);
    public void request(CropEnvirInfo cropEnvirInfo);
}
