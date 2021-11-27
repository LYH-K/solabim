package kr.co.chd.system.analysis_management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalysisServiceImp implements AnalysisService {
    @Autowired
    private AnalysisMapper analysisMapper;

    @Override
    public void addCropAnalysis(CropAnalysis cropAnalysis) {
        analysisMapper.insert(cropAnalysis);
    }

    @Override
    public List<CropAverage> searchCropList() {
        List<CropAverage> cropAverages = analysisMapper.select();

        for(CropAverage cropAnalysisInfo : cropAverages){
            System.out.println(cropAnalysisInfo);
        }

        return null;
    }

    @Override
    public String predictHarvest(List<CropAnalysis> growths) {
        return null;
    }

    @Override
    public List<CropInfo> searchAnalysisInfo(String date) {
        List<CropInfo> cropInfoList = analysisMapper.selectByDate(date);

        return null;
    }
}
