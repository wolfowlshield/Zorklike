package org.vashonsd;

import org.vashonsd.ItemTypes.EquippableItem;
import org.vashonsd.ItemTypes.Item;
import org.vashonsd.ItemTypes.ReadableItem;

import java.util.Locale;
import java.util.Scanner;

public class Game {

    Scanner input = new Scanner(System.in);
    SentenceDeconstructor sentence = new SentenceDeconstructor();

    boolean isRunning = true;

    Room garden = new Room("garden");
    Room parlor = new Room("parlor");
    Room dungeon = new Room("dungeon");
    Room forest = new Room("forest");
    Room treehouse = new Room("treehouse");
    Room hallway = new Room("hallway");
    Room bedroom = new Room("bedroom");

    Item stick = new Item("stick", forest);

    EquippableItem sword = new EquippableItem("sword", dungeon, "weapon");
    EquippableItem oldArmor = new EquippableItem("old armor", forest, "armor");

    ReadableItem letter = new ReadableItem("letter", garden);

    RoomMap gameMap = new RoomMap();

    Character player = new Character(garden);
    Character trainingDummy = new Character(forest, "dummy");

    Commands commands;

    public Game() {
        gameMap.addRoomToMap(garden, gameMap.generateNearbyRoomHashMap("north", forest, "west", parlor));
        gameMap.addRoomToMap(parlor, gameMap.generateNearbyRoomHashMap("east", garden, "down", dungeon, "south", hallway));
        gameMap.addRoomToMap(hallway, gameMap.generateNearbyRoomHashMap("north", parlor, "east", bedroom));
        gameMap.addRoomToMap(bedroom, gameMap.generateNearbyRoomHashMap("west", hallway));
        gameMap.addRoomToMap(dungeon, gameMap.generateNearbyRoomHashMap("up", parlor));
        gameMap.addRoomToMap(forest, gameMap.generateNearbyRoomHashMap("south", garden, "up", treehouse));
        gameMap.addRoomToMap(treehouse, gameMap.generateNearbyRoomHashMap("down", forest));

        letter.setText("Hope you can read this! Thanks for playing this game!");

        commands = new Commands(this, gameMap, player);
    }

    public void run() {
        while (isRunning) {

            System.out.println(player.getCurrentRoom().getDescription());
            System.out.println("What do you want to do?");
            sentence.setUserSentence(input.nextLine().toLowerCase(Locale.ROOT));

            commands.execute(sentence.getVerb(), sentence.getDirectObject());
        }
    }

    public void stop() {
        isRunning = false;
    }
}
