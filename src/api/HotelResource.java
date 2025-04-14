package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;
import service.RoomService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class HotelResource {
    private static HotelResource instance;
    private final CustomerService customerService = CustomerService.getInstance();
    private final ReservationService reservationService = ReservationService.getInstance();
    private final RoomService roomService = RoomService.getInstance();

    private HotelResource() {}

    public static HotelResource getInstance() {
        if (instance == null) {
            instance = new HotelResource();
        }
        return instance;
    }

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email.toLowerCase());
    }

    public void createACustomer(String email, String firstName, String lastName) {
        try {
            customerService.addCustomer(email.toLowerCase(), firstName, lastName);
        } catch (IllegalArgumentException e) {
            System.out.println("Error creating customer: " + e.getMessage());
        }
    }

    public IRoom getRoom(String roomNumber) {
        return roomService.getARoom(roomNumber);
    }

    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        Customer customer = customerService.getCustomer(customerEmail.toLowerCase());
        if (customer == null) {
            System.out.println("No customer found with email: " + customerEmail);
            return null;
        }
        return reservationService.reserveARoom(customer, room, checkInDate, checkOutDate);
    }

    public Collection<Reservation> getCustomersReservations(String customerEmail) {
        Customer customer = customerService.getCustomer(customerEmail.toLowerCase());
        if (customer == null) {
            System.out.println("No customer found with email: " + customerEmail);
            return new ArrayList<>();
        }
        return reservationService.getCustomersReservation(customer);
    }

    public Collection<IRoom> findARoom(Date checkInDate, Date checkOutDate) {
        return reservationService.findRooms(checkInDate, checkOutDate);
    }
}