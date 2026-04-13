package dk.easv.eventticketsystem.DAL.db;

import dk.easv.eventticketsystem.BE.TicketType;
import dk.easv.eventticketsystem.DAL.interfaces.ITicketTypeDataAccess;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketTypeDAO_DB implements ITicketTypeDataAccess {
    private DBConnector databaseConnector = new DBConnector();

    public TicketTypeDAO_DB() throws IOException {}

    @Override
    public TicketType createTicketType(TicketType type) throws Exception {
        String sql = "INSERT INTO dbo.TicketTypes (eventId, typeName, price, isVoucher) VALUES (?, ?, ?, ?)";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            if (type.getEventId() != null) stmt.setInt(1, type.getEventId());
            else stmt.setNull(1, Types.INTEGER);
            stmt.setString(2, type.getTypeName());
            stmt.setDouble(3, type.getPrice());
            stmt.setBoolean(4, type.isVoucher());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return new TicketType(rs.getInt(1), type.getEventId(), type.getTypeName(), type.getPrice(), type.isVoucher());
                }
            }
            throw new Exception("No ID returned");
        } catch (Exception ex) {
            throw new Exception("Could not create ticket type", ex);
        }
    }

    @Override
    public List<TicketType> getTicketTypesForEvent(int eventId) throws Exception {
        List<TicketType> types = new ArrayList<>();
        String sql = "SELECT * FROM dbo.TicketTypes WHERE eventId = ? OR eventId IS NULL";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, eventId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int eid = rs.getInt("eventId");
                types.add(new TicketType(
                        rs.getInt("typeId"),
                        rs.wasNull() ? null : eid,
                        rs.getString("typeName"),
                        rs.getDouble("price"),
                        rs.getBoolean("isVoucher")
                ));
            }
            return types;
        } catch (Exception ex) {
            throw new Exception("Could not get ticket types", ex);
        }
    }

    @Override
    public void deleteTicketType(int typeId) throws Exception {
        String sql = "DELETE FROM dbo.TicketTypes WHERE typeId = ?";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, typeId);
            stmt.executeUpdate();
        } catch (Exception ex) {
            throw new Exception("Could not delete ticket type", ex);
        }
    }
}