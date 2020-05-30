package modelclass;

public class ViewBillModel {
    private String invoce;
    private String date1;
    private String date2;
    private String orderstatus;
    private String img;
    private String price;
    private String daytype;
    private String productid;
    private String quant;
    private String productname;
    private String id;

    public String getInvoce() {
        return invoce;
    }

    public void setInvoce(String invoce) {
        this.invoce = invoce;
    }

    public String getDate1() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public String getDate2() {
        return date2;
    }

    public void setDate2(String date2) {
        this.date2 = date2;
    }

    public String getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(String orderstatus) {
        this.orderstatus = orderstatus;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDaytype() {
        return daytype;
    }

    public void setDaytype(String daytype) {
        this.daytype = daytype;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getQuant() {
        return quant;
    }

    public void setQuant(String quant) {
        this.quant = quant;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ViewBillModel(String invoce, String date1, String date2, String orderstatus, String img, String price, String daytype, String productid, String quant, String productname, String id) {
        this.invoce = invoce;
        this.date1 = date1;
        this.date2 = date2;
        this.orderstatus = orderstatus;
        this.img = img;
        this.price = price;
        this.daytype = daytype;
        this.productid = productid;
        this.quant = quant;
        this.productname = productname;
        this.id = id;
    }
}
