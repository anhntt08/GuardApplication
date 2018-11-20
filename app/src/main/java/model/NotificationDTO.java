package model;


public class NotificationDTO {
    private String image_url;
    private Long datetime;
    private Long cameraID;

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public Long getCameraID() {
        return cameraID;
    }

    public void setCameraID(Long cameraID) {
        this.cameraID = cameraID;
    }

    public Long getDatetime() {
        return datetime;
    }

    public void setDatetime(Long datetime) {
        this.datetime = datetime;
    }
}
