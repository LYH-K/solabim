package kr.co.chd.envir.envir_management.envirmanagement;

public interface CropEnvirService {
    public EnvirInfo measureEnvir() throws Exception;
    public void sendEnvirInfo(EnvirInfo envirInfo) throws Exception;
}
