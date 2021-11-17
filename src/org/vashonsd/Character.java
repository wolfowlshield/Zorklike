package org.vashonsd;

import java.util.ArrayList;

public class Character {

    private Room currentRoom;

    public String name;

    ArrayList<Item> heldItems = new ArrayList<>();

    // Should also have a dictionary for equipped items

    public Character(Room startingRoom) {
        currentRoom = startingRoom;
        name = "player";
    }

    public Character(Room startingRoom, String name) {
        currentRoom = startingRoom;
        startingRoom.nonPlayers.add(this);
        this.name = name;
    }

    public void moveRoom(Room room) {
        currentRoom = room;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }
    public String getName() {
        return name;
    }
    public ArrayList<Item> getInventory(){
        return heldItems;
    }

    public void addItem(Item item) { // Change this to a pickup Item method
        heldItems.add(item);
    }
    public void dropItem(Item item) {
        heldItems.remove(item);
        currentRoom.addItem(item);
    }

    public String attack(Character enemy, Item weapon) {
        return "You attacked  " + enemy.getName() + " with a " + weapon.getName();
    }
}
