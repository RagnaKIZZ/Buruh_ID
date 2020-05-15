package ahmedt.buruhid.ui.payment;

public class HistoryPaymentItem {
    private String price, type, date;

    public HistoryPaymentItem(String price, String type, String date) {
        this.price = price;
        this.type = type;
        this.date = date;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
