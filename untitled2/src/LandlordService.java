import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LandlordService {

    public boolean addApartment(int landlordId, String title, double price, int rooms, String description) {
        String query = "INSERT INTO apartments (landlord_id, title, price, rooms, description, is_booked) VALUES (?, ?, ?, ?, ?, FALSE)";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, landlordId);
            stmt.setString(2, title);
            stmt.setDouble(3, price);
            stmt.setInt(4, rooms);
            stmt.setString(5, description);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean removeApartment(int landlordId, int apartmentId) {
        String query = "DELETE FROM apartments WHERE id = ? AND landlord_id = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, apartmentId);
            stmt.setInt(2, landlordId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean editApartment(int landlordId, int apartmentId, String title, double price, int rooms, String description) {
        String query = "UPDATE apartments SET title = ?, price = ?, rooms = ?, description = ? WHERE id = ? AND landlord_id = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, title);
            stmt.setDouble(2, price);
            stmt.setInt(3, rooms);
            stmt.setString(4, description);
            stmt.setInt(5, apartmentId);
            stmt.setInt(6, landlordId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean rateUser(int landlordId, int userId, int rating) {
        String query = "INSERT INTO landlord_ratings (landlord_id, user_id, rating) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, landlordId);
            stmt.setInt(2, userId);
            stmt.setInt(3, rating);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

