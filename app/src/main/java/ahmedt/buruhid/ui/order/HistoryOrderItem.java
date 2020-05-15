package ahmedt.buruhid.ui.order;

public class HistoryOrderItem {
    private String type, desc, price, date;
    private int photo;

    public HistoryOrderItem(String type, String desc, String price, String date, int photo) {
        this.type = type;
        this.desc = desc;
        this.price = price;
        this.date = date;
        this.photo = photo;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
