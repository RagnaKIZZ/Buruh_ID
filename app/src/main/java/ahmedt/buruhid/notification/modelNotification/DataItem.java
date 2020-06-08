package ahmedt.buruhid.notification.modelNotification;


import com.google.gson.annotations.SerializedName;


public class DataItem {

    @SerializedName("user_id")
    private String userId;

    @SerializedName("isRead")
    private String isRead;

    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("message")
    private String message;

    @SerializedName("create_date")
    private String createDate;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateDate() {
        return createDate;
    }

    @Override
    public String toString() {
        return
                "DataItem{" +
                        "user_id = '" + userId + '\'' +
                        ",isRead = '" + isRead + '\'' +
                        ",id = '" + id + '\'' +
                        ",title = '" + title + '\'' +
                        ",message = '" + message + '\'' +
                        ",create_date = '" + createDate + '\'' +
                        "}";
    }
}