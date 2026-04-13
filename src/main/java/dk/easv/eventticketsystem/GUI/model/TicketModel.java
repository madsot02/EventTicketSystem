package dk.easv.eventticketsystem.GUI.model;

import dk.easv.eventticketsystem.BE.Ticket;
import dk.easv.eventticketsystem.BE.TicketType;
import dk.easv.eventticketsystem.BE.Voucher;
import dk.easv.eventticketsystem.BLL.TicketManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TicketModel {
    private TicketManager ticketManager;
    private ObservableList<Ticket> tickets;
    private ObservableList<Voucher> vouchers;
    private ObservableList<TicketType> ticketTypes;

    public TicketModel() throws Exception {
        ticketManager = new TicketManager();
        tickets       = FXCollections.observableArrayList();
        vouchers      = FXCollections.observableArrayList();
        ticketTypes   = FXCollections.observableArrayList();

        vouchers.addAll(ticketManager.getAllVouchers());
    }

    public ObservableList<Ticket> getObservableTickets() { return tickets; }
    public ObservableList<Voucher> getObservableVouchers() { return vouchers; }
    public ObservableList<TicketType> getObservableTicketTypes() { return ticketTypes; }

    public void loadTicketsForEvent(int eventId) throws Exception {
        tickets.clear();
        tickets.addAll(ticketManager.getTicketsForEvent(eventId));
    }

    public void loadTicketTypesForEvent(int eventId) throws Exception {
        ticketTypes.clear();
        ticketTypes.addAll(ticketManager.getTicketTypesForEvent(eventId));
    }

    public TicketType createTicketType(TicketType ticketType) throws Exception {
        TicketType created = ticketManager.createTicketType(ticketType);
        ticketTypes.add(created);
        return created;
    }

    public void deleteTicketType(TicketType ticketType) throws Exception {
        ticketManager.deleteTicketType(ticketType.getTicketTypeId());
        ticketTypes.remove(ticketType);
    }

    public Ticket createEventTicket(int eventId, int ticketTypeId) throws Exception {
        Ticket created = ticketManager.createEventTicket(eventId, ticketTypeId);
        tickets.add(created);
        return created;
    }

    public void markTicketUsed(Ticket ticket) throws Exception {
        ticketManager.markTicketUsed(ticket.getTicketId());
        ticket.setIsUsed(true);
        int index = tickets.indexOf(ticket);
        if (index != -1) tickets.set(index, ticket);
    }

    public Voucher createVoucher(Voucher voucher) throws Exception {
        Voucher created = ticketManager.createVoucher(voucher);
        vouchers.add(created);
        return created;
    }

    public void markVoucherUsed(Voucher voucher) throws Exception {
        ticketManager.markVoucherUsed(voucher.getVoucherId());
        voucher.setIsUsed(true);
        int index = vouchers.indexOf(voucher);
        if (index != -1) vouchers.set(index, voucher);
    }
}