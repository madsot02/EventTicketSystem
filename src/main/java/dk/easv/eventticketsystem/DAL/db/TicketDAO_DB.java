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
        String sql = "INSERT INTO dbo.Tickets (ticketUUID, eventId, typeName, isUsed, customerName, customerEmail, price) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, uuid);
            if (ticket.getEventId() != null) stmt.setInt(2, ticket.getEventId());
            else stmt.setNull(2, Types.INTEGER);
            stmt.setString(3, ticket.getTypeName());

            stmt.setBoolean(4, false );
            stmt.setString(5,ticket.getCustomerName());
            stmt.setString(6,ticket.getCustomerEmail());
            stmt.setDouble(7,ticket.getPrice());
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

    public boolean markTicketAsUsed(String uuid) throws Exception {

        String checkSql = "SELECT isUsed FROM dbo.Tickets WHERE ticketUUID = ?";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(checkSql)) {
            stmt.setString(1, uuid);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) throw new Exception("Ticket not found: " + uuid);
            boolean alreadyUsed = rs.getBoolean("isUsed");
            if (alreadyUsed) return true;
        }


        String updateSql = "UPDATE dbo.Tickets SET isUsed = 1 WHERE ticketUUID = ?";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(updateSql)) {
            stmt.setString(1, uuid);
            stmt.executeUpdate();
        }
        return false;
    }

   /* private Ticket mapRow(ResultSet rs) throws SQLException {
        int eid = rs.getInt("eventId");
        return new Ticket(
                rs.getInt("ticketId"),
                rs.getString("ticketUUID"),
                rs.wasNull() ? null : eid,
                rs.getString("typeName"),
                rs.getBoolean("isVoucher"),
                rs.getBoolean("isUsed")
        );
    }*/
}