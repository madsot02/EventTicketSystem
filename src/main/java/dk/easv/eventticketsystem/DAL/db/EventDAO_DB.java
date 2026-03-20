package dk.easv.eventticketsystem.DAL.db;

import dk.easv.eventticketsystem.BE.Event;
import dk.easv.eventticketsystem.DAL.interfaces.IEventDataAccess;

import java.util.List;

public class EventDAO_DB implements IEventDataAccess{
    @Override
    public Event createEvent(Event newEvent) throws Exception {
        return null;
    }

    @Override
    public List<Event> getAllEvent() throws Exception {
        return List.of();
    }

    @Override
    public void updateEvent(Event updateEvent) throws Exception {

    }

    @Override
    public void deleteEvent(int eventId) throws Exception {

    }
}
