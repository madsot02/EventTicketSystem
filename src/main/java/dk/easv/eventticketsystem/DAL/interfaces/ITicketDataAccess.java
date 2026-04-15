package dk.easv.eventticketsystem.DAL.interfaces;

//project imports
import dk.easv.eventticketsystem.BE.Ticket;

public interface ITicketDataAccess {
    Ticket createTicket(Ticket ticket) throws Exception;
}