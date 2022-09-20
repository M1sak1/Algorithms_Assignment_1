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
                    DFSHolder.setNumRows(Integer.parseInt(output.substring(0,output.indexOf(','))));
                    DFSHolder.setNumColumns(Integer.parseInt(output.substring(output.indexOf(','))));
                    System.out.println(output);
                    System.out.println(DFSHolder.getNumRows());
                    System.out.println(DFSHolder.getNumColumns());
                }
                if(whichvalue == 1){
                    System.out.println(output);
                }
                if(whichvalue == 2){
                    System.out.println(output);
                }
                whichvalue++;
                output = "";
            }
            else{
                output += MazeFile.charAt(i);
            }
        }
        System.out.println(output);
    }
    public static DFSObject DFS(DFSObject DFSHolder){
        return DFSHolder;
    }
}
