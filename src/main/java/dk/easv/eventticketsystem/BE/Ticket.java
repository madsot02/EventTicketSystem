package dk.easv.eventticketsystem.BE;

public class Ticket {
    private int ticketId;
    private String ticketUUID;
    private Integer eventId;    // nullable — vouchers can be valid for all events
    private String typeName;    // "VIP", "General Admission", "1 free beer", etc.
    private boolean isVoucher;
    private boolean isUsed;

    private String eventName = "";

    public Ticket(int ticketId, String ticketUUID, Integer eventId, String typeName, boolean isVoucher, boolean isUsed) {
        this.ticketId   = ticketId;
        this.ticketUUID = ticketUUID;
        this.eventId    = eventId;
        this.typeName   = typeName;
        this.isVoucher  = isVoucher;
        this.isUsed     = isUsed;
    }

    public int getTicketId() { return ticketId; }
    public void setTicketId(int ticketId) { this.ticketId = ticketId; }

    public String getTicketUUID() { return ticketUUID; }
    public void setTicketUUID(String ticketUUID) { this.ticketUUID = ticketUUID; }

    public Integer getEventId() { return eventId; }
    public void setEventId(Integer eventId) { this.eventId = eventId; }

    public String getTypeName() { return typeName; }
    public void setTypeName(String typeName) { this.typeName = typeName; }

    public boolean getIsVoucher() { return isVoucher; }
    public void setIsVoucher(boolean isVoucher) { this.isVoucher = isVoucher; }

    public boolean getIsUsed() { return isUsed; }
    public void setIsUsed(boolean isUsed) { this.isUsed = isUsed; }

    public String getEventName() { return eventName; }
    public void setEventName(String eventName) { this.eventName = eventName; }
}