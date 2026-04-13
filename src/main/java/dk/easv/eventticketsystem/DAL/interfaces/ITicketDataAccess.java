package dk.easv.eventticketsystem.DAL.interfaces;

import dk.easv.eventticketsystem.BE.Ticket;
import java.util.List;

public interface ITicketDataAccess {
    Ticket createTicket(Ticket ticket) throws Exception;
    List<Ticket> getTicketsForEvent(int eventId) throws Exception;
    List<Ticket> getAllVouchers() throws Exception;
    void markTicketUsed(int ticketId) throws Exception;
    void deleteTicket(int ticketId) throws Exception;
}