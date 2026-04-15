package dk.easv.eventticketsystem.DAL.interfaces;

//project imports
import dk.easv.eventticketsystem.BE.TicketType;

//java imports
import java.util.List;

public interface ITicketTypeDataAccess {
    TicketType createTicketType(TicketType type) throws Exception;
    List<TicketType> getTicketTypesForEvent(int eventId) throws Exception;
    void deleteTicketType(int typeId) throws Exception;
}