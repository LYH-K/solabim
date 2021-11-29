package kr.co.chd.envir.envir_management.envirmanagement;

public interface EnvirService {
    public EnvirInfo measureEnvir() throws Exception;
    public void sendEnvirInfo(EnvirInfo envirInfo) throws Exception;
    public void now() throws Exception;
    public void mesureSend() throws Exception;
}
