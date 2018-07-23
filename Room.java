/*
represents the rooms withing the warehouse
*/
import java.util.Arrays;
import java.util.*;

public class Room {

    // array representing presence of doors: north, east, south, west
    private boolean[] doors = {false, false, false, false};
    private boolean[] outSideWall = {false, false, false, false};
    private int NORTH = 0;
    private int EAST = 1;
    private int SOUTH = 2;
    private int WEST = 3;
    private LinkedList<Item> itemsInTheRoom = new LinkedList<Item> ();
    String newLine = System.lineSeparator();

    //  add item to LinkedList of items in the room
    public void addItem(Item i){
        itemsInTheRoom.addLast(i);
    }

    public String roomItemsToString(){
        ListIterator<Item> itemIterator = itemsInTheRoom.listIterator();
        int numItem = 1;
        String itemsString = "";
        while (itemIterator.hasNext()) {
            itemsString += numItem + ": " + itemIterator.next().toString();
                if (itemIterator.hasNext())
                    itemsString += newLine;
            numItem++;
        }
        return itemsString;
    }

    public void setDoor(String side) {

        switch (side) {
            case "n": doors[NORTH] = true;
                break;
            case "e": doors[EAST] = true;
                break;
            case "s": doors[SOUTH] = true;
                break;
            case "w": doors[WEST] = true;
                break;
            default: System.out.println("Invalid door variable recieved by Room.setDoor");
                break;
        }
    }

    public boolean isDoor (String side) {

        boolean isDoor = false;
        switch (side) {
            case "n": if (doors[NORTH] == true)
                isDoor = true;
                break;
            case "e": if (doors[EAST] == true)
                isDoor = true;
                break;
            case "s": if (doors[SOUTH] == true)
                isDoor = true;
                break;
            case "w": if (doors[WEST] == true)
                isDoor = true;
                break;
            default: break;
        }
        return isDoor;

    }

    public String toString() {

        StringBuilder roomAsString = new StringBuilder();

        //first row string
        roomAsString.append(isDoor("n") ? "-- --\n" : "-----\n");
        //second row String
        roomAsString.append("|   |\n");
        //third row, check for doors east or west
        roomAsString.append(isDoor("w") ? "    " : "|   ");
        roomAsString.append(isDoor("e") ? " \n" : "|\n");
        //fourth row
        roomAsString.append("|   |\n");
        //fifth row, check for south door
        roomAsString.append(isDoor("s") ? "-- --\n" : "-----\n");

        return roomAsString.toString();
    }

    public int numDoors() {
        int numDoors = 0;
        for (int i = 0; i < 4; i++) {
            if (doors[i] == true)
                numDoors++;
        }
        return numDoors;
    }

    public boolean isOutSideWall(String side) {
        boolean isOutside = false;
        switch (side) {
            case "n": isOutside = outSideWall[NORTH];
                break;
            case "e": isOutside = outSideWall[EAST];
                break;
            case "s": isOutside = outSideWall[SOUTH];
                break;
            case "w": isOutside = outSideWall[WEST];
                break;
            default: System.out.println("Invalid outSideWall variable recieved by Room.setOutsideWall");
                break;
        }
        return isOutside;
    }

    public void setOutsideWall(String side) {
        switch (side) {
            case "n": outSideWall[NORTH] = true;
                break;
            case "e": outSideWall[EAST] = true;
                break;
            case "s": outSideWall[SOUTH] = true;
                break;
            case "w": outSideWall[WEST] = true;
                break;
            default: System.out.println("Invalid outSideWall variable recieved by Room.setOutsideWall");
                break;
        }
    }

    public void removeItem(int itemNum) {
        Item pickedItem = itemsInTheRoom.get(itemNum);
        itemsInTheRoom.remove(itemNum);
    }

    public Item getItem(int itemNum) {
        Item pickedItem = itemsInTheRoom.get(itemNum);
        return pickedItem;
    }

    public boolean isItem(){
        boolean isItem = false;
        if (itemsInTheRoom.size() > 0){
            isItem = true;
        }
        return isItem;
    }

    public void describeRoom() {

        if (isDoor("n")) {
            System.out.println("There is a door going north");
        }
        if (isDoor("e")) {
            System.out.println("There is a door going east");
        }
        if (isDoor("s")) {
            System.out.println("There is a door going south");
        }
        if (isDoor("w")) {
            System.out.println("There is a door going west");
        }
        if(itemsInTheRoom.size() > 0) {
            System.out.println("In the room you have found:");
            System.out.println(roomItemsToString());
        }
        else
            System.out.println("There are no items in the room");
    }

    public int numItems() {
        return itemsInTheRoom.size();
    }

}
