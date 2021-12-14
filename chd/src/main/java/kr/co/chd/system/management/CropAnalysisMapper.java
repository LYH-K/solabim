package kr.co.chd.system.management;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CropAnalysisMapper {
    public void insert(CropAnalysis cropAnalysis);                    //농작물 분석 정보 등록
    public List<CropAverage> selectAll();                            //하루 별 평균 정보 조회
    public List<CropAverage> select(Map<String, String> condition);  //특정 날짜 평균 정보 조회
    public List<CropAverage> selectInfo();                           //최근 10일 날짜 평균 정보 조회
    public List<CropAverage> selectByNo(int no);                     //페이지 별 평균 정보를 가져온다.
    public List<CropInfo> selectByDate(String date);                 //해당 날짜의 시간별 정보 조회
    public CropData selectRGB();                                     //농작물 RGB값 조회
    public CropAnalysis selectImage(int no);                         //이미지 조회
    public int countBoard();                                         //날짜 별 농작물 총 개수 조회
}
