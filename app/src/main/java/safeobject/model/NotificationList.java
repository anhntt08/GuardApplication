package safeobject.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NotificationList {
    @SerializedName("notice_list")
    private ArrayList<NotificationMobileDTO> noticeList;

    public ArrayList<NotificationMobileDTO> getNoticeArrayList() {
        return noticeList;
    }

    public void setNoticeArrayList(ArrayList<NotificationMobileDTO> noticeArrayList) {
        this.noticeList = noticeArrayList;
    }
}
