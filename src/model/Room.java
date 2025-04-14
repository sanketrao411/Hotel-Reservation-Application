package model;

public class Room implements IRoom {
    private String roomNumber;
    private Double price;
    private RoomType type;

    public Room(String roomNumber, RoomType type, Double price) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.price = price;
    }

    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return price;
    }

    @Override
    public RoomType getRoomType() {
        return type;
    }

    @Override
    public boolean isFree() {
        return price == 0.0;
    }

    @Override
    public String toString() {
        String priceDisplay = (price == 0.0) ? "Free" : "$" + price;
        return "Room Number: " + roomNumber + ", Type: " + type + ", Price: " + priceDisplay;
    }
}