package kr.co.chd.system.management;

import java.util.List;

public interface CropAnalysisService {
    public void addCropAnalysis(CropAnalysis cropAnalysis);
    public List<CropAverage> searchCropList();
    public List<CropAverage> searchCropList(int no);
    public List<CropAverage> searchCropList(String date);
    public String predictHarvest();
    public List<CropInfo> searchAnalysisInfo(String date);
    public void saveCropImage(CropAnalysis CropAnalysis);
    public CropAnalysis analysisCrop(CropAnalysis cropAnalysis);
    public CropAnalysis searchImage(int no);
}
