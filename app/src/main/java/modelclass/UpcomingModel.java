package modelclass;

public class UpcomingModel {
    private String imaage;

    public String getImaage() {
        return imaage;
    }

    public void setImaage(String imaage) {
        this.imaage = imaage;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getQuant() {
        return quant;
    }

    public void setQuant(String quant) {
        this.quant = quant;
    }

    public UpcomingModel(String imaage, String productname, String quant) {
        this.imaage = imaage;
        this.productname = productname;
        this.quant = quant;
    }

    private String productname;
    private String quant;


}
