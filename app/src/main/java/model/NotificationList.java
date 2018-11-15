package model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NotificationList {
    @SerializedName("notice_list")
    private ArrayList<Notification> noticeList;

    public ArrayList<Notification> getNoticeArrayList() {
        return noticeList;
    }

    public void setNoticeArrayList(ArrayList<Notification> noticeArrayList) {
        this.noticeList = noticeArrayList;
    }
}
