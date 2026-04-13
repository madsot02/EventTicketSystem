package dk.easv.eventticketsystem.BLL;

import dk.easv.eventticketsystem.BE.Ticket;
import dk.easv.eventticketsystem.DAL.db.TicketDAO_DB;
import dk.easv.eventticketsystem.DAL.interfaces.ITicketDataAccess;

import java.util.List;

public class TicketManager {
    private final ITicketDataAccess ticketDataAccess;

    public TicketManager() throws Exception {
        ticketDataAccess = new TicketDAO_DB();
    }

    public Ticket createTicket(Ticket ticket) throws Exception {
        return ticketDataAccess.createTicket(ticket);
    }
}