package service;

import model.IRoom;
import model.Room;
import model.RoomType;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RoomService {
    private static RoomService instance;
    private Map<String, IRoom> roomMap;

    private RoomService() {
        roomMap = new HashMap<>();
        initializeDefaultRooms();
    }

    public static RoomService getInstance() {
        if (instance == null) {
            instance = new RoomService();
        }
        return instance;
    }

    private void initializeDefaultRooms() {
        IRoom room1 = new Room("101", RoomType.SINGLE, 100.0);
        IRoom room2 = new Room("102", RoomType.DOUBLE, 150.0);
        roomMap.put(room1.getRoomNumber(), room1);
        roomMap.put(room2.getRoomNumber(), room2);
    }

    public void addRoom(String roomNumber, RoomType type, double price) {
        IRoom room = new Room(roomNumber, type, price);
        roomMap.put(roomNumber, room);
    }

    public IRoom getARoom(String roomNumber) {
        return roomMap.get(roomNumber);
    }

    public Collection<IRoom> getAllRooms() {
        return roomMap.values();
    }
}