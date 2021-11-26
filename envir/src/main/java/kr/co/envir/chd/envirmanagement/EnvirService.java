package kr.co.envir.chd.envirmanagement;

public interface EnvirService {
    public EnvirInfo measureEnvir() throws Exception;
    public void sendEnvirInfo() throws Exception;
}
