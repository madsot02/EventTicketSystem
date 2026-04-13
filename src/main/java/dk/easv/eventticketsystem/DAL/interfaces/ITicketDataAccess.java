package dk.easv.eventticketsystem.DAL.interfaces;

import dk.easv.eventticketsystem.BE.Ticket;
import dk.easv.eventticketsystem.BE.TicketType;
import dk.easv.eventticketsystem.BE.Voucher;

import java.util.List;

public interface ITicketDataAccess {
    TicketType createTicketType(TicketType ticketType) throws Exception;
    List<TicketType> getTicketTypesForEvent(int eventId) throws Exception;
    void deleteTicketType(int ticketTypeId) throws Exception;

    Ticket createEventTicket(int eventId, int ticketTypeId) throws Exception;
    List<Ticket> getTicketsForEvent(int eventId) throws Exception;
    void markTicketUsed(int ticketId) throws Exception;

    Voucher createVoucher(Voucher voucher) throws Exception;
    List<Voucher> getAllVouchers() throws Exception;
    void markVoucherUsed(int voucherId) throws Exception;
}
