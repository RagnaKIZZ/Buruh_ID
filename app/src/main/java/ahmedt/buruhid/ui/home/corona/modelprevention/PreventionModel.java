package ahmedt.buruhid.ui.home.corona.modelprevention;

public class PreventionModel {
    private String desc;
    private int img;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public PreventionModel(String desc, int img) {
        this.desc = desc;
        this.img = img;
    }
}
