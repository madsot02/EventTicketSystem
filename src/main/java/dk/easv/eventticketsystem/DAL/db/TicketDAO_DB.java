package dk.easv.eventticketsystem.DAL.db;

import dk.easv.eventticketsystem.BE.Ticket;
import dk.easv.eventticketsystem.BE.TicketType;
import dk.easv.eventticketsystem.BE.Voucher;
import dk.easv.eventticketsystem.DAL.interfaces.ITicketDataAccess;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO_DB implements ITicketDataAccess {
    private DBConnector databaseConnector = new DBConnector();

    public TicketDAO_DB() throws IOException {}



    @Override
    public TicketType createTicketType(TicketType ticketType) throws Exception {
        String sql = "INSERT INTO dbo.TicketTypes (eventId, typeName, description) VALUES (?, ?, ?)";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, ticketType.getEventId());
            stmt.setString(2, ticketType.getTypeName());
            stmt.setString(3, ticketType.getDescription());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) ticketType.setTicketTypeId(rs.getInt(1));
            }
            return ticketType;
        } catch (Exception ex) {
            throw new Exception("Could not create ticket type", ex);
        }
    }

    @Override
    public List<TicketType> getTicketTypesForEvent(int eventId) throws Exception {
        List<TicketType> list = new ArrayList<>();
        String sql = "SELECT * FROM dbo.TicketTypes WHERE eventId = ?";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, eventId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new TicketType(
                        rs.getInt("ticketTypeId"),
                        rs.getInt("eventId"),
                        rs.getString("typeName"),
                        rs.getString("description")
                ));
            }
            return list;
        } catch (Exception ex) {
            throw new Exception("Could not get ticket types", ex);
        }
    }

    @Override
    public void deleteTicketType(int ticketTypeId) throws Exception {
        String sql = "DELETE FROM dbo.TicketTypes WHERE ticketTypeId = ?";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ticketTypeId);
            stmt.executeUpdate();
        } catch (Exception ex) {
            throw new Exception("Could not delete ticket type", ex);
        }
    }



    @Override
    public Ticket createEventTicket(int eventId, int ticketTypeId) throws Exception {
        String uuid = java.util.UUID.randomUUID().toString();
        String sql = "INSERT INTO dbo.EventTickets (ticketUUID, eventId, ticketTypeId) VALUES (?, ?, ?)";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, uuid);
            stmt.setInt(2, eventId);
            stmt.setInt(3, ticketTypeId);
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return new Ticket(rs.getInt(1), uuid, eventId, ticketTypeId, false);
                }
            }
            throw new Exception("Could not create event ticket");
        } catch (Exception ex) {
            throw new Exception("Could not create event ticket", ex);
        }
    }

    @Override
    public List<Ticket> getTicketsForEvent(int eventId) throws Exception {
        List<Ticket> list = new ArrayList<>();
        String sql = "SELECT * FROM dbo.EventTickets WHERE eventId = ?";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, eventId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new Ticket(
                        rs.getInt("ticketId"),
                        rs.getString("ticketUUID"),
                        rs.getInt("eventId"),
                        rs.getInt("ticketTypeId"),
                        rs.getBoolean("isUsed")
                ));
            }
            return list;
        } catch (Exception ex) {
            throw new Exception("Could not get tickets for event", ex);
        }
    }

    @Override
    public void markTicketUsed(int ticketId) throws Exception {
        String sql = "UPDATE dbo.EventTickets SET isUsed = 1 WHERE ticketId = ?";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ticketId);
            stmt.executeUpdate();
        } catch (Exception ex) {
            throw new Exception("Could not mark ticket as used", ex);
        }
    }



    @Override
    public Voucher createVoucher(Voucher voucher) throws Exception {
        String uuid = java.util.UUID.randomUUID().toString();
        String sql = "INSERT INTO dbo.Vouchers (voucherUUID, eventId, description) VALUES (?, ?, ?)";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, uuid);
            if (voucher.getEventId() != null) stmt.setInt(2, voucher.getEventId());
            else stmt.setNull(2, Types.INTEGER);
            stmt.setString(3, voucher.getDescription());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    voucher.setVoucherId(rs.getInt(1));
                    voucher.setVoucherUUID(uuid);
                }
            }
            return voucher;
        } catch (Exception ex) {
            throw new Exception("Could not create voucher", ex);
        }
    }

    @Override
    public List<Voucher> getAllVouchers() throws Exception {
        List<Voucher> list = new ArrayList<>();
        String sql = "SELECT * FROM dbo.Vouchers";
        try (Connection conn = databaseConnector.getConnection();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int eid = rs.getInt("eventId");
                list.add(new Voucher(
                        rs.getInt("voucherId"),
                        rs.getString("voucherUUID"),
                        rs.wasNull() ? null : eid,
                        rs.getString("description"),
                        rs.getBoolean("isUsed")
                ));
            }
            return list;
        } catch (Exception ex) {
            throw new Exception("Could not get vouchers", ex);
        }
    }

    @Override
    public void markVoucherUsed(int voucherId) throws Exception {
        String sql = "UPDATE dbo.Vouchers SET isUsed = 1 WHERE voucherId = ?";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, voucherId);
            stmt.executeUpdate();
        } catch (Exception ex) {
            throw new Exception("Could not mark voucher as used", ex);
        }
    }
}