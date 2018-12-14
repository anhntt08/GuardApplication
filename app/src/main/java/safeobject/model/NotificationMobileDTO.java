package safeobject.model;



import java.io.Serializable;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.Date;

@SuppressWarnings("serial")
public class NotificationMobileDTO implements Serializable {

    private Long id;
    private String image_url;
    private Long datetime;
    /* status:
    --- 0: not read
    --- 1: reject
    --- 2: done
    */
    private int status;
    private String camera_location;
    private String username;
    private Long notiFeedback;
    public NotificationMobileDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public Long getDatetime() {
        return datetime;
    }

    public void setDatetime(Long datetime) {
        this.datetime = datetime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCamera_location() {
        return camera_location;
    }

    public void setCamera_location(String camera_location) {
        this.camera_location = camera_location;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getNotiFeedback() {
        return notiFeedback;
    }

    public void setNotiFeedback(Long notiFeedback) {
        this.notiFeedback = notiFeedback;
    }
}
