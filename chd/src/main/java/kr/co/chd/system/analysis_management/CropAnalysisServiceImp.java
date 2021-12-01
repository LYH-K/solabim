package kr.co.chd.system.analysis_management;

import org.apache.ibatis.io.Resources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Properties;

@Service
public class CropAnalysisServiceImp implements CropAnalysisService {
    @Autowired
    private CropAnalysisMapper analysisMapper;
    @Autowired
    private CropAnalysis cropAnalysis;
    private int imageNo = 0; // 이미지 넘버 추가
    private String today = LocalDate.now().toString();//오늘 날짜
    private String path = "C:\\cropImage\\"+today;//수신한 이미지들을 저장할 폴더
    private final String propertiesPath = "spring/analysis_no.properties";
    private Properties imageNoProperties;


    //수신한 이미지의 생장률, 측면, 수직 이미지 url 등록
    @Override
    public void addCropAnalysis(CropAnalysis cropAnalysis) {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        int imageNo = 0;

        try{
            fileInputStream = new FileInputStream("C:\\Users\\sdm05\\IntelliJ\\solabim\\chd\\src\\main\\resources\\spring\\analysisno.properties");
            imageNoProperties = new Properties();
            imageNoProperties.load(fileInputStream);
            imageNo = Integer.parseInt(imageNoProperties.getProperty("imageNo"));

            System.out.println(imageNo);

            cropAnalysis.setCropSideImageURL("cropSideImage"+imageNo+".jpg");
            cropAnalysis.setCropVerticalImageURL("cropVerticleImage"+imageNo+".jpg");

            imageNo++;
            imageNoProperties.setProperty("imageNo",String.valueOf(imageNo));
            System.out.println(imageNoProperties.getProperty("imageNo"));
            fileOutputStream = new FileOutputStream("C:\\Users\\sdm05\\IntelliJ\\solabim\\chd\\src\\main\\resources\\spring\\analysisno.properties");
            imageNoProperties.store(fileOutputStream,"변경");
            fileOutputStream.flush();

            System.out.println(imageNo);

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try{
                if(fileInputStream != null){
                    fileInputStream.close();
                }
                if(fileOutputStream != null){
                    fileOutputStream.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        saveCropFacilityInfo(cropAnalysis);

        analysisMapper.insert(cropAnalysis);
    }

    //수신한 이미지를 저장
    @Override
    public void saveCropFacilityInfo(CropAnalysis CropAnalysis){
        File imagesFile = new File(path);

        //오늘 날짜에 해당하는 폴더가 없으면 생성
        if(!imagesFile.isDirectory()){
            imagesFile.mkdir();
        }

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path +"//"+ CropAnalysis.getCropSideImageURL());
            fileOutputStream.write(CropAnalysis.getCropSideImage().getBytes());//측면 이미지 저장

            FileOutputStream fileOutputStream2 = new FileOutputStream(path +"//"+ CropAnalysis.getCropVerticalImageURL());
            fileOutputStream2.write(CropAnalysis.getCropVerticalImage().getBytes());//수직 이미지 저장

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
    public List<CropAverage> searchCropList(String date) {
        List<CropAverage> cropAverageList = analysisMapper.select(date);

        return cropAverageList;
    }

    @Override
    public String predictHarvest() {
        LocalDate today = LocalDate.now();
        List<CropAverage> cropAverageList = analysisMapper.selectAll();

        int maxGrowth = cropAverageList.get(cropAverageList.size() - 1).getGrowthAvg();

        LocalDate predictDate;
        int dataAvgGrowth = maxGrowth / cropAverageList.size();
        if (maxGrowth < 70) {
            if (70 % dataAvgGrowth > 0) {
                predictDate = today.plusDays((70/dataAvgGrowth) + 1);
            } else {
                predictDate = today.plusDays(70/dataAvgGrowth);
            }
        } else {
            return today.toString();
        }

        return predictDate.toString();
    }

    //시간대별 농작물 정보 조회
    @Override
    public List<CropInfo> searchAnalysisInfo(String date) {
        List<CropInfo> cropInfoList = analysisMapper.selectByDate(date);

        return cropInfoList;
    }
}
