package kr.co.chd.system.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

@Service
public class CropAnalysisServiceImp implements CropAnalysisService {
    @Autowired
    private CropAnalysisMapper analysisMapper;
    @Autowired
    private CropEnvirMapper cropEnvirMapper;
    @Autowired
    private CropData cropData;

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

            cropAnalysis.setCropSideImageURL(path+"\\cropSideImage"+imageNo+".jpg");
            cropAnalysis.setCropVerticalImageURL(path+"\\cropVerticalImage"+imageNo+".jpg");

            imageNo++;
            imageNoProperties.setProperty("imageNo",String.valueOf(imageNo));
            fileOutputStream = new FileOutputStream(propertiesPath);
            imageNoProperties.store(fileOutputStream,"변경");
            fileOutputStream.flush();

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

        saveCropImage(cropAnalysis);
        analysisMapper.insert(cropAnalysis);
    }

    //수신한 이미지를 저장
    @Override
    public void saveCropImage(CropAnalysis CropAnalysis){
        File imagesFile = new File(path);
        FileOutputStream fileOutputStreamCSI = null;
        FileOutputStream fileOutputStreamCVI = null;

        //오늘 날짜에 해당하는 폴더가 없으면 생성
        if(!imagesFile.isDirectory()){
            imagesFile.mkdir();
        }

        try {
            fileOutputStreamCSI = new FileOutputStream(CropAnalysis.getCropSideImageURL());
            fileOutputStreamCSI.write(CropAnalysis.getCropSideImage().getBytes());//측면 이미지 저장
            fileOutputStreamCSI.flush();

            fileOutputStreamCVI = new FileOutputStream(CropAnalysis.getCropVerticalImageURL());
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
    public List<CropAverage> searchCropList(Map<String, String> condition) {
        List<CropAverage> cropAverageList = analysisMapper.selectAll(condition);

        return cropAverageList;
    }

    @Override
    public List<CropAverage> searchCrops(){
        return analysisMapper.selectInfo();
    }

    @Override
    public List<CropAverage> searchCropList(int no){
        no = (no -1) * 10;
        List<CropAverage> cropAverageList = analysisMapper.selectByNo(no);

        return cropAverageList;
    }

    @Override
    public CropAnalysis searchImage(int no){
        CropAnalysis cropAnalysis = analysisMapper.selectImage(no);

        return cropAnalysis;
    }

    @Override
    public String predictHarvest() {
        LocalDate today = LocalDate.now();
        List<CropAverage> cropAverageList = analysisMapper.select();

        int maxGrowth = cropAverageList.get(0).getGrowthAvg();

        LocalDate predictDate;
        int dateGrothAvg = maxGrowth / cropAverageList.size();
        if (maxGrowth < 70) {
            if ((70 % dateGrothAvg) > 0) {
                int plusDate = (70 / dateGrothAvg) + 1;
                plusDate = plusDate - cropAverageList.size();
                predictDate = today.plusDays(plusDate);
            } else {
                predictDate = today.plusDays((70 / dateGrothAvg) - cropAverageList.size());
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

    @Override
    public CropAverage searchDateAverage(String date) {
        List<CropInfo> cropInfoList = analysisMapper.selectByDate(date);

        int totalGrowth = 0;
        int totalIlluminance = 0;

        for (int i = 0; i < cropInfoList.size(); i++) {
            totalGrowth = totalGrowth + cropInfoList.get(i).getGrowth();
            totalIlluminance = totalIlluminance + cropInfoList.get(i).getIlluminance();
        }

        CropAverage cropAverage = new CropAverage();
        cropAverage.setDate(date);
        cropAverage.setGrowthAvg(totalGrowth/cropInfoList.size());
        cropAverage.setIlluminanceAvg(
                totalIlluminance/cropInfoList.size());

        return cropAverage;
    }

    @Override
    public CropAnalysis analysisCrop(CropAnalysis cropAnalysis) {
        cropData = analysisMapper.selectRGB();

        String[] rgb = cropAnalysis.getCropRGB().split(",");
        List<Float> intRGB = new ArrayList<Float>();
        for (int i = 0; i < rgb.length; i++) {
            intRGB.add(Float.parseFloat(rgb[i]));
        }

        int sideR = (int)(intRGB.get(0) / (int)cropData.getCropSideR());
        int sideG = (int)(intRGB.get(1) / (int)cropData.getCropSideG());
        int sideB = (int)(intRGB.get(2) / (int)cropData.getCropSideB());
        int sideGrowth = (int)Math.round(((sideR * 0.299) + (sideG * 0.587) + (sideB * 0.114)) * 100);

        int verticalR = (int)(intRGB.get(3) / (int)cropData.getCropVerticalR());
        int verticalG = (int)(intRGB.get(4) / (int)cropData.getCropVerticalG());
        int verticalB = (int)(intRGB.get(5) / (int)cropData.getCropVerticalB());
        int verticalGrowth = (int)Math.round(((verticalR * 0.299) + (verticalG * 0.587) + (verticalB * 0.114)) * 100);

        cropAnalysis.setGrowth(sideGrowth + verticalGrowth / 2);
        return cropAnalysis;
    }

    @Override
    public void addCropEnvirInfo(CropEnvirInfo cropEnvirInfo){
        if(cropEnvirInfo.isResetSignal() != true){
            cropEnvirMapper.insert(cropEnvirInfo);
        }
    }

    @Override
    public int countBoard() {
        return analysisMapper.countBoard();
    }

}
