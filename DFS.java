import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner; // Import the Scanner class to read text files
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
public class DFS {
    public static void main(String[] args) {
        //Scanner imp = new Scanner(System.in);
        //System.out.println("Please input the name of the file containing the maze (including the file extention.)");
        String Fname = args[0]; //imp.nextLine();

        long start = System.currentTimeMillis();
        String Maze = "";
        try {
            File MazeFile = new File(Fname);
            Scanner myReader = new Scanner(MazeFile);
            while (myReader.hasNextLine()){
                Maze += myReader.nextLine();
            }
            myReader.close();
        } catch (FileNotFoundException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        DFSObject DFSHolder = new DFSObject();
        MazeInformation(DFSHolder, Maze);
        //you may notice its not using the first one, that is because doing it through openness was a huge mistake
        DFS2(DFSHolder);
        long end = System.currentTimeMillis();
        System.out.println("This maze's path " + DFSHolder.getPath());
        System.out.println("The steps taken " + (DFSHolder.getStepsForPath() - 1));//it shouldn't count the first node as that's where we begin not a step
        System.out.println("The total steps taken to find the path " + DFSHolder.getSteps());
        System.out.println("This program took: " + (end - start) + "ms to complete");
        PrinttextMaze(DFSHolder);
    }

    public static DFSObject MazeInformation(DFSObject DFSHolder, String MazeFile){
        int whichValue = 0;
        String output = "";
        for(int i = 0; i < MazeFile.length();i++){
            if(Character.compare(MazeFile.charAt(i),':') == 0){
                if(whichValue == 0){
                    DFSHolder.setNumRows(Integer.parseInt(output.substring(0,output.indexOf(',')))); //grabs the number of rows and converts into an int
                    DFSHolder.setNumColumns(Integer.parseInt(output.substring(output.indexOf(',') + 1))); //grabs the number of columns and converts into an int
                }
                if(whichValue == 1){
                    DFSHolder.setLocation(Integer.parseInt(output));
                }
                if(whichValue == 2){
                    DFSHolder.setEndPosition(Integer.parseInt(output));
                }
                whichValue++;
                output = "";
            }
            else{
                output += MazeFile.charAt(i);
            }
        }
        DFSHolder.setMaze(output);
        return DFSHolder;
    }
    //tried to do the DFS based on openness but it just cant be done as openess does not provide enough information
    /*public static DFSObject DFS(DFSObject DFSHolder){
        System.out.println("Location " + DFSHolder.getLocation());
        int currentPrelocation = DFSHolder.getPreLocation();
        int currentLoc = DFSHolder.getLocation();
        char walls = DFSHolder.getMaze().charAt(DFSHolder.getLocation() - 1); // char list is from 0 and location goes from 1 and getmaze is a string of the walls
        if(DFSHolder.getLocation() == DFSHolder.getEndPosition()){
            System.out.println("We got here from finishing it");
            DFSHolder.setPathSolved(true);
            DFSHolder.setPath(DFSHolder.getPath() + DFSHolder.getLocation());
            return DFSHolder;
        }
        switch(walls){
            case '0': // right and down closed
                //if(DFSHolder.getLocation() - DFSHolder.getNumColumns() < 1 && DFSHolder.getPreLocation() != DFSHolder.getLocation() - DFSHolder.getNumColumns()){
                //if(DFSHolder.getLocation() - DFSHolder.getNumColumns() >= DFSHolder.getEndPosition()){
                //Only option is left or up and one is where you came from. Or you hit in a courner and cant break out
                System.out.println("Case 0 " + currentLoc);
                if((DFSHolder.getLocation() - 1) % DFSHolder.getNumColumns() != 0 && DFSHolder.getPreLocation() != DFSHolder.getLocation() - 1) { //can actually go left ,and it's not where you came from
                    DFSHolder.setPreLocation(DFSHolder.getLocation());
                    DFSHolder.setLocation(DFSHolder.getLocation() - 1);
                    DFSHolder.setSteps(DFSHolder.getSteps() + 1);
                    DFS(DFSHolder);
                }
                else if(DFSHolder.getLocation() - DFSHolder.getNumColumns() >= 1 && DFSHolder.getPreLocation() != DFSHolder.getLocation() - DFSHolder.getNumColumns()){ //can go up and it's not the pre location
                        DFSHolder.setPreLocation(DFSHolder.getLocation());
                        DFSHolder.setLocation(DFSHolder.getLocation() - DFSHolder.getNumColumns());
                        DFSHolder.setSteps(DFSHolder.getSteps() + 1);
                        DFS(DFSHolder);
                }
                System.out.println("broke");
                break;
            case '1': //right only open
                System.out.println("Case 1 " + currentLoc);
                //If both left and right are valid options to travers this will compute the best one to go in hopes we get to the end the fastest
                if(DFSHolder.getPreLocation() != DFSHolder.getLocation() + 1 && currentLoc % DFSHolder.getNumColumns() != 0 && (DFSHolder.getLocation() - 1) % DFSHolder.getNumColumns() != 0 && DFSHolder.getPreLocation() != DFSHolder.getLocation() - 1 && Character.getNumericValue(DFSHolder.getMaze().charAt(DFSHolder.getLocation() - 2)) % 2 != 0){
                    //a number divided by the columns will always have the same decimals as the number a column below, therefore we can observe the decimals of our current position to determine if going left or right would be the better option
                    if(DFSHolder.getLocation() + 1 <= DFSHolder.getEndPosition()) {
                        DFSHolder.setPreLocation(DFSHolder.getLocation());
                        DFSHolder.setLocation(DFSHolder.getLocation() + 1);
                        DFSHolder.setSteps(DFSHolder.getSteps() + 1);
                        DFS(DFSHolder);

                        if (DFSHolder.isPathSolved()) {
                            break;
                        }
                        //then if we haven't solved it we try to go the other way
                        System.out.println("AnotherPath case 1 " + currentLoc);
                        DFSHolder.setPreLocation(currentLoc);
                        DFSHolder.setLocation(currentLoc - 1);
                        DFSHolder.setSteps(DFSHolder.getSteps() + 1);
                        DFS(DFSHolder);
                    }
                    else{
                        DFSHolder.setPreLocation(currentLoc);
                        DFSHolder.setLocation(currentLoc - 1);
                        DFSHolder.setSteps(DFSHolder.getSteps() + 1);
                        DFS(DFSHolder);
                        if (DFSHolder.isPathSolved()) {
                            break;
                        }
                        DFSHolder.setPreLocation(DFSHolder.getLocation());
                        DFSHolder.setLocation(DFSHolder.getLocation() + 1);
                        DFSHolder.setSteps(DFSHolder.getSteps() + 1);
                        DFS(DFSHolder);
                    }
                }
                else if(currentPrelocation != currentLoc + 1 && currentLoc % DFSHolder.getNumColumns() != 0) { //try right
                    DFSHolder.setPreLocation(DFSHolder.getLocation());
                    DFSHolder.setLocation(DFSHolder.getLocation() + 1);
                    DFSHolder.setSteps(DFSHolder.getSteps() + 1);
                    DFS(DFSHolder);
                }
                if(DFSHolder.isPathSolved()){
                    break;
                }
                //then we left
                if((currentLoc - 1) % DFSHolder.getNumColumns() != 0 && currentPrelocation != currentLoc - 1 && Character.getNumericValue(DFSHolder.getMaze().charAt(DFSHolder.getLocation() - 2)) % 2 != 0) { //going left and checks if possible
                    DFSHolder.setPreLocation(currentLoc);
                    DFSHolder.setLocation(currentLoc - 1);
                    DFSHolder.setSteps(DFSHolder.getSteps() + 1);
                    DFS(DFSHolder);
                }
                System.out.println("broke");
                break;
            case '2': //down only open
               System.out.println("Case 2 " + currentLoc);
                //checks if both down and up are available options
                if(DFSHolder.getPreLocation() != DFSHolder.getLocation() + DFSHolder.getNumColumns() && DFSHolder.getLocation() + DFSHolder.getNumColumns() <= (DFSHolder.getNumColumns() * DFSHolder.getNumRows()) && DFSHolder.getPreLocation() != DFSHolder.getLocation() - DFSHolder.getNumColumns() && DFSHolder.getLocation() - DFSHolder.getNumColumns() > 0 && Character.getNumericValue(DFSHolder.getMaze().charAt((DFSHolder.getLocation() - DFSHolder.getNumColumns()) - 1))  > 1) {
                    if(DFSHolder.getLocation() + DFSHolder.getNumColumns() <= DFSHolder.getEndPosition()) {
                        DFSHolder.setPreLocation(DFSHolder.getLocation());
                        DFSHolder.setLocation(DFSHolder.getLocation() + DFSHolder.getNumColumns());
                        DFSHolder.setSteps(DFSHolder.getSteps() + 1);
                        DFS(DFSHolder);
                        if(DFSHolder.isPathSolved()){
                            break;
                        }
                        //then if we haven't solved it we try to go the other way
                        System.out.println("We Got here for case2");
                        if(currentLoc == DFSHolder.getLocation()){
                            System.out.println("ITS FINE");
                        }
                        DFSHolder.setPreLocation(currentLoc);
                        DFSHolder.setLocation(currentLoc - DFSHolder.getNumColumns());
                        DFSHolder.setSteps(DFSHolder.getSteps() + 1);
                        DFS(DFSHolder);
                    }
                    else{
                        System.out.println("Printing character wall " + DFSHolder.getMaze().charAt((DFSHolder.getLocation() - DFSHolder.getNumColumns()) - 1));
                        DFSHolder.setPreLocation(DFSHolder.getLocation());
                        DFSHolder.setLocation(DFSHolder.getLocation() - DFSHolder.getNumColumns());
                        DFSHolder.setSteps(DFSHolder.getSteps() + 1);
                        DFS(DFSHolder);

                        if(DFSHolder.isPathSolved()){
                            break;
                        }
                        //then if we haven't solved it we try to go the other way
                        System.out.println("We Got here for case2");
                        if(currentLoc == DFSHolder.getLocation()){
                            System.out.println("ITS FINE");
                        }
                        DFSHolder.setPreLocation(currentLoc);
                        DFSHolder.setLocation(currentLoc + DFSHolder.getNumColumns());
                        DFSHolder.setSteps(DFSHolder.getSteps() + 1);
                        DFS(DFSHolder);
                    }
                }
                else if(currentPrelocation != currentLoc + DFSHolder.getNumColumns() && DFSHolder.getLocation() + DFSHolder.getNumColumns() <= (DFSHolder.getNumColumns() * DFSHolder.getNumRows())){ //going up
                    DFSHolder.setPreLocation(DFSHolder.getLocation());
                    DFSHolder.setLocation(DFSHolder.getLocation() + DFSHolder.getNumColumns());
                    DFSHolder.setSteps(DFSHolder.getSteps() + 1);
                    DFS(DFSHolder);
                }
                if(DFSHolder.isPathSolved()){
                    break;
                }
                if(currentLoc - DFSHolder.getNumColumns() > 0 && currentPrelocation != currentLoc - DFSHolder.getNumColumns() && Character.getNumericValue(DFSHolder.getMaze().charAt((DFSHolder.getLocation() - DFSHolder.getNumColumns()) - 1))  > 1){
                    DFSHolder.setPreLocation(currentLoc);
                    DFSHolder.setLocation(currentLoc - DFSHolder.getNumColumns());
                    DFSHolder.setSteps(DFSHolder.getSteps() + 1);
                    DFS(DFSHolder);
                }
                System.out.println("broke");
                break;
            case '3': // both right and down open
                System.out.println("Case 3 " + currentLoc);
                if(DFSHolder.getLocation() % DFSHolder.getNumColumns() != 0 && DFSHolder.getPreLocation() != DFSHolder.getLocation() + 1){
                    if(DFSHolder.getLocation() + DFSHolder.getNumColumns() <= (DFSHolder.getNumColumns() * DFSHolder.getNumRows()) && DFSHolder.getPreLocation() != DFSHolder.getLocation() + DFSHolder.getNumColumns()){
                        if(DFSHolder.getLocation() + DFSHolder.getNumColumns() <= DFSHolder.getEndPosition()){
                            DFSHolder.setPreLocation(DFSHolder.getLocation());
                            DFSHolder.setLocation(DFSHolder.getLocation() + DFSHolder.getNumColumns());
                            DFSHolder.setSteps(DFSHolder.getSteps() + 1);
                            DFS(DFSHolder);
                            if(DFSHolder.isPathSolved()){
                                break;
                            }
                            DFSHolder.setPreLocation(currentLoc);
                            DFSHolder.setLocation(currentLoc + 1);
                            DFSHolder.setSteps(DFSHolder.getSteps() + 1);
                            DFS(DFSHolder);
                        }
                        else{
                            DFSHolder.setPreLocation(currentLoc);
                            DFSHolder.setLocation(currentLoc + 1);
                            DFSHolder.setSteps(DFSHolder.getSteps() + 1);
                            DFS(DFSHolder);
                            if(DFSHolder.isPathSolved()){
                                break;
                            }
                            DFSHolder.setPreLocation(currentLoc);
                            DFSHolder.setLocation(currentLoc + DFSHolder.getNumColumns());
                            DFSHolder.setSteps(DFSHolder.getSteps() + 1);
                            DFS(DFSHolder);
                        }
                    }
                    else{
                        DFSHolder.setPreLocation(DFSHolder.getLocation());
                        DFSHolder.setLocation(DFSHolder.getLocation() + 1);
                        DFSHolder.setSteps(DFSHolder.getSteps() + 1);
                        DFS(DFSHolder);
                    }
                }
                else if (DFSHolder.getLocation() + DFSHolder.getNumColumns() <= (DFSHolder.getNumColumns() * DFSHolder.getNumRows()) && DFSHolder.getPreLocation() != DFSHolder.getLocation() + DFSHolder.getNumColumns()){
                    DFSHolder.setPreLocation(DFSHolder.getLocation());
                    DFSHolder.setLocation(DFSHolder.getLocation() + DFSHolder.getNumColumns());
                    DFSHolder.setSteps(DFSHolder.getSteps() + 1);
                    DFS(DFSHolder);
                }
                //up
                if(DFSHolder.isPathSolved()){
                    break;
                }
                if(currentLoc - DFSHolder.getNumColumns() > 0 && currentLoc - DFSHolder.getNumColumns() != currentPrelocation && Character.getNumericValue(DFSHolder.getMaze().charAt((DFSHolder.getLocation() - DFSHolder.getNumColumns()) - 1))  > 1){
                    DFSHolder.setPreLocation(currentLoc);
                    DFSHolder.setLocation(currentLoc - DFSHolder.getNumColumns());
                    DFSHolder.setSteps(DFSHolder.getSteps() + 1);
                    DFS(DFSHolder);
                }
                if(DFSHolder.isPathSolved()){
                    break;
                }
                if((currentLoc - 1) % DFSHolder.getNumColumns() != 0 && currentLoc - 1 != currentPrelocation && Character.getNumericValue(DFSHolder.getMaze().charAt(DFSHolder.getLocation() - 2)) % 2 != 0){
                    DFSHolder.setPreLocation(currentLoc);
                    DFSHolder.setLocation(currentLoc - 1);
                    DFSHolder.setSteps(DFSHolder.getSteps() + 1);
                    DFS(DFSHolder);
                }
                System.out.println("broke");
                break;
        }
        if(DFSHolder.isPathSolved()){
            DFSHolder.setPath(currentLoc + DFSHolder.getPath());
        }
        return DFSHolder;
    }

     */
//will recurse through this function moving in a possible direction until the end location is found, the path and steps taken are recorded and printed
    static DFSObject DFS2(DFSObject DFSHolder) {
        int i;
        //a current prelocation and location is used to keep track of where this instance is as when a recursion ends its information is passed to the recursive instace that called it, including its locational data
        int currentPrelocation = DFSHolder.getPreLocation();
        int currentLoc = DFSHolder.getLocation();
        char walls = DFSHolder.getMaze().charAt(DFSHolder.getLocation() - 1); // char list is from 0 and location goes from 1 and getmaze is a string of all openess. so it will get the openness of our current location
        //when we find the end position
        if (DFSHolder.getLocation() == DFSHolder.getEndPosition()) {
            //solve the path so every function instance currently on hold will be given this information and know to put its location into the path
            DFSHolder.setPathSolved(true);
            //sets our current location as the end of the path
            DFSHolder.setPath(DFSHolder.getPath()  + DFSHolder.getLocation());
            //increments the tracker of steps taken in path
            DFSHolder.setStepsForPath(DFSHolder.getStepsForPath() + 1);
            return DFSHolder;
        }
        //this just loops through the directions. going into all possible ones.
        for (i = 0; i < 4; i++) {
            if (isPossible(DFSHolder, i)) {
                switch (i) {
                    case 0 -> { //right
                        DFSHolder.setPreLocation(currentLoc); //sets up new location and pre-location variables for the next node
                        DFSHolder.setLocation(currentLoc + 1);
                        //holds the actual steps taken to find the solution
                        DFSHolder.setSteps(DFSHolder.getSteps() + 1);
                        //recurses,
                        DFS2(DFSHolder);
                        //breaks the for if the end is found
                        if (DFSHolder.isPathSolved()) {
                            i = 10; //break the loop
                        }
                        break;
                    }
                    case 1 -> { //down
                        DFSHolder.setPreLocation(currentLoc);
                        DFSHolder.setLocation(currentLoc + DFSHolder.getNumColumns());
                        DFSHolder.setSteps(DFSHolder.getSteps() + 1);
                        DFS2(DFSHolder);
                        if (DFSHolder.isPathSolved()) {
                            i = 10; //break the loop
                        }
                    }
                    case 2 -> { //left
                        DFSHolder.setPreLocation(currentLoc);
                        DFSHolder.setLocation(currentLoc - 1);
                        DFSHolder.setSteps(DFSHolder.getSteps() + 1);
                        DFS2(DFSHolder);
                        if (DFSHolder.isPathSolved()) {
                            i = 10; //break the loop
                        }
                    }
                    case 3 -> { //up
                        DFSHolder.setPreLocation(currentLoc);
                        DFSHolder.setLocation(currentLoc - DFSHolder.getNumColumns());
                        DFSHolder.setSteps(DFSHolder.getSteps() + 1);
                        DFS2(DFSHolder);
                        if (DFSHolder.isPathSolved()) {
                            i = 10; //break the loop
                        }
                    }
                }
                //sets the locations back to what their meant to be, before trying a new path
                DFSHolder.setPreLocation(currentPrelocation);
                DFSHolder.setLocation(currentLoc);
            }
        }
        //when the end is found all recursive instances that were used to get to that path will end and they will have held the true pathsolved attribute from the finish node and know to add their location to the path
        if (DFSHolder.isPathSolved()){
            DFSHolder.setPath(currentLoc +","+ DFSHolder.getPath());
            DFSHolder.setStepsForPath(DFSHolder.getStepsForPath() + 1);
        }
        return DFSHolder;
    }
    //a function used to determine if a direction to travel is possible based on the maze's size and openness of this location and surrounding locations
    static boolean isPossible(DFSObject DFSHolder, int dir){
        switch(dir) {
            //Right
            case 0:
                //checks if the previous location is where we are trying to go and if this move would wrap use onto the next column down from going right
                if (DFSHolder.getPreLocation() != DFSHolder.getLocation() + 1 && DFSHolder.getLocation() % DFSHolder.getNumColumns() != 0 ) {
                    //checks the openness of our current location and makes sure its 1 or 3
                    if (Character.getNumericValue(DFSHolder.getMaze().charAt(DFSHolder.getLocation() - 1)) % 2 != 0) {
                        return true;
                    }
                }
                break;
            //Down
            case 1:
                ////checks if the previous location is where we are trying to go and if moving down would put us through the maze's bottom
                if (DFSHolder.getPreLocation() != DFSHolder.getLocation() + DFSHolder.getNumColumns() && DFSHolder.getLocation() + DFSHolder.getNumColumns() <= (DFSHolder.getNumColumns() * DFSHolder.getNumRows())){
                    //checks openness of this location and if its 2,3 you can move down
                    if (Character.getNumericValue(DFSHolder.getMaze().charAt((DFSHolder.getLocation() - 1)))  > 1) {
                        return true;
                    }
                }
                break;
            //Left
            case 2:
                // check if the move is possible
                if((DFSHolder.getLocation() - 1) % DFSHolder.getNumColumns() != 0 && DFSHolder.getLocation() - 1 != DFSHolder.getPreLocation()){
                    //will check the openness of the location to the left (char starts at 0 and location from 1) ensuring the left openness is 1,3
                    if (Character.getNumericValue(DFSHolder.getMaze().charAt(DFSHolder.getLocation() - 2)) % 2 != 0) {
                        return true;
                    }
                }
                break;
            //Up
            case 3:
                //checking if the move is possible
                if(DFSHolder.getLocation() - DFSHolder.getNumColumns() > 0 && DFSHolder.getLocation() - DFSHolder.getNumColumns() != DFSHolder.getPreLocation()){
                    //same thing as case 1 but checks the openness of the location above as it needs to be 2,3
                    if (Character.getNumericValue(DFSHolder.getMaze().charAt((DFSHolder.getLocation() - DFSHolder.getNumColumns()) - 1)) > 1){
                        return true;
                    }
                }
                break;
        }
        // returns false by default
        return false;
    }
//this function takes the completed DFSHolder with all openness, path, start, finish and prints a maze with a path with it
    public static void PrinttextMaze(DFSObject DFSHolder){
        //constructing the walls
        String onLine = "-";
        String betweenLine = "|";
        //our location to go through the entire maze
        int location = 1;
        //the openness of this node
        int direction;
        //changing the stored path example. x,xx,x,xx,xxx into a useable array of values
        int[] storePath = new int[DFSHolder.getStepsForPath()];
        int q = 0;
        int p = 0;
        String holder = ""; //holds the current values of a path location
        //goes through the string and takes the path location values and puts them in a int array, sectioned by the ,'s in the path string
        while(p < DFSHolder.getPath().length()){
            if(DFSHolder.getPath().charAt(p) == ','){
                storePath[q] = Integer.parseInt(holder);
                //increments the storing array
                q++;
                holder = "";
            }
            else {
                //adds to the current string
                holder += DFSHolder.getPath().charAt(p);
            }
            p++;
        }
        //puts the last value in
        storePath[q] = Integer.parseInt(holder);

        //used to return 2 values from a function
        String[] returnHolder;
        for(int i = 0 ; i < DFSHolder.getNumColumns(); i++){
            onLine += "---";
        }
        //prints the top wall
        System.out.println(onLine);

        for(int i = 0; i < DFSHolder.getNumRows(); i++){
            onLine = "|";
            betweenLine = "|";
            for(int j = 0; j < DFSHolder.getNumColumns(); j++){
                //grabs openness of current location
                direction = Character.getNumericValue(DFSHolder.getMaze().charAt(location - 1));
                //generates the whitespace or walls for the lines and checks if we are on the path
                returnHolder = PrintWalls(direction,onLine,betweenLine,storePath, location);
                //saving values
                onLine = returnHolder[0];
                betweenLine = returnHolder[1];
                //increment our tracker
                location++;
            }
            //prints our online
            System.out.println(onLine);
            //prints our between line if were not at the end of the maze
            if(i + 1 != DFSHolder.getNumRows()){
                System.out.println(betweenLine);
            }
        }
        //prints the bottom wall
        onLine = "-";
        for(int i = 0 ; i < DFSHolder.getNumColumns(); i++){
            onLine += "---";
        }
        System.out.println(onLine);
    }
    //adds the required characters when printing the completed maze
    public static String[] PrintWalls(int direction, String onLine, String betweenLine, int[] path, int location){
        String[] Lines = new String[]{onLine,betweenLine};
        boolean start = false;
        boolean finish = false;
        boolean onPath = false;
        //[0] is online
        //[1] is betweenLines
        //checks if the current location is on the path(including the start and finish
        for(int p = 0; p < path.length; p++){
            if(path[p] == location){
                if(path[0] == location){
                    start = true;
                }
                if(path[path.length-1] == location){
                    finish = true;
                }
                onPath = true;
                break;
            }
        }
        //creates the walls and necessary path values based on this locations openness
        switch(direction){
            case 0:{
                //will create one below and to the right
                if(start){
                    Lines[0] += "S |";
                }
                else if(finish){
                    Lines[0] += "F |";
                }
                else if(onPath){
                    Lines[0] += "* |";
                }
                else{
                    Lines[0] += "  |";
                }
                Lines[1] += "--";
                break;
            }
            case 1:{
                //will create one below
                if(start){
                    Lines[0] += "S  ";
                }
                else if(finish){
                    Lines[0] += "F  ";
                }
                else if(onPath){
                    Lines[0] += "*  ";
                }
                else{
                    Lines[0] += "   ";
                }
                Lines[1] += "--";
                break;
            }
            case 2:{
                if(start){
                    Lines[0] += "S |";
                }
                else if(finish){
                    Lines[0] += "F |";
                }
                else if(onPath){
                    Lines[0] += "* |";
                }
                else{
                    Lines[0] += "  |";
                }
                Lines[1] += "  ";
                //will create one to the right
                break;
            }
            default:{
                //case 3 has right and down open so it wont need to create walls

                if(start){
                    Lines[0] += "S  ";
                }
                else if(finish){
                    Lines[0] += "F  ";
                }
                else if(onPath){
                    Lines[0] += "*  ";
                }
                else{
                    Lines[0] += "   ";
                }
                Lines[1] += "  ";
            }
        }
        Lines[1] += "|";
        return Lines;
    }
}
