package model;


import android.graphics.Camera;

import java.util.Date;


public class Notification {
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
