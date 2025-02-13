import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ApartmentService {
    public void searchApartments(String orderBy) {
        String query = "SELECT * FROM apartments ORDER BY " + orderBy;
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("Apartment ID: " + rs.getInt("id") +
                        ", Title: " + rs.getString("title") +
                        ", Price: " + rs.getBigDecimal("price") +
                        ", Rooms: " + rs.getInt("rooms") +
                        ", Rating: " + rs.getDouble("rating"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean bookApartment(int userId, int apartmentId) {
        String query = "UPDATE apartments SET is_booked = TRUE WHERE id = ? AND is_booked = FALSE";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, apartmentId);
            if (stmt.executeUpdate() > 0) {
                String bookingQuery = "INSERT INTO bookings (user_id, apartment_id) VALUES (?, ?)";
                try (PreparedStatement bookingStmt = conn.prepareStatement(bookingQuery)) {
                    bookingStmt.setInt(1, userId);
                    bookingStmt.setInt(2, apartmentId);
                    return bookingStmt.executeUpdate() > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean cancelBooking(int userId, int apartmentId) {
        String query = "DELETE FROM bookings WHERE user_id = ? AND apartment_id = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, apartmentId);
            if (stmt.executeUpdate() > 0) {
                String updateQuery = "UPDATE apartments SET is_booked = FALSE WHERE id = ?";
                try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                    updateStmt.setInt(1, apartmentId);
                    return updateStmt.executeUpdate() > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean leaveReview(int id, int apartmentIdToRate, int rating) {
        return false;
    }
}
