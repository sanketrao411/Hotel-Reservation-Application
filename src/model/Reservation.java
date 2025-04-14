package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Reservation {
    private Customer customer;
    private IRoom room;
    private Date checkInDate;
    private Date checkoutDate;

    // Define the date format to display only the date part
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkoutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkoutDate = checkoutDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public IRoom getRoom() {
        return room;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckoutDate() {
        return checkoutDate;
    }

    @Override
    public String toString() {
        return "Reservation " +
                "\n" + customer +"\n " +
                "Room=" + room +"\n " +
                "Check-in Date=" + dateFormat.format(checkInDate) +"\n " +
                "Check-out Date=" + dateFormat.format(checkoutDate);
    }
}