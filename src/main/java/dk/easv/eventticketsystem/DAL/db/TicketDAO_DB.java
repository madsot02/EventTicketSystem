package dk.easv.eventticketsystem.DAL.db;

//project imports
import dk.easv.eventticketsystem.BE.Ticket;
import dk.easv.eventticketsystem.DAL.interfaces.ITicketDataAccess;

//java imports
import java.io.IOException;
import java.sql.*;

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
}