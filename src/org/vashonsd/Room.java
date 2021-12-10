package org.vashonsd;

import org.vashonsd.ItemTypes.Item;

import java.util.ArrayList;
import java.util.Objects;

public class Room {

    // public enum Direction {EAST, WEST, NORTH, SOUTH, UP, DOWN}

    ArrayList<Item> items = new ArrayList<>();
    ArrayList<Character> nonPlayers = new ArrayList<>();

    String name;

    public Room(String name) {
        this.name = name;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public Item getItem(String itemName) {
        for (Item i:items) {
            if (Objects.equals(i.toString(), itemName)) {
                return i;
            }
        }
        return null;
    }

    public ArrayList<Item> getItems() {
        return items;
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

    public String getDescription() {
        String result = "You are standing in the " + name;
        for (Item i: items) {
            result = result.concat("\nThere is a " + i);
        }
        for (Character c: nonPlayers) {
            result = result.concat("\nYou see " + c.getName());
        }
        return result;
    }
    public String toString() {
        return name;
    }
}