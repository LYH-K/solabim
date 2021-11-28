package kr.co.chd.system.analysis_management;

import java.util.List;

public interface AnalysisService {
    public void addCropAnalysis(CropAnalysis cropAnalysis);
    public List<CropAverage> searchCropList();
    public List<CropAverage> searchCrop(String date);
    public String predictHarvest();
    public List<CropInfo> searchAnalysisInfo(String date);

    //추가
    public void saveImage(CropAnalysis CropAnalysis);
}
