package modelclass;

public class MyPlanModelClass {
    private int img ;
    private String productname;
    private int color;
    private String id;

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

    public MyPlanModelClass(int img, String productname, int color, String id) {
        this.img = img;
        this.productname = productname;
        this.color = color;
        this.id = id;
    }
}
