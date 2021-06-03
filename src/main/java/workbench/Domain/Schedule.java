package workbench.Domain;

public class Schedule {
    private String id;
    private String movieId;
    private String hallId;
    private String showTime;
    private String showPeriod;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id='" + id + '\'' +
                ", movieId='" + movieId + '\'' +
                ", hallId='" + hallId + '\'' +
                ", showTime='" + showTime + '\'' +
                ", showPeriod='" + showPeriod + '\'' +
                '}';
    }

    public String getMovieId() {
        return movieId;
    }

    public Schedule() {
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getHallId() {
        return hallId;
    }

    public void setHallId(String hallId) {
        this.hallId = hallId;
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
