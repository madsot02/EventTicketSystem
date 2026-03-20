package dk.easv.eventticketsystem.DAL.interfaces;

import dk.easv.eventticketsystem.BE.Event;

import java.util.List;

public interface IEventDataAccess {
    Event createEvent(Event newEvent) throws Exception;
    List<Event> getAllEvents() throws  Exception;
    void updateEvent(Event updateEvent) throws Exception;
    void deleteEvent(int eventId) throws Exception;
}
