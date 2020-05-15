package ahmedt.buruhid.ui.home;

public class WhatsNewItem {
    String title, desc;
    int photo;

    public WhatsNewItem(String title, String desc, int photo) {
        this.title = title;
        this.desc = desc;
        this.photo = photo;
    }

    public WhatsNewItem() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
