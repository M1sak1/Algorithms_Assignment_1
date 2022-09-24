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
        //A small scale solution of small problems is all that's needed now
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
    public static DFSObject DFS(DFSObject DFSHolder){
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

    static DFSObject DFS2(DFSObject DFSHolder) {
        //System.out.println(Arrays.deepToString(maze) + "\n" + Arrays.toString(start) + "\n" + Arrays.toString(finish));
        int i;
        int currentPrelocation = DFSHolder.getPreLocation();
        int currentLoc = DFSHolder.getLocation();
        char walls = DFSHolder.getMaze().charAt(DFSHolder.getLocation() - 1); // char list is from 0 and location goes from 1 and getmaze is a string of the walls
        if (DFSHolder.getLocation() == DFSHolder.getEndPosition()) {
            System.out.println("We got here from finishing it");
            DFSHolder.setPathSolved(true);
            DFSHolder.setPath(DFSHolder.getPath()  + DFSHolder.getLocation());
            DFSHolder.setStepsForPath(DFSHolder.getStepsForPath() + 1);
            return DFSHolder;
        }
        //this just loops through the directions. going into all possible ones.
        for (i = 0; i < 4; i++) {
            if (isPossible(DFSHolder, i)) {
                switch (i) {
                    case 0 -> { //right
                        DFSHolder.setPreLocation(currentLoc);
                        DFSHolder.setLocation(currentLoc + 1);
                        DFSHolder.setSteps(DFSHolder.getSteps() + 1);
                        DFS2(DFSHolder);
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
                DFSHolder.setPreLocation(currentPrelocation);
                DFSHolder.setLocation(currentLoc);
            }
        }
        if (DFSHolder.isPathSolved()){
            DFSHolder.setPath(currentLoc +","+ DFSHolder.getPath());
            DFSHolder.setStepsForPath(DFSHolder.getStepsForPath() + 1);
        }
        return DFSHolder;
    }
    static boolean isPossible(DFSObject DFSHolder, int dir){
        switch(dir) {
            //Right
            case 0:
                if (DFSHolder.getPreLocation() != DFSHolder.getLocation() + 1 && DFSHolder.getLocation() % DFSHolder.getNumColumns() != 0 ) {
                    if (Character.getNumericValue(DFSHolder.getMaze().charAt(DFSHolder.getLocation() - 1)) % 2 != 0) {
                        return true;
                    }
                }
                break;
            //Down
            case 1:
                if (DFSHolder.getPreLocation() != DFSHolder.getLocation() + DFSHolder.getNumColumns() && DFSHolder.getLocation() + DFSHolder.getNumColumns() <= (DFSHolder.getNumColumns() * DFSHolder.getNumRows())){
                    if (Character.getNumericValue(DFSHolder.getMaze().charAt((DFSHolder.getLocation() - 1)))  > 1) {
                        return true;
                    }
                }
                break;
            //Left
            case 2:
                // check if the move is possible
                if((DFSHolder.getLocation() - 1) % DFSHolder.getNumColumns() != 0 && DFSHolder.getLocation() - 1 != DFSHolder.getPreLocation()){
                    if (Character.getNumericValue(DFSHolder.getMaze().charAt(DFSHolder.getLocation() - 2)) % 2 != 0) {
                        return true;
                    }
                }
                break;
            //Up
            case 3:
                //checking if the move is possible
                if(DFSHolder.getLocation() - DFSHolder.getNumColumns() > 0 && DFSHolder.getLocation() - DFSHolder.getNumColumns() != DFSHolder.getPreLocation()){
                    if (Character.getNumericValue(DFSHolder.getMaze().charAt((DFSHolder.getLocation() - DFSHolder.getNumColumns()) - 1)) > 1){
                        return true;
                    }
                }
                break;
        }
        // returns false by default
        return false;
    }
}
