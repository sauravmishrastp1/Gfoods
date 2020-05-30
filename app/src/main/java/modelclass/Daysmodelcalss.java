package modelclass;

public class Daysmodelcalss {
    private String days;
    private boolean isselected;

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public boolean isIsselected() {
        return isselected;
    }

    public void setIsselected(boolean isselected) {
        this.isselected = isselected;
    }

    public Daysmodelcalss() {
    }

    public Daysmodelcalss(String days, boolean isselected) {
        this.days = days;
        this.isselected = isselected;
    }
}
