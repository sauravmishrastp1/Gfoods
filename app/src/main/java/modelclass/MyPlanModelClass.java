package modelclass;

public class MyPlanModelClass {
    private int img ;
    private String productname;
    private int color;
    private String id;
    private String date;

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public MyPlanModelClass(int img, String productname, int color, String id, String date) {
        this.img = img;
        this.productname = productname;
        this.color = color;
        this.id = id;
        this.date = date;
    }
}
