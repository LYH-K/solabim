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
            System.out.println(cropAnalysisInfo.getDate());
            System.out.println(cropAnalysisInfo.getGrowthAvg());
            System.out.println(cropAnalysisInfo.getilluminanceAvg());
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

        for(CropInfo cropInfo : cropInfoList){
            System.out.println(cropInfo.getDate());
            System.out.println(cropInfo.getTime());
            System.out.println(cropInfo.getGrowth());
            System.out.println(cropInfo.getilluminance());
            System.out.println(cropInfo.getHorizontalAngle());
            System.out.println(cropInfo.getVerticalAngle());
            System.out.println(cropInfo.getCropSideImageURL());
            System.out.println(cropInfo.getCropVerticalImageURL());
        }

        return null;
    }
}
