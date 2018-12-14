package safeobject.model;

public class NotificationDTO {
    private String image_url;
    private Long datetime;
    private Long cameraID;
    public NotificationDTO() {
    }


    public Long getTime() {
        return datetime;
    }

    public void setTime(Long time) {
        this.datetime = time;
    }

    public Long getCameraID() {
        return cameraID;
    }

    public void setCameraID(Long cameraID) {
        this.cameraID = cameraID;
    }

    public String getImageURL() {
        return image_url;
    }

    public void setImageURL(String imageURL) {
        this.image_url = imageURL;
    }



}
