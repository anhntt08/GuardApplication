package model;

public class CameraDTO {
    private int cameraID;
    private String cameraLocation;

    public CameraDTO() {
    }

    public CameraDTO(int cameraID, String cameraLocation) {
        this.cameraID = cameraID;
        this.cameraLocation = cameraLocation;
    }

    public int getCameraID() {
        return cameraID;
    }

    public void setCameraID(int cameraID) {
        this.cameraID = cameraID;
    }

    public String getCameraLocation() {
        return cameraLocation;
    }

    public void setCameraLocation(String cameraLocation) {
        this.cameraLocation = cameraLocation;
    }
}
