package ui;

import api.AdminResource;
import model.IRoom;
import model.Room;
import model.RoomType;
import util.InputUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private static final AdminResource adminResource = AdminResource.getInstance();

    public static void displayAdminMenu() {
        boolean running = true;
        while (running) {
            try {
                displayMenu();
                int choice = InputUtils.promptForMenuSelection(1, 5);

                switch (choice) {
                    case 1:
                        seeAllCustomers();
                        break;
                    case 2:
                        seeAllRooms();
                        break;
                    case 3:
                        seeAllReservations();
                        break;
                    case 4:
                        addARoom();
                        break;
                    case 5:
                        running = false;
                        System.out.println("Returning to the Main Menu.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\nAdmin Menu");
        System.out.println("1. See all Customers");
        System.out.println("2. See all Rooms");
        System.out.println("3. See all Reservations");
        System.out.println("4. Add a Room");
        System.out.println("5. Back to Main Menu");
    }

    private static void seeAllCustomers() {
        System.out.println("All Customers:");
        adminResource.getAllCustomers().forEach(System.out::println);
    }

    private static void seeAllRooms() {
        System.out.println("All Rooms:");
        adminResource.getAllRooms().forEach(System.out::println);
    }

    private static void seeAllReservations() {
        System.out.println("All Reservations:");
        adminResource.displayAllReservations();
    }

    private static void addARoom() {
        System.out.print("Enter room number: ");
        String roomNumber = scanner.nextLine().trim();
        System.out.print("Enter room price: ");
        double price = Double.parseDouble(scanner.nextLine().trim());

        RoomType roomType = null;
        while (roomType == null) {
            System.out.print("Enter room type (1 for SINGLE, 2 for DOUBLE): ");
            String roomTypeInput = scanner.nextLine().trim();
            if (roomTypeInput.equals("1")) {
                roomType = RoomType.SINGLE;
            } else if (roomTypeInput.equals("2")) {
                roomType = RoomType.DOUBLE;
            } else {
                System.out.println("Invalid input. Please enter 1 for SINGLE or 2 for DOUBLE.");
            }
        }

        IRoom room = new Room(roomNumber, roomType, price);
        List<IRoom> rooms = new ArrayList<>();
        rooms.add(room);
        adminResource.addRoom(rooms);
        System.out.println("Room added successfully.");
    }
}