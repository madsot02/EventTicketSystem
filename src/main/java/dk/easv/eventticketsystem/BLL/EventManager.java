package dk.easv.eventticketsystem.BLL;

import dk.easv.eventticketsystem.BE.Event;
import dk.easv.eventticketsystem.DAL.db.EventDAO_DB;
import dk.easv.eventticketsystem.DAL.interfaces.IEventDataAccess;

public class EventManager{
    private IEventDataAccess eventDataAccess;

    public EventManager() throws Exception{
        eventDataAccess = new EventDAO_DB();
    }
}
