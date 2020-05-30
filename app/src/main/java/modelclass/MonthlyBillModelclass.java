package modelclass;

public class MonthlyBillModelclass {
    private String date;
    private String productname;
    private String price;
    private String quantity;
    private String id;
    private String productid;
    private String productvolume;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getProductvolume() {
        return productvolume;
    }

    public void setProductvolume(String productvolume) {
        this.productvolume = productvolume;
    }

    public MonthlyBillModelclass(String date, String productname, String price, String quantity, String id, String productid, String productvolume) {
        this.date = date;
        this.productname = productname;
        this.price = price;
        this.quantity = quantity;
        this.id = id;
        this.productid = productid;
        this.productvolume = productvolume;
    }
}
