package org.vashonsd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Room {

    public enum Direction {EAST, WEST, NORTH, SOUTH, UP, DOWN} // Probably better to keep them as strings, but we'll see
    HashMap<Direction, Room> nearbyRooms = new HashMap<>();

    ArrayList<Item> items = new ArrayList<>();

    String name;

    public Room(String name) {
        this.name = name;
    }

    public void addNearbyRoom(Direction direction, Room room) {
        nearbyRooms.put(direction, room);
    }

    public Room getRoom(Direction direction) {
        return nearbyRooms.get(direction);
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public Item getItem(String itemName) {
        for (Item i:items) {
            if (Objects.equals(i.getName(), itemName)) {
                return i;
            }
        }
        return null;
    }

    public String toString() {
        return name;
    }
}