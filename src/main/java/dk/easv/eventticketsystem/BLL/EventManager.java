package dk.easv.eventticketsystem.BLL;

import dk.easv.eventticketsystem.BE.Event;
import dk.easv.eventticketsystem.DAL.db.EventDAO_DB;
import dk.easv.eventticketsystem.DAL.interfaces.IEventDataAccess;

import java.util.List;

public class EventManager{
    private final IEventDataAccess eventDataAccess;

    public EventManager() throws Exception{
        eventDataAccess = new EventDAO_DB();
    }

    public List<Event> getAllEvents() throws Exception{
        return eventDataAccess.getAllEvents();
    }
    public Event createEvent(Event newEvent) throws Exception{
        return eventDataAccess.createEvent(newEvent);
    }
    public void updateEvent(Event updateEvent) throws Exception{
        eventDataAccess.updateEvent(updateEvent);
    }
    public void deleteEvent(Event selectedEvent) throws Exception{
        eventDataAccess.deleteEvent(selectedEvent.getId());
    }
}
