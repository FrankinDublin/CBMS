package vo;

public class UserPurchaseInfo {
    private String id;
    private String movieName;
    private String hallName;
    private String showTime;
    private String showPeriod;
    private String cols;
    private String rows;
    private String setInclude;
    private String cost;
    private String dealTime;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
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

    public String getCols() {
        return cols;
    }

    public void setCols(String cols) {
        this.cols = cols;
    }

    public String getRows() {
        return rows;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }

    public String getSetInclude() {
        return setInclude;
    }

    public void setSetInclude(String setInclude) {
        this.setInclude = setInclude;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDealTime() {
        return dealTime;
    }

    public void setDealTime(String dealTime) {
        this.dealTime = dealTime;
    }
}
