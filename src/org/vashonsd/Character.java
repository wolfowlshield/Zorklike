package org.vashonsd;

import org.vashonsd.ItemTypes.EquippableItem;
import org.vashonsd.ItemTypes.Item;

import java.util.ArrayList;
import java.util.HashMap;

public class Character {

    private Room currentRoom;
    public String name;


    ArrayList<Item> heldItems = new ArrayList<>();
    HashMap<String, EquippableItem> equipment = new HashMap<>();

    public Character(Room startingRoom) {
        currentRoom = startingRoom;
        name = "player";
        initEquipment();
    }

    public Character(Room startingRoom, String name) {
        currentRoom = startingRoom;
        startingRoom.nonPlayers.add(this);
        this.name = name;
        initEquipment();
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
    public HashMap<String, EquippableItem> getEquipment() {
        return equipment;
    }

    public void addItem(Item item) { // Change this to a pickup Item method
        heldItems.add(item);
    }
    public void dropItem(Item item) {
        heldItems.remove(item);
        currentRoom.addItem(item);
    }

    public void equip(EquippableItem item) {
        equipment.put(item.getEquipKey(), item);
    }

    public String attack(Character enemy, Item weapon) {
        return "You attacked  " + enemy.getName() + " with a " + weapon;
    }

    private void initEquipment() {
        equipment.put("weapon", null);
        equipment.put("armor", null);
    }
}
