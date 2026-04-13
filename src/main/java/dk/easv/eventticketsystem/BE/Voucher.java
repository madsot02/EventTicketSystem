package dk.easv.eventticketsystem.BE;

public class Voucher {
    private int voucherId;
    private String voucherUUID;
    private Integer eventId;
    private String description;
    private boolean isUsed;

    private String eventName = "";

    public Voucher(int voucherId, String voucherUUID, Integer eventId, String description, boolean isUsed) {
        this.voucherId   = voucherId;
        this.voucherUUID = voucherUUID;
        this.eventId     = eventId;
        this.description = description;
        this.isUsed      = isUsed;
    }

    public int getVoucherId() { return voucherId; }
    public void setVoucherId(int voucherId) { this.voucherId = voucherId; }

    public String getVoucherUUID() { return voucherUUID; }
    public void setVoucherUUID(String voucherUUID) { this.voucherUUID = voucherUUID; }

    public Integer getEventId() { return eventId; }
    public void setEventId(Integer eventId) { this.eventId = eventId; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean getIsUsed() { return isUsed; }
    public void setIsUsed(boolean isUsed) { this.isUsed = isUsed; }

    public String getEventName() { return eventName; }
    public void setEventName(String eventName) { this.eventName = eventName; }
}