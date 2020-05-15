package ahmedt.buruhid.make_order.select_worker;

public class SelectWorkerItem {
    private String name,type, address;
    private int img;
    private double rating;

    public SelectWorkerItem(String name, String type, String address, int img, double rating) {
        this.name = name;
        this.type = type;
        this.address = address;
        this.img = img;
        this.rating = rating;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
