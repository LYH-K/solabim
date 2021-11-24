package system.co.kr.chd.Service;

import system.co.kr.chd.model.CropAnalysis;
import system.co.kr.chd.model.CropAverage;
import system.co.kr.chd.model.CropInfo;

import java.util.List;

public class AnalysisService {
    public CropAnalysis receiveAnalysisInfo(CropAnalysis cropAnalysis){
        return cropAnalysis;
    }

    public void addCropAnalysis(CropAnalysis cropAnalysis){

    }

    public List<CropAverage> searchCropList(){
        return null;
    }

    public String predictHarvest(List<CropAnalysis> cropAnalysisList){
        return null;
    }

    public List<CropInfo> searchAnalysisInfo(String date){
        return null;
    }

}
