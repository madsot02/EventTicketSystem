package dk.easv.eventticketsystem.BLL;

//project imports
import dk.easv.eventticketsystem.BE.Ticket;
import dk.easv.eventticketsystem.DAL.db.TicketDAO_DB;
import dk.easv.eventticketsystem.DAL.interfaces.ITicketDataAccess;

public class TicketManager {
    private final ITicketDataAccess ticketDataAccess;

    public TicketManager() throws Exception {
        ticketDataAccess = new TicketDAO_DB();
    }

    public Ticket createTicket(Ticket ticket) throws Exception {
        return ticketDataAccess.createTicket(ticket);
    }
}