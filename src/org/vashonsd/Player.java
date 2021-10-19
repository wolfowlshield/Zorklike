package org.vashonsd;

import java.util.ArrayList;

public class Player {
    Room currentRoom;
    ArrayList<Item> heldItems = new ArrayList<>();

    public Player(Room startingRoom) {
        currentRoom = startingRoom;
    }
}
