package kr.co.chd.system.analysis_management;

import java.util.List;

public interface AnalysisService {
    public void addCropAnalysis(CropAnalysis cropAnalysis);
    public List<CropAverage> searchCropList();
    public String predictHarvest(List<CropAnalysis> growths);
    public List<CropInfo> searchAnalysisInfo(String date);
}
