package dk.easv.eventticketsystem.GUI.model;

import dk.easv.eventticketsystem.BE.Event;
import dk.easv.eventticketsystem.BLL.EventManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EventModel{
    private EventManager eventManager;
    private ObservableList<Event> events;

    public EventModel() throws Exception{
        eventManager = new EventManager();

        events = FXCollections.observableArrayList();
        events.addAll(eventManager.getAllEvents());
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

        public void deleteEvent(Event selectedEvent) throws Exception{
            eventManager.deleteEvent(selectedEvent);
            events.remove(selectedEvent);
        }
}
