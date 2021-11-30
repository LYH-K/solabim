package kr.co.chd.system.analysis_management;

import java.util.List;

public interface CropAnalysisService {
    public void addCropAnalysis(CropAnalysis cropAnalysis);
    public List<CropAverage> searchCropList();
    public List<CropAverage> searchCropList(String date);
    public String predictHarvest();
    public List<CropInfo> searchAnalysisInfo(String date);
    public void saveImage(CropAnalysis CropAnalysis);
}
