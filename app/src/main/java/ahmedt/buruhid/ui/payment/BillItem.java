package ahmedt.buruhid.ui.payment;

public class BillItem {
    private String price, type, days;

    public BillItem(String price, String type, String days) {
        this.price = price;
        this.type = type;
        this.days = days;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }
}
