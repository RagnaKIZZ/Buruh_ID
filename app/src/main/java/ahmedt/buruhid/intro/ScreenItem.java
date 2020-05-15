package ahmedt.buruhid.intro;

public class ScreenItem {
    private String title, description;
    private int photo;

    public ScreenItem(String title, String description, int photo) {
        this.title = title;
        this.description = description;
        this.photo = photo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
