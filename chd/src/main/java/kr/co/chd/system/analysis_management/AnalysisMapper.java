package kr.co.chd.system.analysis_management;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AnalysisMapper {
    public int insert(CropAnalysis cropAnalysis);
    public List<CropAverage> selectByDate();
    public List<CropInfo> selectByTime(String time);
    public void request(CropAnalysis cropAnalysis);
}
