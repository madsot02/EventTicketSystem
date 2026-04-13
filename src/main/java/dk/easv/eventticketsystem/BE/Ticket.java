package dk.easv.eventticketsystem.BE;

public class Ticket {
    private int ticketId;
    private String ticketUUID;
    private int eventId;
    private int ticketTypeId;
    private boolean isUsed;

    // Display helpers — populated like assignedCoordinators in Event
    private String eventName = "";
    private String typeName  = "";

    public Ticket(int ticketId, String ticketUUID, int eventId, int ticketTypeId, boolean isUsed) {
        this.ticketId     = ticketId;
        this.ticketUUID   = ticketUUID;
        this.eventId      = eventId;
        this.ticketTypeId = ticketTypeId;
        this.isUsed       = isUsed;
    }

    public int getTicketId() { return ticketId; }
    public void setTicketId(int ticketId) { this.ticketId = ticketId; }

    public String getTicketUUID() { return ticketUUID; }
    public void setTicketUUID(String ticketUUID) { this.ticketUUID = ticketUUID; }

    public int getEventId() { return eventId; }
    public void setEventId(int eventId) { this.eventId = eventId; }

    public int getTicketTypeId() { return ticketTypeId; }
    public void setTicketTypeId(int ticketTypeId) { this.ticketTypeId = ticketTypeId; }

    public boolean getIsUsed() { return isUsed; }
    public void setIsUsed(boolean isUsed) { this.isUsed = isUsed; }

    public String getEventName() { return eventName; }
    public void setEventName(String eventName) { this.eventName = eventName; }

    public String getTypeName() { return typeName; }
    public void setTypeName(String typeName) { this.typeName = typeName; }
}
