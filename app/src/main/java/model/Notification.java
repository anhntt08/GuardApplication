package model;


public class Notification {
    private String title;
    private String imageURL = "";
    private String camraID;
    private String dateTime;

    public Notification() {
    }

    public Notification(String title, String imageURL){
        this.title = title;
        this.imageURL = imageURL;
    }
    public Notification(String title, String imageURL, String camraID, String dateTime) {
        this.title = title;
        this.imageURL = imageURL;
        this.camraID = camraID;
        this.dateTime = dateTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getCamraID() {
        return camraID;
    }

    public void setCamraID(String camraID) {
        this.camraID = camraID;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
