public class User {
    private int id;
    private String username;
    private String role;
    private String rating;

    public User(int id, String username, String role, String rating) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public String getRating() {
        return rating;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setRating(double rating) {
        this.rating = String.valueOf(rating);
    }
}

