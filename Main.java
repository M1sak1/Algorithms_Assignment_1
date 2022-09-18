import java.util.Random;
import java.util.Scanner;
import java.util.random.*;

public class Main {
    //lmao I have totally forgotten how to use java
    public static void main(String[] args) {
        Scanner imp = new Scanner(System.in);
        System.out.println("input maze size (Please only input a single int)");
        String mazeSizeRaw = imp.nextLine();
        int mazeSize = Integer.parseInt(mazeSizeRaw);
        Cell[][] maze = makeMaze(mazeSize);
        maze = GenPath(maze);
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
        Cell[][] popMaze = blankMaze;
        boolean Finished = false;
        Random rand = new Random();
        int rnum = 0;
        while (!Finished){
            rnum = rand.nextInt(6);
            if(rnum == 5){
                Finished = true;
            }
            System.out.println(rnum);
        }
        return popMaze;
    }
}
/*Assessment Specifications MAZE Generation
I think the word node and cell are interchanged halfway through but mean the same thing
    Size(row,column) is a variable that is inputted on maze generation
    No Loops or inaccessible areas
    Represent using a 2d array of cells that stores all four walls and if those walls are open or close of each cell
    We always know the start and finish cell
    Maze has to have a closed area just outer connected walls that limit movement outside the maze

    To create the maze we "must" just the random walk technique,
    To generate an n×m maze, we create a grid graph of size n×m. The nodes in the grid represent the cells in the
    maze and can be indexed by 1, …, n×m in a row-major wise. Figure 2 shows a 5×5 grid with the cells indexed by
    1, …, 25. Nevertheless, for the random walk technique, we mark a random node (node 1 in Figure 2) as the
    starting node, and then walk around randomly on the graph. When selecting which node to visit from the current
    node, we randomly select an unvisited node from the neighbouring nodes that could be accessed. Note that not
    visiting any previously visited node will ensure no cyclic path. Nevertheless, for each move that we make from
    one node to a neighbouring node, we have an edge, and we consider the corresponding wall between the respective
    two cells is missing. As part of the random walk, if no movement is made between two neighbouring cells, then
    we assume the wall between the respective cells exists.
    see figure 2-3 for a visual representation
    Does seem like you can go back through the maze and break off of a previously used node in another direction that still satisfies the limitations

    https://www.geeksforgeeks.org/random-walk-implementation-python/

    When running this program through the command line it should take the amount of rows and columns as the input and output a file name
        Should give error messages for invalid inputs
        Once completed and created the maze should save a file of this format
        n,m:start_node:finish_node:cell_openness_list
        n - m are the row and column amounts *i think
        Start and finish node are not equal to and are between 1 and nxm
        cell_openness_list values are as per the following codes and are listed in the row-major order
            0: Both closed
            1: Right only open
            2: Down only open
            3: both open
        5,5:1:3:1223030112231122230210110 (a number per node)
 */
