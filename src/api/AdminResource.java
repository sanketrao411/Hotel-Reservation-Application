package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;
import service.RoomService;

import java.util.Collection;
import java.util.List;

public class AdminResource {
    private static AdminResource instance;
    private final CustomerService customerService = CustomerService.getInstance();
    private final ReservationService reservationService = ReservationService.getInstance();
    private final RoomService roomService = RoomService.getInstance();

    private AdminResource() {}

    public static AdminResource getInstance() {
        if (instance == null) {
            instance = new AdminResource();
        }
        return instance;
    }

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public void addRoom(List<IRoom> rooms) {
        for (IRoom room : rooms) {
            roomService.addRoom(room.getRoomNumber(), room.getRoomType(), room.getRoomPrice());
        }
    }

    public Collection<IRoom> getAllRooms() {
        return roomService.getAllRooms();
    }

    public Collection<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    public void displayAllReservations() {
        reservationService.printAllReservation();
    }
}