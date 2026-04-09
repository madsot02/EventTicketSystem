package dk.easv.eventticketsystem.DAL.interfaces;

import dk.easv.eventticketsystem.BE.Event;
import dk.easv.eventticketsystem.BE.User;

import java.util.List;

public interface IEventDataAccess {
    Event createEvent(Event newEvent) throws Exception;
    List<Event> getAllEvents() throws  Exception;
    void updateEvent(Event updateEvent) throws Exception;
    void deleteEvent(int eventId) throws Exception;
    void addCoordinatorToEvent(int eventId, int userId) throws Exception;
    void removeCoordinatorFromEvent(int eventId, int userId) throws Exception;
    List<User> getCoordinatorsForEvent(int eventId) throws Exception;
}
