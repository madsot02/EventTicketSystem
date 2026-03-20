package dk.easv.eventticketsystem.DAL.db;

import dk.easv.eventticketsystem.BE.Event;
import dk.easv.eventticketsystem.DAL.interfaces.IEventDataAccess;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class EventDAO_DB implements IEventDataAccess {
    private DBConnector databaseConnector = new DBConnector();

    public EventDAO_DB() throws IOException {
    }

    @Override
    public Event createEvent(Event newEvent) throws Exception {
        String sql = "INSERT INTO dbo.Event (name, description, location, createdBy, ticketsAvailable, date, time) VALUES (?, ?, ?, ?, ?, ?, ?);";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, newEvent.getName());
            stmt.setString(2, newEvent.getDescription());
            stmt.setString(3, newEvent.getLocation());
            stmt.setInt(4, newEvent.getCreatedBy());
            stmt.setInt(5, newEvent.getTicketsAvailable());
            stmt.setDate(6, java.sql.Date.valueOf(newEvent.getDate()));
            stmt.setInt(6, newEvent.getTime());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next())
                    newEvent.setId(rs.getInt(1));
            }
            return newEvent;

        } catch (
                Exception ex) {
            ex.printStackTrace();
            throw new Exception("Could not create event", ex);
        }
    }
    @Override
    public List<Event> getAllEvents() throws Exception {
        return List.of();
    }

    @Override
    public void updateEvent(Event updateEvent) throws Exception {

    }

    @Override
    public void deleteEvent(int eventId) throws Exception {

    }
}
