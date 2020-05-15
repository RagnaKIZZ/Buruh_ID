package ahmedt.buruhid.ui.order;

public class YourOrderItem {
    private String name, type, desc, status, price;
    private int rating, photo;

    public YourOrderItem(String name, String type, String desc, String status, String price, int rating, int photo) {
        this.name = name;
        this.type = type;
        this.desc = desc;
        this.status = status;
        this.price = price;
        this.rating = rating;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
