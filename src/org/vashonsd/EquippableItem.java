package org.vashonsd;

public class EquippableItem extends Item{

    String equipKey;

    public EquippableItem(String name, Room room, String equipKey) {
        super(name, room);
        this.equipKey = equipKey;
    }

    public String getEquipKey() {
        return equipKey;
    }
}
