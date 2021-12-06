package org.vashonsd;

import java.util.*;

public class RoomMap {

    HashMap<Room, HashMap<String, Room>> nearbyRoomDictionary = new HashMap<>();
    HashMap<Room, Integer> smallMap = new HashMap<>();
    List<Room> orderedMap = new ArrayList<>();
    Room empty = new Room("");

    public RoomMap() {
        addRoomToMap(empty, new HashMap<>());
    }

    public void addRoomToMap(Room room, HashMap<String, Room> nearbyRooms) {
        nearbyRoomDictionary.put(room, nearbyRooms);
    }

    public HashMap<String, Room> generateNearbyRoomHashMap(String dir, Room nearbyRoom) {
        HashMap<String, Room> result = new HashMap<>();
        result.put(dir, nearbyRoom);
        return result;
    }

    public HashMap<String, Room> generateNearbyRoomHashMap(String dir, Room nearbyRoom, String dir2, Room nearbyRoom2) {
        HashMap<String, Room> result = new HashMap<>();
        result.put(dir, nearbyRoom);
        result.put(dir2, nearbyRoom2);
        return result;
    }

    public HashMap<String, Room> generateNearbyRoomHashMap(String dir, Room nearbyRoom, String dir2, Room nearbyRoom2, String dir3, Room nearbyRoom3) {
        HashMap<String, Room> result = new HashMap<>();
        result.put(dir, nearbyRoom);
        result.put(dir2, nearbyRoom2);
        result.put(dir3, nearbyRoom3);
        return result;
    }

    public HashMap<Room, HashMap<String, Room>> getNearbyRoomDictionary() {
        return nearbyRoomDictionary;
    }
    // This is hella dumb, but it'll work for now... Until I can think of a better solution

    public String generateVisualMap(Room centerRoom) {
        String visMap = "";
        smallMap.clear();
        orderedMap.clear();
        int orderedMapSize = 0;

        smallMap.put(centerRoom, 11);
        findAllNearbyRooms(centerRoom);

        for (int y = 2; y >= 0; y--) {
            for (int x = 0; x <= 2; x++) {
                for (Map.Entry<Room, Integer> entry: smallMap.entrySet()) {
                    if (entry.getValue() == (x * 10) + y) {
                        orderedMap.add(entry.getKey());
                        break;
                    }
                }
                orderedMapSize++;
                if (orderedMap.size() < orderedMapSize) {
                    orderedMap.add(empty);
                }
            }
        }

        for (int layer = 0; layer < 3; layer++) {
            for (int room = 0; room < 3; room++) {
                if (nearbyRoomDictionary.get(orderedMap.get(room + layer * 3)).containsKey("north")) {
                    visMap = visMap.concat("|-----||-----");
                } else {
                    visMap = visMap.concat("|------------");
                }
            }
            visMap = visMap.concat("|\n");

            for (int room = 0; room < 3; room++) {
                if (nearbyRoomDictionary.get(orderedMap.get(room + layer * 3)).containsKey("west")) {
                    visMap = visMap.concat("= ");
                } else {
                    visMap = visMap.concat("| ");
                }
                if (orderedMap.get(room + layer * 3).toString().length() <= 10) {
                    visMap = visMap.concat(orderedMap.get(room + layer * 3).toString());
                    while (visMap.length() < (room+1) * 13 + layer * 82 + 41) {
                        visMap = visMap.concat(" ");
                    }
                } else {
                    visMap = visMap.concat(orderedMap.get(room + layer * 3).toString().substring(0, 10) + " ");
                }
            }
            if (nearbyRoomDictionary.get(orderedMap.get(2 + layer * 3)).containsKey("east")) {
                visMap = visMap.concat("=\n");
            } else {
                visMap = visMap.concat("|\n");
            }
        }
        for (int room = 0; room < 3; room++) {
            if (nearbyRoomDictionary.get(orderedMap.get(room + 6)).containsKey("south")) {
                visMap = visMap.concat("|-----||-----");
            } else {
                visMap = visMap.concat("|------------");
            }
        }
        visMap = visMap.concat("|\n");

        return visMap;
    }

    public void findAllNearbyRooms(Room currentRoom) {
        if (nearbyRoomDictionary.get(currentRoom).containsKey("west") && !smallMap.containsKey(nearbyRoomDictionary.get(currentRoom).get("west")) && (smallMap.get(currentRoom) / 10) > 0) {
            smallMap.put(nearbyRoomDictionary.get(currentRoom).get("west"), smallMap.get(currentRoom) - 10);
            findAllNearbyRooms(nearbyRoomDictionary.get(currentRoom).get("west"));

        }
        if (nearbyRoomDictionary.get(currentRoom).containsKey("north") && !smallMap.containsKey(nearbyRoomDictionary.get(currentRoom).get("north")) && (smallMap.get(currentRoom) % 10) < 2) {
            smallMap.put(nearbyRoomDictionary.get(currentRoom).get("north"), smallMap.get(currentRoom) + 1);
            findAllNearbyRooms(nearbyRoomDictionary.get(currentRoom).get("north"));
        }
        if (nearbyRoomDictionary.get(currentRoom).containsKey("east") && !smallMap.containsKey(nearbyRoomDictionary.get(currentRoom).get("east")) && (smallMap.get(currentRoom) / 10) < 2) {
            smallMap.put(nearbyRoomDictionary.get(currentRoom).get("east"), smallMap.get(currentRoom) + 10);
            findAllNearbyRooms(nearbyRoomDictionary.get(currentRoom).get("east"));
        }
        if (nearbyRoomDictionary.get(currentRoom).containsKey("south") && !smallMap.containsKey(nearbyRoomDictionary.get(currentRoom).get("south")) && (smallMap.get(currentRoom) % 10) > 0) {
            smallMap.put(nearbyRoomDictionary.get(currentRoom).get("south"), smallMap.get(currentRoom) - 1);
            findAllNearbyRooms(nearbyRoomDictionary.get(currentRoom).get("south"));
        }
    }
}

//  Each box is 12 spaces
// |------------|------------|------------|
// | Forest   ^ | HiddenRoom = Closet     |
// |-----||-----|------------|-----||-----|
// | Garden     = Parlor   v = EastHouse  |
// |------------|------------|------------|
// |                                      |
// |------------|------------|------------|
