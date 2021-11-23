package kr.co.system.chd.analysisManagement.persistence;

import org.apache.ibatis.annotations.Mapper;
import kr.co.system.chd.analysisManagement.model.*;

import java.util.List;

@Mapper
public interface CropAnalysisMapper {
    public int insert(CropAnalysis cropAnalysis);
    public List<CropAverage> selectByDate();
    public List<CropInfo> selectByTime();
    public void request(CropAnalysis cropAnalysis);
}
