import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.util.Queue;
import java.io.FileNotFoundException;
public class BFS {
    public static void main(String[] Args){
        String rawData = "";
        try{
            File opfil = new File("Maze.txt");
            Scanner reader = new Scanner(opfil);
            while(reader.hasNextLine()){
                rawData = reader.nextLine();
            }
        }
        catch(FileNotFoundException e){
            System.out.println("There is no file.");
        }

        int[][] maze = generateMatrix(rawData);
    }
    static int[][] generateMatrix(String inp){
        //split the data into its important points
        //0: num rows and columns. - 1: start - 2: End. 3: Maze data.
        String[] data = inp.split(":");
        String[] MazeInf = data[3].split("");
        System.out.println(Arrays.toString(MazeInf));
        String[] size = data[0].split(",");
        int[][] maze = new int[Integer.parseInt(size[0])][Integer.parseInt(size[1])];
        int i,j;
        for(i = 0; i < maze.length; i++){
            for(j = 0; j < maze[0].length; j++){
                maze[i][j] = Integer.parseInt(MazeInf[i * maze[0].length + j]);
                //System.out.println(maze[i][j]);
            }
        }
        System.out.println(Arrays.deepToString(maze));
        return maze;
    }

    static boolean isPossible(int[][] maze, int dir, int Row, int Col){
        switch(dir) {
            //Right
            case 0:
                if (Col + 1 < maze.length) {
                    if (maze[Row][Col] != 0 && maze[Row][Col] != 2) {
                        return true;
                    }
                }
                break;
            //Down
            case 1:
                if (Row + 1 < maze[0].length){
                    if (maze[Row][Col] != 0 && maze[Row][Col] != 1) {
                        return true;
                    }
                }
                break;
            //Left
            case 2:
                // check if the move is possible
                if(Col - 1 > 0){
                    if (maze[Row][Col - 1] == 1 || maze[Row][Col - 1] == 3){
                        return true;
                    }
                }
                break;
            //Up
            case 3:
                //checking if the move is possible
                if(Row - 1 > 0){
                    if(maze[Row - 1][Col] == 2 || maze[Row - 1][Col] == 3){
                        return true;
                    }
                }
                break;
        }
        // returns false by default
        return false;
    }
}
