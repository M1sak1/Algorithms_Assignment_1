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
    }

    public static void MazeInformation(DFSObject DFSHolder, String MazeFile){
        int whichvalue = 0;
        String output = "";
        for(int i = 0; i < MazeFile.length();i++){
            if(Character.compare(MazeFile.charAt(i),':') == 0){
                if(whichvalue == 0){
                    DFSHolder.setNumRows(Integer.parseInt(output.substring(0,output.indexOf(',')))); //grabs the number of rows and converts into an int
                    DFSHolder.setNumColumns(Integer.parseInt(output.substring(output.indexOf(',') + 1))); //grabs the number of columns and converts into an int
                }
                if(whichvalue == 1){
                    DFSHolder.setLocation(Integer.parseInt(output));
                }
                if(whichvalue == 2){
                    DFSHolder.setEndPosition(Integer.parseInt(output));
                }
                whichvalue++;
                output = "";
            }
            else{
                output += MazeFile.charAt(i);
            }
        }
        DFSHolder.setMaze(output);
        System.out.println(DFSHolder.getMaze());
    }
    public static DFSObject DFS(DFSObject DFSHolder){
        int walls = DFSHolder.getMaze().charAt(DFSHolder.getLocation());
        if(DFSHolder.getLocation() == DFSHolder.getEndPosition()){
            DFSHolder.setPathSolved(true);
            DFSHolder.setPath(DFSHolder.getPath() + DFSHolder.getLocation());
            return DFSHolder;
        }
        switch(walls){
            case 0: // right and down closed
                if(DFSHolder.getLocation() - 1 % DFSHolder.getNumColumns() != 0 && DFSHolder.getPreLocation() != DFSHolder.getLocation() - 1){
                    if(DFSHolder.getLocation() - DFSHolder.getNumColumns() < 1 && DFSHolder.getPreLocation() != DFSHolder.getLocation() - DFSHolder.getNumColumns()){
                        if(DFSHolder.getLocation() - DFSHolder.getNumColumns() >= DFSHolder.getEndPosition()){
                            DFSHolder.setPreLocation(DFSHolder.getLocation());
                            DFSHolder.setLocation(DFSHolder.getLocation() - DFSHolder.getNumColumns());
                            DFSHolder.setSteps(DFSHolder.getSteps() + 1);
                            DFS(DFSHolder);
                        }
                        else{
                            DFSHolder.setPreLocation(DFSHolder.getLocation());
                            DFSHolder.setLocation(DFSHolder.getLocation() - 1);
                            DFSHolder.setSteps(DFSHolder.getSteps() + 1);
                            DFS(DFSHolder);
                        }
                    }
                    else{
                        DFSHolder.setPreLocation(DFSHolder.getLocation());
                        DFSHolder.setLocation(DFSHolder.getLocation() - 1);
                        DFSHolder.setSteps(DFSHolder.getSteps() + 1);
                        DFS(DFSHolder);
                    }
                }
                else{
                    DFSHolder.setPreLocation(DFSHolder.getLocation());
                    DFSHolder.setLocation(DFSHolder.getLocation() - DFSHolder.getNumColumns());
                    DFSHolder.setSteps(DFSHolder.getSteps() + 1);
                    DFS(DFSHolder);
                }
                break;
            case 1: //right only open
                if(DFSHolder.getPreLocation() != DFSHolder.getLocation() + 1) {
                    DFSHolder.setPreLocation(DFSHolder.getLocation());
                    DFSHolder.setLocation(DFSHolder.getLocation() + 1);
                    DFSHolder.setSteps(DFSHolder.getSteps() + 1);
                    DFS(DFSHolder);
                }
                else{ //going left
                    DFSHolder.setPreLocation(DFSHolder.getLocation());
                    DFSHolder.setLocation(DFSHolder.getLocation() - 1);
                    DFSHolder.setSteps(DFSHolder.getSteps() + 1);
                    DFS(DFSHolder);
                }
                break;
            case 2: //down only open
                if(DFSHolder.getPreLocation() != DFSHolder.getLocation() + DFSHolder.getNumColumns()) {
                    DFSHolder.setPreLocation(DFSHolder.getLocation());
                    DFSHolder.setLocation(DFSHolder.getLocation() + DFSHolder.getNumColumns());
                    DFSHolder.setSteps(DFSHolder.getSteps() + 1);
                    DFS(DFSHolder);
                }
                else{ //going up
                    DFSHolder.setPreLocation(DFSHolder.getLocation());
                    DFSHolder.setLocation(DFSHolder.getLocation() - DFSHolder.getNumColumns());
                    DFSHolder.setSteps(DFSHolder.getSteps() + 1);
                    DFS(DFSHolder);
                }
                break;
            case 3: // both right and down open
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
            DFSHolder.setPath(DFSHolder.getPreLocation() + DFSHolder.getPath());
        }
        return DFSHolder;
    }
}
