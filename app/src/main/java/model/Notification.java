package model;



import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Notification implements Serializable {
    private int id;
    private String imageURL;
    private Date dateTime;
    /* status:
    --- 0: not read
    --- 1: reject
    --- 2: done
    */
    private int status;
    private CameraDTO camera;
    private UserDTO user;

    public Notification() {
    }

    public Notification(CameraDTO cameraDTO, Date dateTime){
        this.camera = cameraDTO;
        this.dateTime = dateTime;
    }

    public Notification(int id, String imageURL, CameraDTO camera_id, Date dateTime, int status, UserDTO userID) {
        this.id = id;
        this.imageURL = imageURL;
        this.camera = camera_id;
        this.dateTime = dateTime;
        this.status = status;
        this.user = userID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }


    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public CameraDTO getCamera() {
        return camera;
    }

    public void setCamera(CameraDTO camera) {
        this.camera = camera;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
