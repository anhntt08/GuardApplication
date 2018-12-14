package safeobject.model;

import java.util.Arrays;
import java.util.Date;

public class FeedbackDTO {
    private String feedbackDescription;
    private Long time;
    private FeedbackPhotoDTO[] feedbackPhotoList;
    private Long userId;
    private String username;

    public FeedbackDTO() {
    }

    @Override
    public String toString() {
        return "FeedbackDTO{" +
                "feedbackDescription='" + feedbackDescription + '\'' +
                ", time=" + time +
                ", feedbackPhotoList=" + Arrays.toString(feedbackPhotoList) +
                '}';
    }

    public String getFeedbackDescription() {
        return feedbackDescription;
    }

    public void setFeedbackDescription(String feedbackDescription) {
        this.feedbackDescription = feedbackDescription;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public FeedbackPhotoDTO[] getFeedbackPhotoList() {
        return feedbackPhotoList;
    }

    public void setFeedbackPhotoList(FeedbackPhotoDTO[] feedbackPhotoList) {
        this.feedbackPhotoList = feedbackPhotoList;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId( Long userId ) {
        this.userId = userId;
    }

    public FeedbackDTO(String feedbackDescription, Long time, FeedbackPhotoDTO[] feedbackPhotoList, Long userId ) {
        this.feedbackDescription = feedbackDescription;
        this.time = time;
        this.feedbackPhotoList = feedbackPhotoList;
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
