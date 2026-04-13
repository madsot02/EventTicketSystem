package dk.easv.eventticketsystem.GUI.model;

import dk.easv.eventticketsystem.BE.Ticket;
import dk.easv.eventticketsystem.BLL.TicketManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TicketModel {
    private TicketManager ticketManager;
    private ObservableList<Ticket> tickets;
    private ObservableList<Ticket> vouchers;

    public TicketModel() throws Exception {
        ticketManager = new TicketManager();
        tickets  = FXCollections.observableArrayList();
        vouchers = FXCollections.observableArrayList();

        vouchers.addAll(ticketManager.getAllVouchers());
    }

    public ObservableList<Ticket> getObservableTickets() { return tickets; }
    public ObservableList<Ticket> getObservableVouchers() { return vouchers; }

    public void loadTicketsForEvent(int eventId) throws Exception {
        tickets.clear();
        tickets.addAll(ticketManager.getTicketsForEvent(eventId));
    }

    public Ticket createTicket(Ticket ticket) throws Exception {
        Ticket created = ticketManager.createTicket(ticket);
        if (created.getIsVoucher()) vouchers.add(created);
        else tickets.add(created);
        return created;
    }

    public void markTicketUsed(Ticket ticket) throws Exception {
        ticketManager.markTicketUsed(ticket.getTicketId());
        ticket.setIsUsed(true);
        ObservableList<Ticket> list = ticket.getIsVoucher() ? vouchers : tickets;
        int index = list.indexOf(ticket);
        if (index != -1) list.set(index, ticket);
    }

    public void deleteTicket(Ticket ticket) throws Exception {
        ticketManager.deleteTicket(ticket.getTicketId());
        if (ticket.getIsVoucher()) vouchers.remove(ticket);
        else tickets.remove(ticket);
    }
}