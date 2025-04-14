package ui;

import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;
import util.InputUtils;

import java.util.Collection;
import java.util.Date;

public class MainMenu {
    private static final HotelResource hotelResource = HotelResource.getInstance();

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            try {
                displayMenu();
                int choice = InputUtils.promptForMenuSelection(1, 5);

                switch (choice) {
                    case 1:
                        findAndReserveRoom();
                        break;
                    case 2:
                        seeMyReservations();
                        break;
                    case 3:
                        createAccount();
                        break;
                    case 4:
                        AdminMenu.displayAdminMenu();
                        break;
                    case 5:
                        running = false;
                        System.out.println("Exiting the application. Goodbye!");
                        break;
                }
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\nWelcome to the Hotel Reservation Application");
        System.out.println("1. Find and reserve a room");
        System.out.println("2. See my reservations");
        System.out.println("3. Create an account");
        System.out.println("4. Admin");
        System.out.println("5. Exit");
    }

    private static void findAndReserveRoom() {
        try {
            Date checkInDate = InputUtils.promptForValidDate("Enter check-in date (dd/MM/yyyy): ");
            Date checkOutDate = InputUtils.promptForValidDate("Enter check-out date (dd/MM/yyyy): ");

            if (checkOutDate.before(checkInDate)) {
                System.out.println("Invalid input: Check-out date must be after check-in date.");
                return;
            }

            Collection<IRoom> availableRooms = hotelResource.findARoom(checkInDate, checkOutDate);

            if (availableRooms.isEmpty()) {
                System.out.println("No available rooms for the selected dates.");
            } else {
                System.out.println("Available rooms:");
                availableRooms.forEach(System.out::println);

                if (InputUtils.promptYesNo("Would you like to book a room? (y/n): ")) {
                    if (InputUtils.promptYesNo("Do you have an account with us? (y/n): ")) {
                        String roomNumber;
                        IRoom room;
                        do {
                            System.out.print("Enter room number to reserve: ");
                            roomNumber = InputUtils.scanner.nextLine().trim();
                            room = hotelResource.getRoom(roomNumber);
                            if (room == null) {
                                System.out.println("Invalid room number. Please enter a valid room number.");
                            }
                        } while (room == null);

                        while (true) {
                            String email = InputUtils.promptForValidEmail();
                            Customer customer = hotelResource.getCustomer(email);

                            if (customer != null) {
                                Reservation reservation = hotelResource.bookARoom(email, room, checkInDate, checkOutDate);
                                System.out.println("Reservation successful: " + reservation);
                                break;
                            } else {
                                System.out.println("No account found with this email.");
                                if (InputUtils.promptYesNo("Would you like to create an account? (y/n): ")) {
                                    email = createAccount();
                                    Reservation reservation = hotelResource.bookARoom(email, room, checkInDate, checkOutDate);
                                    System.out.println("Reservation successful: " + reservation);
                                    break;
                                } else {
                                    System.out.println("Please enter an email associated with an existing account.");
                                }
                            }
                        }
                    } else {
                        String email = createAccount();
                        String roomNumber;
                        IRoom room;
                        do {
                            System.out.print("Enter room number to reserve: ");
                            roomNumber = InputUtils.scanner.nextLine().trim();
                            room = hotelResource.getRoom(roomNumber);
                            if (room == null) {
                                System.out.println("Invalid room number. Please enter a valid room number.");
                            }
                        } while (room == null);

                        Reservation reservation = hotelResource.bookARoom(email, room, checkInDate, checkOutDate);
                        System.out.println("Reservation successful: " + reservation);
                    }
                } else {
                    System.out.println("Returning to the main menu.");
                }
            }
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private static void seeMyReservations() {
        try {
            String email = InputUtils.promptForValidEmail();
            Customer customer = hotelResource.getCustomer(email);

            if (customer == null) {
                System.out.println("No account found with this email.");
                return;
            }

            Collection<Reservation> reservations = hotelResource.getCustomersReservations(email);

            if (reservations == null || reservations.isEmpty()) {
                System.out.println("No reservations found for this email.");
            } else {
                System.out.println("Your reservations:");
                reservations.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private static String createAccount() {
        try {
            String email = InputUtils.promptForValidEmail();

            // Check if the email already exists
            if (hotelResource.getCustomer(email) != null) {
                System.out.println("Error creating customer: Email already exists: " + email);
                return null;
            }

            System.out.print("Enter your first name: ");
            String firstName = InputUtils.scanner.nextLine().trim();

            System.out.print("Enter your last name: ");
            String lastName = InputUtils.scanner.nextLine().trim();

            hotelResource.createACustomer(email, firstName, lastName);
            System.out.println("Account created successfully.");
            return email;
        } catch (IllegalArgumentException e) {
            System.out.println("Error creating account: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
        return null;
    }
}