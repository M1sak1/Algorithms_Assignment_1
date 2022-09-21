import java.util.Scanner; // Import the Scanner class to read text files
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
public class DFS {
    public static void main(String[] args) {
        String Maze = "";
        try {
            File MazeFile = new File("Maze.txt");
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

        DFS(DFSHolder);
        System.out.println(DFSHolder.getPath());
        System.out.println(DFSHolder.getSteps());
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
        int currentLoc = DFSHolder.getLocation();
        char walls = DFSHolder.getMaze().charAt(DFSHolder.getLocation() - 1); // char list is from 0 and location goes from 1 and getmaze is a string of the walls
        System.out.println(walls);
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
                System.out.println("Case 0");
                if(DFSHolder.getLocation() - 1 % DFSHolder.getNumColumns() != 0 && DFSHolder.getPreLocation() != DFSHolder.getLocation() - 1) { //can actually go left ,and it's not where you came from
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
                break;
            case '1': //right only open
                System.out.println("Case 1");
                //If both left and right are valid options to travers this will compute the best one to go in hopes we get to the end the fastest
                if(DFSHolder.getPreLocation() != DFSHolder.getLocation() + 1 && DFSHolder.getLocation() - 1 % DFSHolder.getNumColumns() != 0 && DFSHolder.getPreLocation() != DFSHolder.getLocation() - 1){
                    if(DFSHolder.getLocation() % DFSHolder.getNumColumns() <= DFSHolder.getEndPosition() % DFSHolder.getNumColumns()){
                        //a number divided by the columns will always have the same decimals as the number a column below, therefore we can observe the decimals of our current position to determine if going left or right would be the better option
                        DFSHolder.setPreLocation(DFSHolder.getLocation());
                        DFSHolder.setLocation(DFSHolder.getLocation() + 1);
                        DFSHolder.setSteps(DFSHolder.getSteps() + 1);
                        DFS(DFSHolder);
                        if(DFSHolder.isPathSolved()){
                            break;
                        }
                        //then if we haven't solved it we try to go the other way
                        DFSHolder.setPreLocation(DFSHolder.getLocation());
                        DFSHolder.setLocation(DFSHolder.getLocation() - 1);
                        DFSHolder.setSteps(DFSHolder.getSteps() + 1);
                        DFS(DFSHolder);
                    }

                }
                if(DFSHolder.getPreLocation() != DFSHolder.getLocation() + 1) { //try down
                    DFSHolder.setPreLocation(DFSHolder.getLocation());
                    DFSHolder.setLocation(DFSHolder.getLocation() + 1);
                    DFSHolder.setSteps(DFSHolder.getSteps() + 1);
                    DFS(DFSHolder);
                }
                if(DFSHolder.isPathSolved()){
                    break;
                }
                //then we try up
                if(DFSHolder.getLocation() - 1 % DFSHolder.getNumColumns() != 0 && DFSHolder.getPreLocation() != DFSHolder.getLocation() - 1 ) { //going left and checks if possible
                    DFSHolder.setPreLocation(DFSHolder.getLocation());
                    DFSHolder.setLocation(DFSHolder.getLocation() - 1);
                    DFSHolder.setSteps(DFSHolder.getSteps() + 1);
                    DFS(DFSHolder);
                }
                break;
            case '2': //down only open
                System.out.println("Case 2");
                //checks if both down and up are available options
                if(DFSHolder.getPreLocation() != DFSHolder.getLocation() + DFSHolder.getNumColumns() && DFSHolder.getLocation() - DFSHolder.getNumColumns() > 0 && DFSHolder.getPreLocation() != DFSHolder.getLocation() - DFSHolder.getNumColumns()) {
                    if(DFSHolder.getLocation() + DFSHolder.getNumColumns() <= DFSHolder.getEndPosition()) {
                        DFSHolder.setPreLocation(DFSHolder.getLocation());
                        DFSHolder.setLocation(DFSHolder.getLocation() + DFSHolder.getNumColumns());
                        DFSHolder.setSteps(DFSHolder.getSteps() + 1);
                        DFS(DFSHolder);
                        if(DFSHolder.isPathSolved()){
                            break;
                        }
                        //then if we haven't solved it we try to go the other way
                        DFSHolder.setPreLocation(DFSHolder.getLocation());
                        DFSHolder.setLocation(DFSHolder.getLocation() - DFSHolder.getNumColumns());
                        DFSHolder.setSteps(DFSHolder.getSteps() + 1);
                        DFS(DFSHolder);
                    }
                }
                if(DFSHolder.getPreLocation() != DFSHolder.getLocation() + DFSHolder.getNumColumns()){ //going down
                    System.out.println(DFSHolder.getLocation() + " down");
                    DFSHolder.setPreLocation(DFSHolder.getLocation());
                    DFSHolder.setLocation(DFSHolder.getLocation() + DFSHolder.getNumColumns());
                    DFSHolder.setSteps(DFSHolder.getSteps() + 1);
                    DFS(DFSHolder);
                }
                if(DFSHolder.isPathSolved()){
                    break;
                }
                if( DFSHolder.getLocation() - DFSHolder.getNumColumns() > 0 && DFSHolder.getPreLocation() != DFSHolder.getLocation() - DFSHolder.getNumColumns()){
                    System.out.println(DFSHolder.getLocation() + " up");
                    DFSHolder.setPreLocation(DFSHolder.getLocation());
                    DFSHolder.setLocation(DFSHolder.getLocation() - DFSHolder.getNumColumns());
                    DFSHolder.setSteps(DFSHolder.getSteps() + 1);
                    DFS(DFSHolder);
                }
                break;
            case '3': // both right and down open
                System.out.println("Case 3");
                if(DFSHolder.getLocation() + 1 % DFSHolder.getNumColumns() != 0 && DFSHolder.getPreLocation() != DFSHolder.getLocation() + 1){
                    if(DFSHolder.getLocation() + DFSHolder.getNumColumns() < (DFSHolder.getNumColumns() * DFSHolder.getNumRows()) && DFSHolder.getPreLocation() != DFSHolder.getLocation() + DFSHolder.getNumColumns()){
                        if(DFSHolder.getLocation() + DFSHolder.getNumColumns() <= DFSHolder.getEndPosition()){
                            DFSHolder.setPreLocation(DFSHolder.getLocation());
                            DFSHolder.setLocation(DFSHolder.getLocation() + DFSHolder.getNumColumns());
                            DFSHolder.setSteps(DFSHolder.getSteps() + 1);
                            DFS(DFSHolder);
                        }
                        else{
                            DFSHolder.setPreLocation(DFSHolder.getLocation());
                            DFSHolder.setLocation(DFSHolder.getLocation() + 1);
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
                else{
                    DFSHolder.setPreLocation(DFSHolder.getLocation());
                    DFSHolder.setLocation(DFSHolder.getLocation() + DFSHolder.getNumColumns());
                    DFSHolder.setSteps(DFSHolder.getSteps() + 1);
                    DFS(DFSHolder);
                }
                break;
        }
        if(DFSHolder.isPathSolved()){
            DFSHolder.setPath(currentLoc + DFSHolder.getPath());
        }
        return DFSHolder;
    }
}
