package modelclass;

public class MyWalletModel {
    private String pay;
    private String cashback;

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public String getCashback() {
        return cashback;
    }

    public void setCashback(String cashback) {
        this.cashback = cashback;
    }

    public MyWalletModel(String pay, String cashback) {
        this.pay = pay;
        this.cashback = cashback;
    }
}
