package dk.easv.eventticketsystem.BE;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class CartItem {
    private final TicketType ticketType;
    private final IntegerProperty amount;

    public CartItem(TicketType ticketType, int amount) {
        this.ticketType = ticketType;
        this.amount = new SimpleIntegerProperty(amount);
    }

    public TicketType getTicketType()        { return ticketType; }
    public String getTypeName()              { return ticketType.getTypeName(); }
    public double getPrice()                 { return ticketType.getPrice(); }
    public int getAmount()                   { return amount.get(); }
    public IntegerProperty amountProperty()  { return amount; }

    // Subtotal for denne linje
    public double getSubtotal()              { return ticketType.getPrice() * amount.get(); }
}