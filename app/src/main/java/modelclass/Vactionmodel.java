package modelclass;

public class Vactionmodel {
    private String startdate;
    private String enddate;
    private String id;

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Vactionmodel(String startdate, String enddate, String id) {
        this.startdate = startdate;
        this.enddate = enddate;
        this.id = id;
    }
}
