package dk.easv.eventticketsystem.BE;

public class TicketType {
    private int ticketTypeId;
    private int eventId;
    private String typeName;
    private String description;

    public TicketType(int ticketTypeId, int eventId, String typeName, String description) {
        this.ticketTypeId = ticketTypeId;
        this.eventId = eventId;
        this.typeName = typeName;
        this.description = description;
    }

    public int getTicketTypeId() { return ticketTypeId; }
    public void setTicketTypeId(int ticketTypeId) { this.ticketTypeId = ticketTypeId; }

    public int getEventId() { return eventId; }
    public void setEventId(int eventId) { this.eventId = eventId; }

    public String getTypeName() { return typeName; }
    public void setTypeName(String typeName) { this.typeName = typeName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() { return typeName; }
}