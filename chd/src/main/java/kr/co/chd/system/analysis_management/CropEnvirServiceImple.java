package kr.co.chd.system.analysis_management;

import jakarta.annotation.Resource;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CropEnvirServiceImple implements CropEnvirService {
    @Resource
    private CropEnvirMapper cropEnvirMapper;

    @Autowired
    private CropFacilityService cropFacilityService;

    @Override
    public void receiveCropEnvirInfo(CropEnvirInfo cropEnvirInfo) {
        cropEnvirMapper.insert(cropEnvirInfo);
        cropFacilityService.sendCropEnvirInfo (cropEnvirInfo);
    }
}
