package dk.easv.eventticketsystem.DAL.db;

import dk.easv.eventticketsystem.BE.Event;
import dk.easv.eventticketsystem.BE.Role;
import dk.easv.eventticketsystem.BE.User;
import dk.easv.eventticketsystem.DAL.interfaces.IUserDataAccess;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class UserDAO_DB implements IUserDataAccess {
    private DBConnector databaseConnector = new DBConnector();

    public UserDAO_DB() throws IOException {
    }

    @Override
    public User getUserByUsername(String username) throws Exception {
        String sql = "SELECT * FROM dbo.Users WHERE username = ?;";

        try (Connection conn = databaseConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                return new User(
                        rs.getInt("userId"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("username"),
                        rs.getString("password"),
                        Role.valueOf(rs.getString("role")));
            }
            return null;
        }


    }

    @Override
    public User createUser(User newUser) throws Exception {
        String sql = "INSERT INTO dbo.Users (firstName, lastName, username, password, role) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, newUser.getFirstName());
            stmt.setString(2, newUser.getLastName());
            stmt.setString(3, newUser.getUsername());
            stmt.setString(4, newUser.getPassword());
            stmt.setString(5, newUser.getRole().toString());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            int userId;

            if (rs.next()) {
                userId = rs.getInt(1);
            } else {
                throw new Exception("Creating user failed, no ID obtained");
            }
            return new User(userId,
                    newUser.getFirstName(),
                    newUser.getLastName(),
                    newUser.getUsername(),
                    newUser.getPassword(),
                    newUser.getRole());
        }
    }

    @Override
    public List<User> getAllUsers() throws Exception {
        List<User> allUsers = new ArrayList<>();

        try(Connection conn = databaseConnector.getConnection();
            Statement stmt = conn.createStatement()) {
            String sql = "SELECT * FROM dbo.Users;";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                User user = new User(
                        rs.getInt("userId"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("username"),
                        rs.getString("password"),
                        Role.valueOf(rs.getString("role")));
                allUsers.add(user);
            }
            return allUsers;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Could not get users", ex);
        }
    }

    @Override
    public void deleteUser(int userId) throws Exception {
        String sql = "DELETE FROM dbo.Users WHERE userId = ?;";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Could not delete user from database.",ex);
        }
    }

}
