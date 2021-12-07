package org.vashonsd.ItemTypes;

import org.vashonsd.ItemTypes.Item;
import org.vashonsd.Room;

public class ReadableItem extends Item {

    String text;

    public ReadableItem(String name, Room room) {
        super(name, room);
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
