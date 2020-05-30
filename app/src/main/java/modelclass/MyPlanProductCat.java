package modelclass;

public class MyPlanProductCat {
    private String productimg;
    private String producvtname;
    private String quanty;
    private String mrp;
    private int color;

    public String getProductimg() {
        return productimg;
    }

    public void setProductimg(String productimg) {
        this.productimg = productimg;
    }

    public String getProducvtname() {
        return producvtname;
    }

    public void setProducvtname(String producvtname) {
        this.producvtname = producvtname;
    }

    public String getQuanty() {
        return quanty;
    }

    public void setQuanty(String quanty) {
        this.quanty = quanty;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
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

    public MyPlanProductCat(String productimg, String producvtname, String quanty, String mrp, int color, String id) {
        this.productimg = productimg;
        this.producvtname = producvtname;
        this.quanty = quanty;
        this.mrp = mrp;
        this.color = color;
        this.id = id;
    }

    private String id;


}
