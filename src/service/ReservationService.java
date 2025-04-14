package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class ReservationService {
    private static ReservationService instance;
    private List<Reservation> reservations;

    private ReservationService() {
        reservations = new ArrayList<>();
    }

    public static ReservationService getInstance() {
        if (instance == null) {
            instance = new ReservationService();
        }
        return instance;
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        if (checkOutDate.before(checkInDate)) {
            throw new IllegalArgumentException("Check-out date must be after check-in date.");
        }
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(reservation);
        return reservation;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        List<IRoom> availableRooms = new ArrayList<>();
        Collection<IRoom> allRooms = RoomService.getInstance().getAllRooms();

        for (IRoom room : allRooms) {
            boolean isAvailable = true;
            for (Reservation reservation : reservations) {
                if (reservation.getRoom().equals(room) &&
                        !(checkOutDate.before(reservation.getCheckInDate()) || checkInDate.after(reservation.getCheckoutDate()))) {
                    isAvailable = false;
                    break;
                }
            }
            if (isAvailable) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public Collection<Reservation> getCustomersReservation(Customer customer) {
        List<Reservation> customerReservations = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.getCustomer().equals(customer)) {
                customerReservations.add(reservation);
            }
        }
        return customerReservations;
    }

    public void printAllReservation() {
        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }
    }
}