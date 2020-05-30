package modelclass;

public class StateName {
    private int img;
    private String statename;
    private String id;

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getStatename() {
        return statename;
    }

    public void setStatename(String statename) {
        this.statename = statename;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public StateName(int img, String statename, String id) {
        this.img = img;
        this.statename = statename;
        this.id = id;
    }
}
