package dk.easv.eventticketsystem.BE;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class CartItem {
    private final TicketType ticketType;
    private final IntegerProperty amount;
    private final DoubleProperty subtotal;

    public CartItem(TicketType ticketType, int amount) {
        this.ticketType = ticketType;
        this.amount = new SimpleIntegerProperty(amount);
        this.subtotal = new SimpleDoubleProperty(ticketType.getPrice() * amount);

        this.amount.addListener((obs, oldVal, newVal) ->
                this.subtotal.set(ticketType.getPrice() * newVal.intValue()));
    }

    public TicketType getTicketType()        { return ticketType; }
    public String getTypeName()              { return ticketType.getTypeName(); }
    public double getPrice()                 { return ticketType.getPrice(); }
    public int getAmount()                   { return amount.get(); }
    public IntegerProperty amountProperty()  { return amount; }
    public double getSubtotal()              { return subtotal.get(); }
    public DoubleProperty subtotalProperty() { return subtotal; }
}