package modelclass;

public class OrderModel {
    private String  id;
    private String productid;
    private String date;
    private String quantity;
    private String productname;
    private String image;
    private String city;
    private String price;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public OrderModel(String id, String productid, String date, String quantity, String productname, String image, String city, String price) {
        this.id = id;
        this.productid = productid;
        this.date = date;
        this.quantity = quantity;
        this.productname = productname;
        this.image = image;
        this.city = city;
        this.price = price;
    }
}
