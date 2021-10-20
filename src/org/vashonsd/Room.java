package org.vashonsd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Room {

    // public enum Direction {EAST, WEST, NORTH, SOUTH, UP, DOWN}
    HashMap<String, Room> nearbyRooms = new HashMap<>();

    ArrayList<Item> items = new ArrayList<>();

    String name;

    public Room(String name) {
        this.name = name;
    }

    public void addNearbyRoom(String direction, Room room) {
        nearbyRooms.put(direction, room);
        room.nearbyRooms.put(getOppositeDirection(direction), this); // Add the way back
    }

    public Room getNearbyRoom(String direction) {
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

    public static String getOppositeDirection(String direction) { // I figured this was probably worth making a static function
        switch (direction) {
            case "west" -> {
                return "east";
            }
            case "east" -> {
                return "west";
            }
            case "north" -> {
                return "south";
            }
            case "south" -> {
                return "north";
            }
            case "up" -> {
                return "down";
            }
            case "down" -> {
                return "up";
            }
            default -> {
                return null;
            }
        }
    }

    public String toString() {
         String result = "You are standing in the " + name;
        for (Item i: items) {
            result = result.concat("\nThere is a " + i);
        }
        return result;
    }
}