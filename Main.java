import java.util.Scanner;

public class Main {
    //lmao I have totally forgotten how to use java
    public static void main(String[] args) {
        Scanner imp = new Scanner(System.in);
        System.out.println("input maze size (Please only input a single int)");
        String mazeSizeRaw = imp.nextLine();
        int mazeSize = Integer.parseInt(mazeSizeRaw);
        Cell[][] maze = makeMaze(mazeSize);
        // System.out.println(maze);
    }
    //This creates a completely open maze. every value is set to 3.
    public static Cell[][] makeMaze(int size){
        Cell[][] maze = new Cell[size][size];
        int i = 0;
        int j = 0;
        for(i = 0; i < size; i++){
            for(j = 0; j < size; j++){
                maze[i][j] = new Cell();
            }
        }
        return maze;
    }
    //does nothing as yet
    public static Cell[][] GenPath(Cell[][] blankMaze){
        return blankMaze;
    }
}

