package vo;

public class TicketSetProfitInfo {
    private String ownerId;
    private String setInclude;
    private String price;
    private String count;

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getSetInclude() {
        return setInclude;
    }

    public void setSetInclude(String setInclude) {
        this.setInclude = setInclude;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
