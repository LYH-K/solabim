package kr.co.chd.facility;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DataMapper {
    public CropData selectAll();
}
