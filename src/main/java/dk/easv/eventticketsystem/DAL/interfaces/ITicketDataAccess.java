    package dk.easv.eventticketsystem.DAL.interfaces;

    import dk.easv.eventticketsystem.BE.Ticket;
    import java.util.List;

    public interface ITicketDataAccess {
        Ticket createTicket(Ticket ticket) throws Exception;
    }