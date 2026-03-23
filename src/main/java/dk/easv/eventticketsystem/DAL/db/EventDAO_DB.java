package dk.easv.eventticketsystem.DAL.db;

import dk.easv.eventticketsystem.BE.Event;
import dk.easv.eventticketsystem.DAL.interfaces.IEventDataAccess;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventDAO_DB implements IEventDataAccess {
    private DBConnector databaseConnector = new DBConnector();

    public EventDAO_DB() throws IOException {
    }

    @Override
    public Event createEvent(Event newEvent) throws Exception {
        String sql = "INSERT INTO dbo.Events (name, description, location, ticketsAvailable, startDate, endDate, startTime, endTime) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, newEvent.getName());
            stmt.setString(2, newEvent.getLocation());
            stmt.setString(3, newEvent.getDescription());
            stmt.setDate(4, java.sql.Date.valueOf(newEvent.getStartDate()));
            stmt.setDate(5, java.sql.Date.valueOf(newEvent.getEndDate()));
            stmt.setString(6, newEvent.getStartTime());
            stmt.setString(7, newEvent.getEndTime());
            stmt.setInt(8, newEvent.getTicketsAvailable());

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
        List<Event> allEvents = new ArrayList<>();

        try(Connection conn = databaseConnector.getConnection();
            Statement stmt = conn.createStatement()) {
            String sql = "SELECT * FROM dbo.Events;";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Event event = new Event(
                rs.getInt("eventId"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getString("location"),
                rs.getInt("ticketsAvailable"),
                rs.getDate("startDate").toLocalDate(),
                rs.getDate("endDate").toLocalDate(),
                rs.getString("startTime"),
                rs.getString("endTime"));

                allEvents.add(event);
            }
            return allEvents;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Could not get movies", ex);
        }
    }

    @Override
    public void updateEvent(Event updateEvent) throws Exception {

    }

    @Override
    public void deleteEvent(int eventId) throws Exception {

    }
}
