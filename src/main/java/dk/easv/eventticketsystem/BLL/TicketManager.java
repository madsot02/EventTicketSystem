package dk.easv.eventticketsystem.BLL;

import dk.easv.eventticketsystem.BE.Ticket;
import dk.easv.eventticketsystem.BE.TicketType;
import dk.easv.eventticketsystem.BE.Voucher;
import dk.easv.eventticketsystem.DAL.db.TicketDAO_DB;
import dk.easv.eventticketsystem.DAL.interfaces.ITicketDataAccess;

import java.util.List;

public class TicketManager {
    private final ITicketDataAccess ticketDataAccess;

    public TicketManager() throws Exception {
        ticketDataAccess = new TicketDAO_DB();
    }

    public TicketType createTicketType(TicketType ticketType) throws Exception {
        return ticketDataAccess.createTicketType(ticketType);
    }

    public List<TicketType> getTicketTypesForEvent(int eventId) throws Exception {
        return ticketDataAccess.getTicketTypesForEvent(eventId);
    }

    public void deleteTicketType(int ticketTypeId) throws Exception {
        ticketDataAccess.deleteTicketType(ticketTypeId);
    }

    public Ticket createEventTicket(int eventId, int ticketTypeId) throws Exception {
        return ticketDataAccess.createEventTicket(eventId, ticketTypeId);
    }

    public List<Ticket> getTicketsForEvent(int eventId) throws Exception {
        return ticketDataAccess.getTicketsForEvent(eventId);
    }

    public void markTicketUsed(int ticketId) throws Exception {
        ticketDataAccess.markTicketUsed(ticketId);
    }

    public Voucher createVoucher(Voucher voucher) throws Exception {
        return ticketDataAccess.createVoucher(voucher);
    }

    public List<Voucher> getAllVouchers() throws Exception {
        return ticketDataAccess.getAllVouchers();
    }

    public void markVoucherUsed(int voucherId) throws Exception {
        ticketDataAccess.markVoucherUsed(voucherId);
    }
}