package modelclass;

public class EventCalender {
    private int color;
    private long timeInMillis;
    private Object data;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public long getTimeInMillis() {
        return timeInMillis;
    }

    public void setTimeInMillis(long timeInMillis) {
        this.timeInMillis = timeInMillis;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public EventCalender(int color, long timeInMillis, Object data) {
        this.color = color;
        this.timeInMillis = timeInMillis;
        this.data = data;
    }
}
