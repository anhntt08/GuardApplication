package model;



import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Notification implements Serializable {
    private int id;
    private String imageURL = "";
    private CameraDTO camera_id;
    private Date dateTime;
    /* status:
    --- 0: not read
    --- 1: reject
    --- 2: done
    */
    private int status;
    private int userID;

    public Notification() {
    }

    public Notification(CameraDTO cameraDTO, Date dateTime){
        this.camera_id = cameraDTO;
        this.dateTime = dateTime;
    }

    public Notification(int id, String imageURL, CameraDTO camera_id, Date dateTime, int status, int userID) {
        this.id = id;
        this.imageURL = imageURL;
        this.camera_id = camera_id;
        this.dateTime = dateTime;
        this.status = status;
        this.userID = userID;
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

    public CameraDTO getCamera_id() {
        return camera_id;
    }

    public void setCamera_id(CameraDTO camera_id) {
        this.camera_id = camera_id;
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

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
