package org.vashonsd;

public class Main {

    public static void main(String[] args) {

        Room garden = new Room("garden");
        Room parlor = new Room("parlor");

        Item letter = new Item("letter");

        garden.addNearbyRoom(Room.Direction.WEST, parlor);
        garden.addItem(letter);

        System.out.println(garden.getRoom(Room.Direction.WEST));

        System.out.println(garden.getItem("letter"));
    }
}
