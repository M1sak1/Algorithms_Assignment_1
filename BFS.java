import java.time.Instant;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Object;
public class BFS {
    public static void main(String[] Args){
        //Scanner imp = new Scanner(System.in);
        //System.out.println("Please input the name of the file containing the maze (including the file extention.)");
        String Fname = Args[0]; //imp.nextLine();
        System.out.println(Args[0]);
        long start = System.currentTimeMillis();
        String rawData = "";
        try{
            File opfil = new File(Fname);
            Scanner reader = new Scanner(opfil);
            while(reader.hasNextLine()){
                rawData = reader.nextLine();
            }
        }
        catch(FileNotFoundException e){
            System.out.println("There is no file.");
        }
        // getting the data back from the generator
        List<Cloneable> data = generateMatrix(rawData);
        bfs((int[][]) data.get(0), (int[]) data.get(1), (int[]) data.get(2));
        long end = System.currentTimeMillis();
        System.out.println("\nThis program took: " + (end - start) + "ms to complete");
    }

    static void bfs(int[][] maze, int[] start, int[] finish){
        //System.out.println(Arrays.deepToString(maze) + "\n" + Arrays.toString(start) + "\n" + Arrays.toString(finish));
        Queue<int[]> bfs = new LinkedList<>();
        boolean[][] visList = new boolean[maze.length][maze[0].length];
        int i;
        boolean found = false;
        while(!found){
            if(start[0] == finish[0] && start[1] == finish[1]){
                // System.out.println("\ngotya");
                found = true;
            }
            //this just loops through the directions. enqueueing all possible ones.
            for(i = 0; i < 4; i ++){
                if(isPossible(maze, i, start[0], start[1], visList)){
                    int[] imp = new int[2];
                    switch (i) {
                        case 0 -> {
                            imp[0] = start[0];
                            imp[1] = start[1] + 1;
                        }
                        case 1 -> {
                            imp[0] = start[0] + 1;
                            imp[1] = start[1];
                        }
                        case 2 -> {
                            imp[0] = start[0];
                            imp[1] = start[1] - 1;
                        }
                        case 3 -> {
                            imp[0] = start[0] - 1;
                            imp[1] = start[1];
                        }
                    }
                    bfs.add(imp);
                }
            }
            //System.out.print(Arrays.toString(start) + " ");
            System.out.print(start[0] * maze[0].length + start[1] + " ");
            if(bfs.isEmpty()){ break; } // breaking if the Q is empty

            visList[start[0]][start[1]] = true; // this is to avoid repatation.

            start = bfs.remove();

        }
    }

    static List<Cloneable> generateMatrix(String inp){
        //split the data into its important points
        //0: num rows and columns. - 1: start - 2: End. 3: Maze data.
        String[] data = inp.split(":");
        String[] MazeInf = data[3].split("");
        //System.out.println(Arrays.toString(MazeInf));
        String[] size = data[0].split(",");
        int[][] maze = new int[Integer.parseInt(size[0])][Integer.parseInt(size[1])];
        int[] startCoords = new int[2];
        int[] endCoords = new int[2];
        int i,j;
        //System.out.println(data[1]);
        for(i = 0; i < maze.length; i++){
            for(j = 0; j < maze[0].length; j++){
                int coordval = i * maze[0].length + j;
                maze[i][j] = Integer.parseInt(MazeInf[coordval]);
                //System.out.println(coordval + " " + data[1]);
                if(coordval == Integer.parseInt(data[1])){
                    System.out.println(coordval);
                    //System.out.println("boop");
                    startCoords[0] = i;
                    startCoords[1] = j;
                }
                if(coordval == Integer.parseInt(data[2])){
                    endCoords[0] = i;
                    endCoords[1] = j;
                }
                //System.out.println(maze[i][j]);
            }
        }
        //System.out.println(Arrays.deepToString(maze));
        return Arrays.asList(maze, startCoords, endCoords);
    }

    // Returns false if you cannot move to it.
    static boolean isPossible(int[][] maze, int dir, int Row, int Col, boolean[][] visList){
        switch(dir) {
            //Right
            case 0:
                if (Col + 1 < maze[0].length) {
                    if (maze[Row][Col] != 0 && maze[Row][Col] != 2) {
                        if(!visList[Row][Col + 1]){
                            return true;
                        }
                    }
                }
                break;
            //Down
            case 1:
                if (Row + 1 < maze.length){
                    if (maze[Row][Col] != 0 && maze[Row][Col] != 1) {
                        if(!visList[Row + 1][Col]){
                            return true;
                        }
                    }
                }
                break;
            //Left
            case 2:
                // check if the move is possible
                if(Col - 1 >= 0){
                    if (maze[Row][Col - 1] == 1 || maze[Row][Col - 1] == 3){
                        if(!visList[Row][Col - 1]){
                            return true;
                        }
                    }
                }
                break;
            //Up
            case 3:
                //checking if the move is possible
                if(Row - 1 >= 0){
                    if(maze[Row - 1][Col] == 2 || maze[Row - 1][Col] == 3){
                        if(!visList[Row - 1][Col]){
                            return true;
                        }
                    }
                }
                break;
        }
        // returns false by default
        return false;
    }
}
