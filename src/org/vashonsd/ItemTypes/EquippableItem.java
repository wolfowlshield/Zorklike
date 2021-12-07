package org.vashonsd.ItemTypes;

import org.vashonsd.ItemTypes.Item;
import org.vashonsd.Room;

public class EquippableItem extends Item {

    String equipKey;

    public EquippableItem(String name, Room room, String equipKey) {
        super(name, room);
        this.equipKey = equipKey;
    }

    public String getEquipKey() {
        return equipKey;
    }
}
