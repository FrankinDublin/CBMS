package vo;


public class ScheduleInfo {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String hall;
    private String showTime;
    private String row;
    private String col;

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public String getCol() {
        return col;
    }

    public void setCol(String col) {
        this.col = col;
    }

    @Override
    public String toString() {
        return "ScheduleInfo{" +
                "hall='" + hall + '\'' +
                ", showTime='" + showTime + '\'' +
                ", showPeriod='" + showPeriod + '\'' +
                '}';
    }

    private String showPeriod;

    public String getHall() {
        return hall;
    }

    public void setHall(String hall) {
        this.hall = hall;
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public String getShowPeriod() {
        return showPeriod;
    }

    public void setShowPeriod(String showPeriod) {
        this.showPeriod = showPeriod;
    }
}
