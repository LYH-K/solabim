package kr.co.facility.facility.persistence;

import kr.co.facility.facility.model.CropData;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DataMapper {
    public CropData selectAll();
}
