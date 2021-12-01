package kr.co.chd.system.analysis_management;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CropAnalysisMapper {
    public int insert(CropAnalysis cropAnalysis);
    public List<CropAverage> selectAll();//하루 별 평균 정보를 가져온다.
    public List<CropAverage> select(String date);//특정 날짜 평균 정보를 가져온다.
    public List<CropAverage> selectByNo(int no);//피이지 별 평균 정보를 가져온다.
    public List<CropInfo> selectByDate(String date); //해당 날짜의 시간별 정보를 가져온다
    public void request(CropAnalysis cropAnalysis);
    public CropData selectRGB();
}
