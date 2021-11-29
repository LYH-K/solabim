package kr.co.chd.system.analysis_management;

import org.springframework.stereotype.Service;

@Service
public interface CropFacilityService {
    public void sendEnvirInfo (EnvirInfo envirInfo);
}
