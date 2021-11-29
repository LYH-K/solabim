package kr.co.chd.system.analysis_management;

import org.apache.ibatis.io.Resources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;

@Service
public class AnalysisServiceImp implements AnalysisService {
    @Autowired
    private AnalysisMapper analysisMapper;
    private int imageNo = 0; // 이미지 넘버 추가
    private String today = LocalDate.now().toString();//오늘 날짜
    private String path = "C:\\cropImage\\"+today;//수신한 이미지들을 저장할 폴더
    private final String imageNoInfoPath = "spring/analysisno.properties";
    private Properties imageNoInfo;


    //수신한 이미지의 생장률, 측면, 수직 이미지 url 등록
    @Override
    public void addCropAnalysis(CropAnalysis cropAnalysis) {
        try{
            InputStream inputStream = Resources.getResourceAsStream(imageNoInfoPath);
            imageNoInfo = new Properties();
            imageNoInfo.load(inputStream);
            imageNo = Integer.parseInt(imageNoInfo.getProperty("imageNo"));
            System.out.println(imageNo);

        } catch (Exception e){
            e.printStackTrace();
        }

        cropAnalysis.setCropSideImageURL("cropSideImage"+imageNo+".jpg");
        cropAnalysis.setCropVerticalImageURL("cropVerticleImage"+imageNo+".jpg");

//        imageNo++;
//        imageNoInfo.setProperty("")
//
//        imageNoInfo.store();
//
//        saveImage(cropAnalysis);

        //analysisMapper.insert(cropAnalysis);
    }

    //수신한 이미지를 저장
    @Override
    public void saveImage(CropAnalysis CropAnalysis){
        File imagesFile = new File(path);

        //오늘 날짜에 해당하는 폴더가 없으면 생성
        if(!imagesFile.isDirectory()){
            imagesFile.mkdir();
        }

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path +"//"+ CropAnalysis.getCropSideImageURL());
            fileOutputStream.write(CropAnalysis.getCropSideImage().getBytes());//측면 이미지 저장

            FileOutputStream fileOutputStream2 = new FileOutputStream(path +"//"+ CropAnalysis.getCropVerticalImageURL());
            fileOutputStream2.write(CropAnalysis.getCropSideImage().getBytes());//수직 이미지 저장

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //모든 날짜 검색
    @Override
    public List<CropAverage> searchCropList() {
        List<CropAverage> cropAverageList = analysisMapper.selectAll();

        return cropAverageList;
    }

    //날짜로 검색
    @Override
    public List<CropAverage> searchCrop(String date) {
        List<CropAverage> cropAverageList = analysisMapper.select(date);

        return cropAverageList;
    }

    @Override
    public String predictHarvest() {


        return "2021.12.01";
    }

    //시간대별 농작물 정보 조회
    @Override
    public List<CropInfo> searchAnalysisInfo(String date) {
        List<CropInfo> cropInfoList = analysisMapper.selectByDate(date);

        return cropInfoList;
    }
}
