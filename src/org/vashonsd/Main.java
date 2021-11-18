package org.vashonsd;

import java.util.*;

public class Main {


    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        SentenceDeconstructor deconstructor = new SentenceDeconstructor();

        boolean isRunning = true;
        String userInput;
        String directObject;
        String secondSubject;

        Room garden = new Room("garden");
        Room parlor = new Room("parlor");
        Room dungeon = new Room("dungeon");
        Room forest = new Room("forest");

        new Item("letter", garden);

        new EquippableItem("sword", dungeon, "weapon");
        EquippableItem equiping;


        garden.addNearbyRoom("west", parlor);
        dungeon.addNearbyRoom("up", parlor);
        forest.addNearbyRoom("south", garden);

        Character player = new Character(garden);
        Character trainingDummy = new Character(forest, "dummy");

        while (isRunning) {


            System.out.println(player.getCurrentRoom());
            System.out.println("What do you want to do?");
            userInput = input.nextLine().toLowerCase(Locale.ROOT);

            deconstructor.setUserSentence(userInput);

            directObject = deconstructor.getDirectObject();
            secondSubject = deconstructor.getPrepSubject();

            equiping = null;

            switch (deconstructor.getVerb()) { // Ooo! New format! Thanks, IntelliJ!
                                 // Switch cases seem like they may get a lot of use in this code

                case "exit" -> isRunning = false;

                case "move", "go", "head" -> {
                    if (directObject == null) {
                        System.out.println("Move where?");
                        directObject = input.nextLine();
                    }
                    Room nextRoom = player.getCurrentRoom().getNearbyRoom(directObject);
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
                    if (directObject == null) {
                        System.out.println("Equip what?");
                        directObject = input.nextLine();
                    }
                    while (true) {
                        for (Item i : player.getInventory()) {
                            if (Objects.equals(i.getName(), directObject)) {
                                equiping = (EquippableItem) i;
                                if (i.getClass() == EquippableItem.class) {
                                    player.equip(equiping);
                                } else {
                                    System.out.println("You can't equip that Item");
                                }
                                break;
                            }
                        }
                        if (equiping == null) {
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
                    Item weapon = null;

                    if (directObject == null) {
                        System.out.println("Who are you attacking?");
                        directObject = deconstructor.findNoun(input.nextLine().toLowerCase(Locale.ROOT));
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
                            directObject = deconstructor.findNoun(input.nextLine().toLowerCase(Locale.ROOT));
                        } else {
                            break;
                        }
                    }

                    // Replace the code below once you can equip a weapon

                    if (secondSubject == null){
                        System.out.println("What are you attacking with?");
                        secondSubject = deconstructor.findNoun(input.nextLine().toLowerCase(Locale.ROOT));
                    }
                    while (true) {
                        for (Item i : player.getInventory()) {
                            if (Objects.equals(secondSubject, i.getName())) {
                                weapon = i;
                                break;
                            }
                        }
                        if (weapon == null) {
                            System.out.println("I couldn't find that item in your inventory. Try entering the item's name again");
                            secondSubject = deconstructor.findNoun(input.nextLine().toLowerCase(Locale.ROOT));
                        } else {
                            break;
                        }
                    }

                    System.out.println(player.attack(enemy, weapon));

                }
                // case "look" -> System.out.println(player.getCurrentRoom()); // Repetitive right now
                default -> System.out.println("I don't know that command");
            }
        }

    }
}
