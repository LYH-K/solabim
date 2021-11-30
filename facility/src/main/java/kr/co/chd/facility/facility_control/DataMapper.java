package kr.co.chd.facility.facility_control;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DataMapper {
    public CropData selectAll();
}
