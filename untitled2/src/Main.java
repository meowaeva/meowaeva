import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AuthService authService = new AuthService();
        ApartmentService apartmentService = new ApartmentService();
        LandlordService landlordService = new LandlordService();
        AdminService adminService = new AdminService();
        Scanner scanner = new Scanner(System.in);

        System.out.println("1. Login\n2. Register");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            User user = authService.login(username, password);

            if (user != null) {
                System.out.println("Welcome, " + user.getUsername() + "! Role: " + user.getRole());
                if (user.getRole().equals("User")) {
                    boolean userActive = true;
                    while (userActive) {
                        System.out.println("1. Search Apartments\n2. Book Apartment\n3. Cancel Booking\n4. Leave a Review\n5. Logout");
                        int userChoice = scanner.nextInt();
                        scanner.nextLine();

                        switch (userChoice) {
                            case 1:
                                System.out.println("Sort by: 1. Price 2. Rooms 3. Rating");
                                int sortOption = scanner.nextInt();
                                scanner.nextLine();
                                String orderBy = switch (sortOption) {
                                    case 1 -> "price";
                                    case 2 -> "rooms";
                                    case 3 -> "rating";
                                    default -> "price";
                                };
                                apartmentService.searchApartments(orderBy);
                                break;
                            case 2:
                                System.out.println("Enter Apartment ID to book:");
                                int apartmentIdToBook = scanner.nextInt();
                                if (apartmentService.bookApartment(user.getId(), apartmentIdToBook)) {
                                    System.out.println("Apartment booked successfully!");
                                } else {
                                    System.out.println("Failed to book apartment.");
                                }
                                break;
                            case 3:
                                System.out.println("Enter Apartment ID to cancel booking:");
                                int apartmentIdToCancel = scanner.nextInt();
                                if (apartmentService.cancelBooking(user.getId(), apartmentIdToCancel)) {
                                    System.out.println("Booking cancelled successfully!");
                                } else {
                                    System.out.println("Failed to cancel booking.");
                                }
                                break;
                            case 4:
                                System.out.println("Enter Apartment ID and your rating (1-5):");
                                int apartmentIdToRate = scanner.nextInt();
                                int rating = scanner.nextInt();
                                if (apartmentService.leaveReview(user.getId(), apartmentIdToRate, rating)) {
                                    System.out.println("Review submitted successfully!");
                                } else {
                                    System.out.println("Failed to submit review.");
                                }
                                break;
                            case 5:
                                userActive = false;
                                break;
                        }
                    }
                } else if (user.getRole().equals("Landlord")) {
                    boolean landlordActive = true;
                    while (landlordActive) {
                        System.out.println("1. Add Apartment\n2. Remove Apartment\n3. Edit Apartment\n4. Rate User\n5. Logout");
                        int landlordChoice = scanner.nextInt();
                        scanner.nextLine();

                        switch (landlordChoice) {
                            case 1:
                                System.out.println("Enter title, price, rooms, and description:");
                                String title = scanner.nextLine();
                                double price = scanner.nextDouble();
                                int rooms = scanner.nextInt();
                                scanner.nextLine();
                                String description = scanner.nextLine();
                                if (landlordService.addApartment(user.getId(), title, price, rooms, description)) {
                                    System.out.println("Apartment added successfully!");
                                } else {
                                    System.out.println("Failed to add apartment.");
                                }
                                break;
                            case 2:
                                System.out.println("Enter Apartment ID to remove:");
                                int removeId = scanner.nextInt();
                                if (landlordService.removeApartment(user.getId(), removeId)) {
                                    System.out.println("Apartment removed successfully!");
                                } else {
                                    System.out.println("Failed to remove apartment.");
                                }
                                break;
                            case 3:
                                System.out.println("Enter Apartment ID, title, price, rooms, and description:");
                                int editId = scanner.nextInt();
                                scanner.nextLine();
                                title = scanner.nextLine();
                                price = scanner.nextDouble();
                                rooms = scanner.nextInt();
                                scanner.nextLine();
                                description = scanner.nextLine();
                                if (landlordService.editApartment(user.getId(), editId, title, price, rooms, description)) {
                                    System.out.println("Apartment edited successfully!");
                                } else {
                                    System.out.println("Failed to edit apartment.");
                                }
                                break;
                            case 4:
                                System.out.println("Enter User ID and rating (1-5):");
                                int rateUserId = scanner.nextInt();
                                int rating = scanner.nextInt();
                                if (landlordService.rateUser(user.getId(), rateUserId, rating)) {
                                    System.out.println("User rated successfully!");
                                } else {
                                    System.out.println("Failed to rate user.");
                                }
                                break;
                            case 5:
                                landlordActive = false;
                                break;
                        }
                    }
                } else if (user.getRole().equals("Admin")) {
                    boolean adminActive = true;
                    while (adminActive) {
                        System.out.println("1. Delete User\n2. Block User\n3. Delete Apartment\n4. View Statistics\n5. Logout");
                        int adminChoice = scanner.nextInt();
                        scanner.nextLine();

                        switch (adminChoice) {
                            case 1:
                                System.out.println("Enter User ID to delete:");
                                int deleteUserId = scanner.nextInt();
                                if (adminService.deleteUser(deleteUserId)) {
                                    System.out.println("User deleted successfully!");
                                } else {
                                    System.out.println("Failed to delete user.");
                                }
                                break;
                            case 2:
                                System.out.println("Enter User ID to block:");
                                int blockUserId = scanner.nextInt();
                                if (adminService.blockUser(blockUserId)) {
                                    System.out.println("User blocked successfully!");
                                } else {
                                    System.out.println("Failed to block user.");
                                }
                                break;
                            case 3:
                                System.out.println("Enter Apartment ID to delete:");
                                int deleteApartmentId = scanner.nextInt();
                                if (adminService.deleteApartment(deleteApartmentId)) {
                                    System.out.println("Apartment deleted successfully!");
                                } else {
                                    System.out.println("Failed to delete apartment.");
                                }
                                break;
                            case 4:
                                adminService.viewStatistics();
                                break;
                            case 5:
                                adminActive = false;
                                break;
                        }
                    }
                }
            } else {
                System.out.println("Invalid credentials!");
            }
        } else if (choice == 2) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            System.out.print("Enter role (User/Landlord/Admin): ");
            String role = scanner.nextLine();
            if (authService.register(username, password, role)) {
                System.out.println("Registration successful!");
            } else {
                System.out.println("Registration failed!");
            }
        }
    }
}
