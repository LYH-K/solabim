package kr.co.system.chd.analysisManagement.service;

import kr.co.system.chd.analysisManagement.model.*;

import java.util.List;

public interface AnalysisService {
    public CropAnalysis receiveAnalysisInfo(CropAnalysis cropAnalysis);
    public void addCropAnalysis(CropAnalysis cropAnalysis);
    public List<CropAverage> searchCropList();
    public String predictHarvest(List<CropAnalysis> growths);
    public List<CropInfo> searchAnalysisInfo(String date);
}
