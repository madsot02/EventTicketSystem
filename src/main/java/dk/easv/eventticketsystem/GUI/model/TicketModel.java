package dk.easv.eventticketsystem.GUI.model;

import dk.easv.eventticketsystem.BE.Ticket;
import dk.easv.eventticketsystem.BLL.TicketManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TicketModel {
    private TicketManager ticketManager;
    private ObservableList<Ticket> tickets;

    public TicketModel(ObservableList<Ticket> tickets) throws Exception {
        this.tickets = tickets;
        ticketManager = new TicketManager();
    }

    public Ticket createTicket(Ticket ticket) throws Exception {
        Ticket created = ticketManager.createTicket(ticket);
        tickets.add(created);
        return created;
    }
}