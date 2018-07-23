/*
James Wynne
main class of game
*/
import java.util.*;
import java.io.*;


public class AdventureGame{

    //varibles
    Warehouse warehouse = new Warehouse(8);
    int[] location = new int[2];
    LinkedList<String> itemsList;
    Scanner input = new Scanner(System.in);
    Backpack backpack = new Backpack();
    int maxBackpackSize = 50;
    String filename;

    public static void main(String args[]) {

        // if user gives filename for a list of their own 50 items
        String itemsFile = "items.txt";
        if (args.length == 1)
            itemsFile = args[0];
        AdventureGame adventureGame = new AdventureGame(itemsFile);
        adventureGame.play();
    }

    // constructor
    public AdventureGame(String f) {

        filename = f;
        itemsList = readInItemsFromText();
        // create items and distribute randomly in warehouse
        ListIterator<String> itemIterator = itemsList.listIterator();
        while (itemIterator.hasNext()) {
            String itemName = itemIterator.next();
            int roomXCoord = (int)(Math.random() * 8);
            int roomYCoord = (int)(Math.random() * 8);
            int randomValue = ((int)(Math.random() * 20) + 1);
            int randomSize = ((int)(Math.random() * 10) + 5);
            Item newItem = new Item(itemName, randomValue, randomSize);

            warehouse.getRoom(roomXCoord,roomYCoord).addItem(newItem);
        }

        // randomly select starting location and begin game
        location[0] = (int)(Math.random() * 8);
        location[1] = (int)(Math.random() * 8);
    }

    public void play() {
        System.out.println("Starting the game...");
        System.out.println("");
        boolean continueGame = true;
        // loop round to continue game
        while (continueGame) {
            // one added to location to change coordinate range from 0-7, to 1-8 for user display
            System.out.println("You are in room (" + (location[0] + 1) + ", " + (location[1] + 1) + ")");
            warehouse.getRoom(location[0], location[1]).describeRoom();
            System.out.println("");

            boolean inOneRoom = true;
            // loop while player in one room, description of room given once outside this loop
            while (inOneRoom) {
                String action = getActionInput();
                switch (action) {
                    case "i": inventory();
                        break;
                    case "p": pickupItem();
                        break;
                    case "d": dropItem();
                        break;
                    default:
                        if (warehouse.getRoom(location[0], location[1]).isDoor(action)) {
                            switch (action) {
                                case "n": location[1]++;
                                    break;
                                case "e": location[0]++;
                                    break;
                                case "s": location[1]--;
                                    break;
                                case "w": location[0]--;
                                    break;
                            }
                            inOneRoom = false;
                            System.out.println("");
                            break;
                        }
                        else {
                            System.out.println("You can't go that way.");
                            System.out.println("");
                        }
                }
            }
        }
    }

    private String getActionInput () {
        //validate input by comparing input to allowedCommands
        boolean commandAllowed = false;
        String[] allowedCommands = {"i", "p", "d", "n", "e", "s", "w"};
        String action;
        do {
            System.out.print("What would you like to do? ");
            action = input.nextLine();
            for (String s : allowedCommands) {
                if (s.equals(action)) {
                    commandAllowed = true;
                    break;
                }

            }
            if (!commandAllowed) {
                System.out.println("Error. Not an acceptable command.");
                System.out.println("Commands:- p: pick up item - i: inventory - d: drop item - or n, e, s or w to move.");
            }
        } while(!commandAllowed);
        return action;
    }

    // shows user the items in there backpack
    private void inventory() {
        System.out.println("Here are the items in your backpack:");
        System.out.println(backpack.toString());
        System.out.println("Total worth of items is " + backpack.getTotalValue());
        System.out.println("Space left in your backpack is " + (maxBackpackSize - backpack.getTotalSize()));
        System.out.println("");
    }

    // remove item from room and add to backpack
    private void pickupItem() {

        Room currentRoom = warehouse.getRoom(location[0], location[1]);
        if (currentRoom.isItem()){
            System.out.println("Ok. Here are the items you can pick up:");
            System.out.println(currentRoom.roomItemsToString());
            boolean err;

            int itemChoice = 0;
            String line;
            do {
                System.out.print("Which item would you like to pick up?");
                err = false;
                try {
                    line = input.nextLine();
                    itemChoice = Integer.parseInt(line);
                    if (itemChoice < 1 || itemChoice > currentRoom.numItems()) {
                        err = true;
                        System.out.println("Error. Input out of range");
                        System.out.println("");
                    }
                }
                catch (NumberFormatException e) {
                    err = true;
                    System.out.println("Error. Please input the number of the item you would like to pick up");
                    System.out.println("");
                }
            } while (err);
            itemChoice--; // take one as list indexes start at 0
            // check to see if room in backpack to pick up items
            if (currentRoom.getItem(itemChoice).getSize() + backpack.getTotalSize() < maxBackpackSize) {
                backpack.insertItem(currentRoom.getItem(itemChoice));
                currentRoom.removeItem(itemChoice);
                System.out.println("OK. Item taken");
                System.out.println("");
            }
            else {
                System.out.println("There is not enough room in your backpack for that item");
                System.out.println("");
            }
        }
        else {
            System.out.println("There are no items in the room");
            System.out.println("");
        }

    }

    // remove item from backpack and add to room
    private void dropItem() {

        if (backpack.getTotalSize() > 0) {
            System.out.println("OK. Here are the items that you can drop");
            System.out.println(backpack.toString());
            int itemChoice = 0;
            boolean err;
            String line;

            do {
                System.out.print("Which item would you like to drop? ");
                err = false;
                try {
                    line = input.nextLine();
                    itemChoice = Integer.parseInt(line);
                    if (itemChoice < 1 || itemChoice > backpack.numItems()) {
                        err = true;
                        System.out.println("Error, input out of range.");
                        System.out.println("");
                    }
                }
                catch (NumberFormatException e) {
                    err = true;
                    System.out.println("Error. Please input the number of the item you would like to drop");
                    System.out.println("");
                }

            } while (err);

            // take 1 from item choice so as to move within iter range
            itemChoice--;
            Item droppedItem = backpack.dropItem(itemChoice);
            warehouse.getRoom(location[0], location[1]).addItem(droppedItem);
            System.out.println("Item dropped");
            System.out.println("");
        }
        else {
            System.out.println("You have no items to drop");
        }

    }
    // item names come from a text file, items seperated by line separator
    private LinkedList<String> readInItemsFromText() {

        LinkedList<String> listOfItems = new LinkedList<String> ();
        Scanner itemsFile;

        try {
            itemsFile = new Scanner(new File(filename));
        }
        catch (FileNotFoundException e) {
            System.out.println("Cannot find items file");
            return listOfItems;
        }

        while (itemsFile.hasNextLine()) {
            listOfItems.addLast(itemsFile.nextLine());
        }
        return listOfItems;
    }

}
