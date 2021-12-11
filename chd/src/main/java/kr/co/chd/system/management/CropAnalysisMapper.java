package kr.co.chd.system.management;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CropAnalysisMapper {
    public int insert(CropAnalysis cropAnalysis);
    public List<CropAverage> selectAll(Map<String, String> condition);//하루 별 평균 정보를 가져온다.
    public List<CropAverage> select();//특정 날짜 평균 정보를 가져온다.
    public List<CropAverage> selectInfo();
    public List<CropAverage> selectByNo(int no);// 페이지 별 평균 정보를 가져온다.
    public List<CropInfo> selectByDate(String date); //해당 날짜의 시간별 정보를 가져온다
    public CropData selectRGB();
    public CropAnalysis selectImage(int no);
    public int countBoard();
}
