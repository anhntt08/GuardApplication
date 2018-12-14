package safeobject.model;

public class NotificationFeedbackDTO {
    private Long id;
    private String description;
    private String imageURL;
    private Long notificationId;

    public NotificationFeedbackDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL( String imageURL ) {
        this.imageURL = imageURL;
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId( Long notificationId ) {
        this.notificationId = notificationId;
    }
}
