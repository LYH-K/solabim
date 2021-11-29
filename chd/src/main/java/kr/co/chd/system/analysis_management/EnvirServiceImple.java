package kr.co.chd.system.analysis_management;

import jakarta.annotation.Resource;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EnvirServiceImple implements EnvirService{
    @Resource
    private EnvirMapper envirMapper;

    @Autowired
    private CropFacilityService cropFacilityService;

    @Override
    public void receiveEnvirInfo(EnvirInfo envirInfo) {
        envirMapper.insert(envirInfo);
        cropFacilityService.sendEnvirInfo (envirInfo);
    }
}
