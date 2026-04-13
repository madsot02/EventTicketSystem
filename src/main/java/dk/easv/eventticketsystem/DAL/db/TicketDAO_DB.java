package dk.easv.eventticketsystem.DAL.db;

import dk.easv.eventticketsystem.BE.Ticket;
import dk.easv.eventticketsystem.DAL.interfaces.ITicketDataAccess;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO_DB implements ITicketDataAccess {
    private DBConnector databaseConnector = new DBConnector();

    public TicketDAO_DB() throws IOException {}

    @Override
    public Ticket createTicket(Ticket ticket) throws Exception {
        String uuid = java.util.UUID.randomUUID().toString();
        String sql = "INSERT INTO dbo.Tickets (ticketUUID, eventId, typeName, isVoucher) VALUES (?, ?, ?, ?)";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, uuid);
            if (ticket.getEventId() != null) stmt.setInt(2, ticket.getEventId());
            else stmt.setNull(2, Types.INTEGER);
            stmt.setString(3, ticket.getTypeName());
            stmt.setBoolean(4, ticket.getIsVoucher());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    ticket.setTicketId(rs.getInt(1));
                    ticket.setTicketUUID(uuid);
                }
            }
            return ticket;
        } catch (Exception ex) {
            throw new Exception("Could not create ticket", ex);
        }
    }

    @Override
    public List<Ticket> getTicketsForEvent(int eventId) throws Exception {
        List<Ticket> list = new ArrayList<>();
        String sql = "SELECT * FROM dbo.Tickets WHERE eventId = ? AND isVoucher = 0";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, eventId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
            return list;
        } catch (Exception ex) {
            throw new Exception("Could not get tickets", ex);
        }
    }

    @Override
    public List<Ticket> getAllVouchers() throws Exception {
        List<Ticket> list = new ArrayList<>();
        String sql = "SELECT * FROM dbo.Tickets WHERE isVoucher = 1";
        try (Connection conn = databaseConnector.getConnection();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(mapRow(rs));
            }
            return list;
        } catch (Exception ex) {
            throw new Exception("Could not get vouchers", ex);
        }
    }

    @Override
    public void markTicketUsed(int ticketId) throws Exception {
        String sql = "UPDATE dbo.Tickets SET isUsed = 1 WHERE ticketId = ?";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ticketId);
            stmt.executeUpdate();
        } catch (Exception ex) {
            throw new Exception("Could not mark ticket as used", ex);
        }
    }

    @Override
    public void deleteTicket(int ticketId) throws Exception {
        String sql = "DELETE FROM dbo.Tickets WHERE ticketId = ?";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ticketId);
            stmt.executeUpdate();
        } catch (Exception ex) {
            throw new Exception("Could not delete ticket", ex);
        }
    }

    private Ticket mapRow(ResultSet rs) throws SQLException {
        int eid = rs.getInt("eventId");
        return new Ticket(
                rs.getInt("ticketId"),
                rs.getString("ticketUUID"),
                rs.wasNull() ? null : eid,
                rs.getString("typeName"),
                rs.getBoolean("isVoucher"),
                rs.getBoolean("isUsed")
        );
    }
}