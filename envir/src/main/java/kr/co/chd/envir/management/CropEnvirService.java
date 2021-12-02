package kr.co.chd.envir.management;

import org.springframework.stereotype.Service;

@Service
public interface CropEnvirService {
    public CropEnvirInfo measureCropEnvir() throws Exception;
    public void sendCropEnvirInfo(CropEnvirInfo cropEnvirInfo) throws Exception;
}

