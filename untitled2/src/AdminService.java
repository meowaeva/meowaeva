import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminService {

    public boolean deleteUser(int userId) {
        String query = "DELETE FROM users WHERE id = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean blockUser(int userId) {
        String query = "UPDATE users SET is_blocked = TRUE WHERE id = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteApartment(int apartmentId) {
        String query = "DELETE FROM apartments WHERE id = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, apartmentId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void viewStatistics() {
        String userStatsQuery = "SELECT role, COUNT(*) AS count FROM users GROUP BY role";
        String apartmentStatsQuery = "SELECT COUNT(*) AS total_apartments, SUM(CASE WHEN is_booked THEN 1 ELSE 0 END) AS booked_apartments FROM apartments";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement userStmt = conn.prepareStatement(userStatsQuery);
             PreparedStatement apartmentStmt = conn.prepareStatement(apartmentStatsQuery);
             ResultSet userRs = userStmt.executeQuery();
             ResultSet apartmentRs = apartmentStmt.executeQuery()) {

            System.out.println("User Statistics:");
            while (userRs.next()) {
                System.out.println(userRs.getString("role") + ": " + userRs.getInt("count"));
            }

            System.out.println("Apartment Statistics:");
            if (apartmentRs.next()) {
                System.out.println("Total Apartments: " + apartmentRs.getInt("total_apartments"));
                System.out.println("Booked Apartments: " + apartmentRs.getInt("booked_apartments"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

