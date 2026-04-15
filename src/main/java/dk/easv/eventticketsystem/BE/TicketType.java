package dk.easv.eventticketsystem.BE;

public class TicketType {
    private int typeId;
    private Integer eventId;
    private String typeName;
    private double price;
    private boolean isVoucher;

    public TicketType(int typeId, Integer eventId, String typeName, double price, boolean isVoucher) {
        this.typeId = typeId;
        this.eventId = eventId;
        this.typeName = typeName;
        this.price = price;
        this.isVoucher = isVoucher;
    }

    public int getTypeId()          { return typeId; }
    public Integer getEventId()     { return eventId; }
    public String getTypeName()     { return typeName; }
    public double getPrice()        { return price; }
    public boolean isVoucher()      { return isVoucher; }

    public void setTypeName(String typeName) { this.typeName = typeName; }
    public void setPrice(double price)       { this.price = price; }

    @Override
    public String toString() {
        return typeName + " - " + price + " kr" + (isVoucher ? " [Voucher]" : "");
    }
}