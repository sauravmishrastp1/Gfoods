package modelclass;

public class MyPlanProductCat {
    private String productimg;
    private String producvtname;
    private String quanty;
    private String mrp;
    private int color;
    private String id;
    private String date;
    private String mainquantity;
    private String o_id;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMainquantity() {
        return mainquantity;
    }

    public void setMainquantity(String mainquantity) {
        this.mainquantity = mainquantity;
    }

    public String getO_id() {
        return o_id;
    }

    public void setO_id(String o_id) {
        this.o_id = o_id;
    }

    public MyPlanProductCat(String productimg, String producvtname, String quanty, String mrp, int color, String id, String date, String mainquantity, String o_id) {
        this.productimg = productimg;
        this.producvtname = producvtname;
        this.quanty = quanty;
        this.mrp = mrp;
        this.color = color;
        this.id = id;
        this.date = date;
        this.mainquantity = mainquantity;
        this.o_id = o_id;
    }
}