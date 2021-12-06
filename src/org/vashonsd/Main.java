package org.vashonsd;

import java.util.*;

public class Main {


    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        SentenceDeconstructor sentence = new SentenceDeconstructor();

        boolean isRunning = true;
        String userInput;
        String directObject;
        String secondSubject;

        Room garden = new Room("garden");
        Room parlor = new Room("parlor");
        Room dungeon = new Room("dungeon");
        Room forest = new Room("forest");
        Room treehouse = new Room("treehouse");
        Room hallway = new Room("hallway");
        Room bedroom = new Room("bedroom");

        new Item("letter", garden);

        new EquippableItem("sword", dungeon, "weapon");
        new EquippableItem("old armor", forest, "armor");

        RoomMap gameMap = new RoomMap();

        gameMap.addRoomToMap(garden, gameMap.generateNearbyRoomHashMap("north", forest, "west", parlor));
        gameMap.addRoomToMap(parlor, gameMap.generateNearbyRoomHashMap("east", garden, "down", dungeon, "south", hallway));
        gameMap.addRoomToMap(hallway, gameMap.generateNearbyRoomHashMap("north", parlor, "east", bedroom));
        gameMap.addRoomToMap(bedroom, gameMap.generateNearbyRoomHashMap("west", hallway));
        gameMap.addRoomToMap(dungeon, gameMap.generateNearbyRoomHashMap("up", parlor));
        gameMap.addRoomToMap(forest, gameMap.generateNearbyRoomHashMap("south", garden, "up", treehouse));
        gameMap.addRoomToMap(treehouse, gameMap.generateNearbyRoomHashMap("down", forest));

        Character player = new Character(garden);
        Character trainingDummy = new Character(forest, "dummy");

        while (isRunning) {


            System.out.println(player.getCurrentRoom().getDescription());
            System.out.println("What do you want to do?");
            userInput = input.nextLine().toLowerCase(Locale.ROOT);

            sentence.setUserSentence(userInput);

            directObject = sentence.getDirectObject();
            secondSubject = sentence.getPrepSubject();



            switch (sentence.getVerb()) { // Ooo! New format! Thanks, IntelliJ!
                                 // Switch cases seem like they may get a lot of use in this code

                case "exit" -> isRunning = false;

                case "move", "go", "head" -> {
                    if (directObject == null) {
                        System.out.println("Move where?");
                        directObject = input.nextLine();
                    }
                    Room nextRoom = gameMap.getNearbyRoomDictionary().get(player.getCurrentRoom()).get(directObject);

                    if (nextRoom != null) {
                        player.moveRoom(nextRoom);
                    } else {
                        System.out.println("Theres no room there");
                    }
                }

                case "grab","pick" -> {
                    if (directObject == null) {
                        System.out.println("Grab what?");
                        directObject = input.nextLine();
                    }
                    for (Item i: player.getCurrentRoom().items) {
                        if (Objects.equals(i.toString(), directObject)) {
                            player.addItem(i);
                            player.getCurrentRoom().items.remove(i);
                            System.out.println("You picked up the " + i.getName());
                            break;
                        }
                    }
                }

                case "inventory" -> {
                    for (Item i: player.getInventory()) {
                        System.out.println("You are holding a " + i.toString());
                    }
                }

                case "equipment" -> {
                    for (Map.Entry<String, EquippableItem> e: player.getEquipment().entrySet()) {
                        if (e.getValue() == null) {
                            System.out.println("For your " + e.getKey() + ", you have nothing.");
                        } else {
                            System.out.println("For your " + e.getKey() + ", you have " + e.getValue().getName());
                        }
                    }
                }

                case "equip" -> {
                    EquippableItem equipping = null;

                    if (directObject == null) {
                        System.out.println("Equip what?");
                        directObject = input.nextLine();
                    }
                    while (true) {
                        for (Item i : player.getInventory()) {
                            if (Objects.equals(i.getName(), directObject)) {
                                equipping = (EquippableItem) i;
                                if (i.getClass() == EquippableItem.class) {
                                    player.equip(equipping);
                                    System.out.println("You equipped " + equipping);
                                } else {
                                    System.out.println("You can't equip that Item");
                                }
                                break;
                            }
                        }
                        if (equipping == null) {
                            System.out.println("I couldn't find an object with that name. Try entering the items name again.");
                            directObject = input.nextLine().toLowerCase(Locale.ROOT);
                            if (directObject.equals("exit")) {
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }

                case "drop" -> {
                    if (directObject == null) {
                        System.out.println("Drop what?");
                        directObject = input.nextLine();
                    }
                    for (Item i: player.getInventory()) {
                        if (Objects.equals(i.toString(), directObject)) {
                            player.dropItem(i);
                            break;
                        }
                    }
                }

                case "attack" -> {
                    Character enemy = null;

                    if (directObject == null) {
                        System.out.println("Who are you attacking?");
                        directObject = sentence.findNoun(input.nextLine().toLowerCase(Locale.ROOT));
                    }

                    while(true) {
                        for (Character c : player.getCurrentRoom().nonPlayers) {
                            if (Objects.equals(c.getName(), directObject)) {
                                enemy = c;
                                break;
                            }
                        }
                        if (enemy == null) {
                            System.out.println("I can't find that enemy. Try entering their name again");
                            directObject = sentence.findNoun(input.nextLine().toLowerCase(Locale.ROOT));
                        } else {
                            break;
                        }
                    }

                    if (player.getEquipment().get("weapon") == null) {
                        System.out.println("What? With your bare hands? Equip a weapon first");
                    } else {
                        System.out.println(player.attack(enemy, player.getEquipment().get("weapon")));
                    }
                }

                case "map" -> System.out.println(gameMap.generateVisualMap(player.getCurrentRoom()));

                // case "look" -> System.out.println(player.getCurrentRoom()); // Repetitive right now
                default -> System.out.println("I don't know that command");
            }
        }

    }
}
