package kr.co.chd.envir.envir_management;

public interface CropEnvirService {
    public CropEnvirInfo measureCropEnvir() throws Exception;
    public void sendCropEnvirInfo(CropEnvirInfo cropEnvirInfo) throws Exception;
}

