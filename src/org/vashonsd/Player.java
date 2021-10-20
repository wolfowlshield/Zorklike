package org.vashonsd;

import java.util.ArrayList;

public class Player {

    private Room currentRoom;

    ArrayList<Item> heldItems = new ArrayList<>();

    public Player(Room startingRoom) {
        currentRoom = startingRoom;
    }

    public void moveRoom(Room room) {
        currentRoom = room;
    }

    public Room getCurrentRoom() {
        return currentRoom;
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
}
