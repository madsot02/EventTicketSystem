package dk.easv.eventticketsystem.BLL;

//project imports
import dk.easv.eventticketsystem.BE.TicketType;
import dk.easv.eventticketsystem.DAL.db.TicketTypeDAO_DB;
import dk.easv.eventticketsystem.DAL.interfaces.ITicketTypeDataAccess;

//java imports
import java.util.List;

public class TicketTypeManager {
    private final ITicketTypeDataAccess ticketTypeDataAccess;

    public TicketTypeManager() throws Exception {
        ticketTypeDataAccess = new TicketTypeDAO_DB();
    }

    public TicketType createTicketType(TicketType type) throws Exception {
        return ticketTypeDataAccess.createTicketType(type);
    }

    public List<TicketType> getTicketTypesForEvent(int eventId) throws Exception {
        return ticketTypeDataAccess.getTicketTypesForEvent(eventId);
    }

    public void deleteTicketType(int typeId) throws Exception {
        ticketTypeDataAccess.deleteTicketType(typeId);
    }
}