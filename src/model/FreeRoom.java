package model;

public class FreeRoom extends Room {
    public FreeRoom(String roomNumber, RoomType type) {
        super(roomNumber, type, 0.0);
    }

    @Override
    public String toString() {
        return "Free Room - " + super.toString();
    }
}