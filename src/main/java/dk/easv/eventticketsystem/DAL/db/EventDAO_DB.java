package dk.easv.eventticketsystem.DAL.db;

import dk.easv.eventticketsystem.BE.Event;
import dk.easv.eventticketsystem.DAL.interfaces.IEventDataAccess;

import java.io.IOException;
import java.sql.*;
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

            statementHelper(newEvent, stmt);

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
                rs.getString("endTime"),
                rs.getBoolean("isDeleted"));

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
       String sql ="UPDATE dbo.Events SET name = ?, description = ? , location = ?, ticketsAvailable= ?, startDate = ?, endDate = ?, startTime = ?, endTime = ? WHERE eventId = ? ;";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            statementHelper(updateEvent, stmt);

            stmt.setInt(9,updateEvent.getId());

            stmt.executeUpdate();



        } catch (
                Exception ex) {
            ex.printStackTrace();
            throw new Exception("Could not edit event", ex);
        }

    }

    @Override
    public void deleteEvent(int eventId) throws Exception {
        String sql = "UPDATE dbo.Events SET isDeleted = 1 WHERE eventId = ?";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, eventId);
            stmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Could not delete event", ex);
        }
    }

    private void statementHelper(Event newEvent, PreparedStatement stmt) throws SQLException {
        stmt.setString(1, newEvent.getName());
        stmt.setString(2, newEvent.getLocation());
        stmt.setString(3, newEvent.getDescription());
        stmt.setInt(4, newEvent.getTicketsAvailable());
        stmt.setDate(5, Date.valueOf(newEvent.getStartDate()));
        stmt.setDate(6, Date.valueOf(newEvent.getEndDate()));
        stmt.setString(7, newEvent.getStartTime());
        stmt.setString(8, newEvent.getEndTime());
    }
}
