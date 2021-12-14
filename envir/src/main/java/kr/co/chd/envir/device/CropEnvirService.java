package kr.co.chd.envir.device;

public interface CropEnvirService {
    CropEnvirInfo measureCropEnvir(boolean resetSignal) throws Exception; //농작물 환경 정보 측정
    void sendCropEnvirInfo(CropEnvirInfo cropEnvirInfo) throws Exception; //농작물 환경 정보 송신
}
