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

    public List<Ticket> getTicketsForEvent(int eventId) throws Exception {
        return ticketDataAccess.getTicketsForEvent(eventId);
    }

    public List<Ticket> getAllVouchers() throws Exception {
        return ticketDataAccess.getAllVouchers();
    }

    public void markTicketUsed(int ticketId) throws Exception {
        ticketDataAccess.markTicketUsed(ticketId);
    }

    public void deleteTicket(int ticketId) throws Exception {
        ticketDataAccess.deleteTicket(ticketId);
    }
}