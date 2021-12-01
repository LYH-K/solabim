package kr.co.chd.system.analysis_management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;

@Service
public class CropAnalysisServiceImp implements CropAnalysisService {
    @Autowired
    private CropAnalysisMapper analysisMapper;

    private String today = LocalDate.now().toString();//오늘 날짜
    private String path = "C:\\cropImage\\"+today;//수신한 이미지들을 저장할 폴더
    private final String propertiesPath = "C:\\Users\\sdm05\\IntelliJ\\solabim\\chd\\src\\main\\resources\\spring\\analysisno.properties";
    private Properties imageNoProperties;

    //수신한 이미지의 생장률, 측면, 수직 이미지 url 등록
    @Override
    public void addCropAnalysis(CropAnalysis cropAnalysis) {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        int imageNo = 0;

        try{
            fileInputStream = new FileInputStream(propertiesPath);
            imageNoProperties = new Properties();
            imageNoProperties.load(fileInputStream);
            imageNo = Integer.parseInt(imageNoProperties.getProperty("imageNo"));

            System.out.println(imageNo);

            cropAnalysis.setCropSideImageURL("cropSideImage"+imageNo+".jpg");
            cropAnalysis.setCropVerticalImageURL("cropVerticleImage"+imageNo+".jpg");

            imageNo++;
            imageNoProperties.setProperty("imageNo",String.valueOf(imageNo));
            System.out.println(imageNoProperties.getProperty("imageNo"));
            fileOutputStream = new FileOutputStream(propertiesPath);
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



       // analysisMapper.insert(cropAnalysis);
    }

    //수신한 이미지를 저장
    @Override
    public void saveCropFacilityInfo(CropAnalysis CropAnalysis){
        File imagesFile = new File(path);
        FileOutputStream fileOutputStreamCSI = null;
        FileOutputStream fileOutputStreamCVI = null;

        //오늘 날짜에 해당하는 폴더가 없으면 생성
        if(!imagesFile.isDirectory()){
            imagesFile.mkdir();
        }

        try {
            fileOutputStreamCSI = new FileOutputStream(path +"//"+ CropAnalysis.getCropSideImageURL());
            fileOutputStreamCSI.write(CropAnalysis.getCropSideImage().getBytes());//측면 이미지 저장
            fileOutputStreamCSI.flush();

            fileOutputStreamCVI = new FileOutputStream(path +"//"+ CropAnalysis.getCropVerticalImageURL());
            fileOutputStreamCVI.write(CropAnalysis.getCropVerticalImage().getBytes());//수직 이미지 저장
            fileOutputStreamCVI.flush();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(fileOutputStreamCSI != null){
                    fileOutputStreamCSI.close();
                }
                if(fileOutputStreamCVI != null){
                    fileOutputStreamCVI.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    public List<CropAverage> searchCropList(int no){
        no = (no -1) * 10;
        List<CropAverage> cropAverageList = analysisMapper.selectByNo(no);

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
