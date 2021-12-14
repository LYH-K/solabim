package kr.co.chd.system.management;

import java.util.List;
import java.util.Map;

public interface CropAnalysisService {
    public void addCropAnalysis(CropAnalysis cropAnalysis);                  //
    public List<CropAverage> searchCropList(Map<String, String> condition);  //
    public List<CropAverage> searchCropList(int no);                         //
    public List<CropAverage> searchCropList();                               //
    public String predictHarvest();                                          //
    public List<CropInfo> searchAnalysisInfo(String date);                   //
    public void saveCropImage(CropAnalysis CropAnalysis);                    //
    public CropAnalysis analysisCrop(CropAnalysis cropAnalysis);             //
    public CropAnalysis searchImage(int no);                                 //
    public void addCropEnvirInfo(CropEnvirInfo cropEnvirInfo);               //
    public int countBoard();                                                 //
    public CropAverage searchDateAverage(String date);                       //
}
