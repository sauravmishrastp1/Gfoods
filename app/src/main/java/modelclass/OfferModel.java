package modelclass;

public class OfferModel {
    private String productimg;
    private String offer;
    private String productname;

    public String getProductimg() {
        return productimg;
    }

    public void setProductimg(String productimg) {
        this.productimg = productimg;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public OfferModel(String productimg, String offer, String productname) {
        this.productimg = productimg;
        this.offer = offer;
        this.productname = productname;
    }
}
