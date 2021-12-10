package org.vashonsd;

import org.vashonsd.ItemTypes.EquippableItem;
import org.vashonsd.ItemTypes.Item;
import org.vashonsd.ItemTypes.ReadableItem;

import java.util.*;

public class Commands {

    List<String> groundItems = new ArrayList<>(Arrays.asList("grab", "pick"));
    List<String> inventoryItems = new ArrayList<>(Arrays.asList("equip", "drop", "read"));
    List<String> otherCharacters = new ArrayList<>(Arrays.asList("attack"));


    Game game;
    RoomMap roomMap;
    Character player;

    public Commands(Game game, RoomMap roomMap, Character player) {
        this.game = game;
        this.roomMap = roomMap;
        this.player = player;
    }

    public void execute(String in, String inTwo) {

        Item interactItem = null;
        if (groundItems.contains(in)) {
            for (Item i: player.getCurrentRoom().getItems()) {
                if (Objects.equals(i.toString(), inTwo)) {
                    interactItem = i;
                }
            }
            if (interactItem != null) {
                switch (in) {
                    case "grab", "pick" -> grab(interactItem);
                    // case "attack" ->
                }
            } else {
                System.out.println("I couldn't find that object");
            }
        } else if (inventoryItems.contains(in)) {
            for (Item i: player.getInventory()) {
                if (Objects.equals(i.toString(), inTwo)) {
                    interactItem = i;
                }
            }
            if (interactItem != null) {
                switch (in) {
                    case "equip" -> equip(interactItem);
                    case "drop" -> drop(interactItem);
                    case "read" -> read(interactItem);
                }
            } else {
                System.out.println("I couldn't find that object");
            }
        } else {
            switch (in) {
                case "exit", "quit" -> exit();
                case "move" -> move(inTwo);
                case "inventory" -> inventory();
                case "equipment" -> equipment();
                case "map" -> map();
                default -> System.out.println("I couldn't find that command");
            }
        }
    }

    private void read(Item reading) {
        if (reading.getClass() == ReadableItem.class) {
            System.out.println("The item says: \"" + ((ReadableItem) reading).getText() + "\"");
        }
    }

    private void drop(Item dropping) {
        player.dropItem(dropping);
        player.getCurrentRoom().addItem(dropping);
    }

    private void grab(Item grabbing) {
        player.addItem(grabbing);
        player.getCurrentRoom().getItems().remove(grabbing);
        System.out.println("You picked up the " + grabbing);
    }

    private void equip(Item equipping) {
        if (equipping.getClass() == EquippableItem.class) {
            player.equip((EquippableItem) equipping);
            System.out.println("You equipped " + equipping);
        } else {
            System.out.println("You can't equip that Item");
        }
    }

    public void exit() {
        game.stop();
    }

    public void move(String direction) {
        if (direction == null) {
            System.out.println("Add a direction to the end!");
        } else {
            Room nextRoom = roomMap.getNearbyRoomDictionary().get(player.getCurrentRoom()).get(direction);
            if (nextRoom != null) {
                player.moveRoom(nextRoom);
            } else {
                System.out.println("There's no room there");
            }
        }
    }

    public void inventory() {
        for (Item i: player.getInventory()) {
            System.out.println("You are holding a " + i.toString());
        }
    }

    public void equipment() {
        for (Map.Entry<String, EquippableItem> e: player.getEquipment().entrySet()) {
            if (e.getValue() == null) {
                System.out.println("For your " + e.getKey() + ", you have nothing.");
            } else {
                System.out.println("For your " + e.getKey() + ", you have " + e.getValue());
            }
        }
    }

    public void map() {
        System.out.println(roomMap.generateVisualMap(player.getCurrentRoom()));
    }
}
