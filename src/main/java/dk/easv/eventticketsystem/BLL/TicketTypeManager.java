package dk.easv.eventticketsystem.BLL;

import dk.easv.eventticketsystem.BE.TicketType;
import dk.easv.eventticketsystem.DAL.db.TicketTypeDAO_DB;
import dk.easv.eventticketsystem.DAL.interfaces.ITicketTypeDataAccess;

import java.util.List;

public class TicketTypeManager {
    private final ITicketTypeDataAccess dao;

    public TicketTypeManager() throws Exception {
        dao = new TicketTypeDAO_DB();
    }

    public TicketType createTicketType(TicketType type) throws Exception {
        return dao.createTicketType(type);
    }

    public List<TicketType> getTicketTypesForEvent(int eventId) throws Exception {
        return dao.getTicketTypesForEvent(eventId);
    }

    public void deleteTicketType(int typeId) throws Exception {
        dao.deleteTicketType(typeId);
    }
}