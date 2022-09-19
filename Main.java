import java.util.Scanner;
import java.util.Random;
public class Main {
    //lmao I have totally forgotten how to use java
    public static void main(String[] args) {
        Scanner imp = new Scanner(System.in);
        System.out.println("input maze size row (Please only input a single int)");
        String mazeSizeRaw = imp.nextLine();
        int mazeSize = Integer.parseInt(mazeSizeRaw);
        Scanner imp2 = new Scanner(System.in);
        System.out.println("input maze size column (Please only input a single int)");
        int mazeSize2 = Integer.parseInt(imp2.nextLine());


        Cell[][] maze = makeMaze(mazeSize,mazeSize2); //I think the row and column can be different
        maze = MazePath(maze);
        // System.out.println(maze);
        PrintMaze(maze);
    }
    //This creates a completely open maze. every value is set to 3.
    public static Cell[][] makeMaze(int size1, int size2){
        Cell[][] maze = new Cell[size1][size2];
        int i = 0;
        int j = 0;
        for(i = 0; i < size1; i++){
            for(j = 0; j < size2; j++){
                maze[i][j] = new Cell();
            }
        }
        return maze;
    }

    //print the list to a file. but as yet does nothing
    // file format -> n,m:start:finish:maze
    public static void PrintMaze(Cell[][] maze){
        String totalpath = "";
        boolean found = false;
        int i, j;
        totalpath = findIndx(maze);
        System.out.println(totalpath);
        //print to a file
    }

    //used to find the start and the finish node.
    public static String findIndx(Cell[][] maze){
        String val = "";
        String start = "";
        String finish = "";
        int i, j;
        for(i = 0; i < maze.length; i++){
            for(j = 0; j < maze.length; j++){
                    if(maze[i][j].getStart()){
                        start = i + "," + j;
                    }
                    if(maze[i][j].getFinish()) {
                        finish = i + "," + j;
                    }
                    val += maze[i][j].getDir() + ",";
                }
            }
        return maze.length + "," + maze[0].length + ":" + start + ":" + finish + ":" + val;
    }

    public static Cell[][] MazePath(Cell[][] maze){
        Random rand = new Random();
        //System.out.println(maze.length-1);
        //System.out.print(maze[0].length-1);
        // generating the start. this ensures the start will be on an edge.
        // this also ensures the start and end are diff.
        int coordinate2 = 0;
        int coordinate1 = rand.nextInt(maze.length);
        if(coordinate1 == 0){
            coordinate2 = rand.nextInt(maze[0].length);
        }
        maze[coordinate1][coordinate2].setFinish(true);
        Cell fin = maze[coordinate1][coordinate2];

        boolean diffStart = false;
        while(!diffStart) {
            coordinate2 = 0;
            coordinate1 = rand.nextInt(maze.length);
            if (coordinate1 == 0) {
                coordinate2 = rand.nextInt(maze[0].length);
            }
            if(maze[coordinate1][coordinate2] != fin){
                maze[coordinate1][coordinate2].setStart(true);
                diffStart = true;
            }
        }
        // == What you see above you is so dumb but it works == //

        // Generating the paths
        maze = MazeRecursion(maze, coordinate1, coordinate2);
        System.out.println("WE GOT OUT");
        return maze;
    }

    public static Cell[][] MazeRecursion(Cell[][] maze, int coordinate1, int coordinate2){
        Random rand = new Random();
        int newcoordinate1 = coordinate1; //affects the row
        int newcoordinate2 = coordinate2; //affects the column
        int randomwalk = 0; //just so it wont yell at me
        if(maze[coordinate1][coordinate2].getDir() == -1){
            int functional = 0; //makes sure the created path is correct and dousn't create a loop or etc
            while(functional == 0) {
                randomwalk = rand.nextInt(4);
                switch (randomwalk){
                    case 0:
                        newcoordinate1 = coordinate1 - 1;
                        newcoordinate2 = coordinate2 - 1;
                        if(newcoordinate1 >= 0 && newcoordinate2 >= 0){ //checks if the cell is in the top left
                            maze[coordinate1][coordinate2].setDir(randomwalk);
                            functional = 1;
                            break;
                        }
                        break;
                    case 1:
                        newcoordinate1 = coordinate2 + 1;
                        if(newcoordinate2 < maze.length){
                            maze[coordinate1][coordinate2].setDir(randomwalk);
                            functional = 1;
                            break;
                        }
                        break;
                    case 2:
                        newcoordinate1 = coordinate1 + 1;
                        if(newcoordinate1 < maze[0].length){
                            maze[coordinate1][coordinate2].setDir(randomwalk);
                            functional = 1;
                            break;
                        }
                        break;
                    case 3:
                        newcoordinate1 = coordinate1 + 1;
                        newcoordinate2 = coordinate2 + 1;
                        if(newcoordinate1 < maze.length && newcoordinate2 < maze[0].length){ //checks if the cell is in the top left
                            maze[coordinate1][coordinate2].setDir(randomwalk);
                            functional = 1;
                            break;
                        }
                        break;
                }
            }
            System.out.print(coordinate1 + " " + coordinate2);
            System.out.println(" " + randomwalk);
            if(randomwalk == 0 || randomwalk == 3){
                maze = MazeRecursion(maze,coordinate1,newcoordinate2);
                maze = MazeRecursion(maze,newcoordinate1,coordinate2);
            }
            else{
                maze = MazeRecursion(maze,newcoordinate1,newcoordinate2);
            }

            if(newcoordinate1 - 1 > 0 && newcoordinate1 + 1 < maze.length){
                if(maze[newcoordinate1 - 1][newcoordinate2].getDir() == -1){
                    maze = MazeRecursion(maze,newcoordinate1 -1, newcoordinate2);
                }
                if(maze[newcoordinate1 + 1][newcoordinate2].getDir() == -1) {
                    maze = MazeRecursion(maze, newcoordinate1 + 1, newcoordinate2);
                }
            }
            if(newcoordinate2 - 1 > 0 && newcoordinate2 + 1 < maze.length){
                if(maze[newcoordinate1][newcoordinate2 - 1].getDir() == -1){
                    maze = MazeRecursion(maze,newcoordinate1, newcoordinate2 - 1);
                }
                if(maze[newcoordinate1][newcoordinate2 + 1].getDir() == -1) {
                    maze = MazeRecursion(maze, newcoordinate1, newcoordinate2 + 1);
                }
            }
        }
        return maze;
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
