package dk.easv.eventticketsystem.GUI.model;

//project imports
import dk.easv.eventticketsystem.BE.TicketType;
import dk.easv.eventticketsystem.BLL.TicketTypeManager;

//java imports
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TicketTypeModel {
    private final TicketTypeManager manager;
    private ObservableList<TicketType> ticketTypes;

    public TicketTypeModel() throws Exception {
        manager = new TicketTypeManager();
        ticketTypes = FXCollections.observableArrayList();
    }

    public void loadForEvent(int eventId) throws Exception {
        ticketTypes.setAll(manager.getTicketTypesForEvent(eventId));
    }

    public ObservableList<TicketType> getTicketTypes() {
        return ticketTypes;
    }

    public TicketType createTicketType(TicketType type) throws Exception {
        TicketType created = manager.createTicketType(type);
        ticketTypes.add(created);
        return created;
    }

    public void deleteTicketType(TicketType type) throws Exception {
        manager.deleteTicketType(type.getTypeId());
        ticketTypes.remove(type);
    }
}