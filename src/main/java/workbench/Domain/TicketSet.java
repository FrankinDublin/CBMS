package workbench.Domain;

public class TicketSet {
   private String id;
   private String setInclude;
   private String price;
   private String ownerId;

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSetInclude() {
        return setInclude;
    }

    public void setSetInclude(String setInclude) {
        this.setInclude = setInclude;
    }

    @Override
    public String toString() {
        return "TicketSet{" +
                "id='" + id + '\'' +
                ", setInclude='" + setInclude + '\'' +
                ", price='" + price + '\'' +
                '}';
    }

    public TicketSet() {
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
