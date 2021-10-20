package org.vashonsd;

import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        boolean isRunning = true;
        String userInput;
        String subject;

        Room garden = new Room("garden");
        Room parlor = new Room("parlor");
        Room dungeon = new Room("dungeon");
        Room forest = new Room("forest");

        new Item("letter", garden);
        new Item("sword", dungeon);

        garden.addNearbyRoom("west", parlor);
        dungeon.addNearbyRoom("up", parlor);
        forest.addNearbyRoom("south", garden);


        Player player = new Player(garden);

        while (isRunning) {

            subject = null;

            System.out.println(player.getCurrentRoom());
            System.out.println("What do you want to do?");
            userInput = input.nextLine().toLowerCase(Locale.ROOT);
            for (String i: new String[]{"the ","a ","to "}) { // Add helping words here so that they can be ignored
                if (userInput.contains(i)) {
                    userInput = userInput.replace(i, "");
                }
            }
            if (userInput.contains(" ")) {
                subject = userInput.substring(userInput.indexOf(' ') + 1);
                userInput = userInput.replace(subject, "");
                userInput = userInput.replace(" ", ""); // Whole ton of wording madness
            }

            switch (userInput) { // Ooo! New format! Thanks, IntelliJ!
                                 // Switch cases seem like they may get a lot of use in this code

                case "exit" -> isRunning = false;
                case "move", "go" -> {
                    if (subject == null) {
                        System.out.println("Move where?");
                        subject = input.nextLine();
                    }
                    Room nextRoom = player.getCurrentRoom().getNearbyRoom(subject);
                    if (nextRoom != null) {
                        player.moveRoom(nextRoom);
                    } else {
                        System.out.println("Theres no room there");
                    }
                }
                case "grab" -> {
                    if (subject == null) {
                        System.out.println("Grab what?");
                        subject = input.nextLine();
                    }
                    for (Item i: player.getCurrentRoom().items) {
                        if (Objects.equals(i.toString(), subject)) {
                            player.addItem(i);
                            player.getCurrentRoom().items.remove(i);
                            break;
                        }
                    }
                }
                case "inventory" -> {
                    for (Item i: player.getInventory()) {
                        System.out.println("You are holding a " + i.toString()); // Is this worth making its own function?
                    }
                }
                case "drop" -> {
                    if (subject == null) {
                        System.out.println("Drop what?");
                        subject = input.nextLine();
                    }
                    for (Item i: player.getInventory()) {
                        if (Objects.equals(i.toString(), subject)) {
                            player.dropItem(i);
                            break;
                        }
                    }
                }
                // case "look" -> System.out.println(player.getCurrentRoom()); // Repetitive right now
                default -> System.out.println("I don't know that command");
            }
        }

    }
}
