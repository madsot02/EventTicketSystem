package dk.easv.eventticketsystem.GUI.model;

import dk.easv.eventticketsystem.BE.Event;
import dk.easv.eventticketsystem.BE.User;
import dk.easv.eventticketsystem.BLL.EventManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class EventModel {
    private EventManager eventManager;
    private ObservableList<Event> events;

    public EventModel() throws Exception {
        eventManager = new EventManager();
        events = FXCollections.observableArrayList();

        for (Event e : eventManager.getAllEvents()) {
            List<User> coords = eventManager.getCoordinatorsForEvent(e.getId());
            String names = coords.stream()
                    .map(User::getFullName)
                    .collect(java.util.stream.Collectors.joining(", "));
            e.setAssignedCoordinators(names);
            events.add(e);
        }
    }

    public ObservableList<Event> getObservableEvents() {
        return events;
    }

    public Event createEvent(Event createEvent) throws Exception {
        Event eventCreated = eventManager.createEvent(createEvent);
        events.add(eventCreated);
        return eventCreated;
    }

    public void updateEvent(Event updateEvent) throws Exception {
        eventManager.updateEvent(updateEvent);
        int index = events.indexOf(updateEvent);
        if (index != -1) {
            events.set(index, updateEvent);
        }
    }

    public void deleteEvent(Event selectedEvent) throws Exception {
        eventManager.deleteEvent(selectedEvent);
        selectedEvent.setIsDeleted(true);
        int index = events.indexOf(selectedEvent);
        if (index != -1) {
            events.set(index, selectedEvent);
        }
    }

    public void addCoordinatorToEvent(int eventId, int userId) throws Exception {
        eventManager.addCoordinatorToEvent(eventId, userId);
    }

    public void removeCoordinatorFromEvent(int eventId, int userId) throws Exception {
        eventManager.removeCoordinatorFromEvent(eventId, userId);
    }

    public List<User> getCoordinatorsForEvent(int eventId) throws Exception {
        return eventManager.getCoordinatorsForEvent(eventId);
    }

    public void refreshCoordinators(Event event) throws Exception {
        List<User> coords = eventManager.getCoordinatorsForEvent(event.getId());
        String names = coords.stream()
                .map(User::getFullName)
                .collect(java.util.stream.Collectors.joining(", "));
        event.setAssignedCoordinators(names);

        int index = events.indexOf(event);
        if (index != -1) {
            events.set(index, event);
        }
    }
}