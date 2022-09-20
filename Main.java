import java.util.Scanner;
import java.util.Random;
public class Main {
    //lmao I have totally forgotten how to use java
//    public static void main(String[] args) {
//        Scanner imp = new Scanner(System.in);
//        System.out.println("input maze size row (Please only input a single int)");
//        String mazeSizeRaw = imp.nextLine();
//        int mazeSize = Integer.parseInt(mazeSizeRaw);
//        Scanner imp2 = new Scanner(System.in);
//        System.out.println("input maze size column (Please only input a single int)");
//        int mazeSize2 = Integer.parseInt(imp2.nextLine());
//
//
//        Cell[][] maze = makeMaze(mazeSize,mazeSize2); //I think the row and column can be different
//        maze = MazePath(maze);
//        // System.out.println(maze);
//        PrintMaze(maze);
//    }
    public static void main(String[] args) {
        Cell[][] maze = makeMaze(3,3); //I think the row and column can be different
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

        maze = mazeRec(maze, coordinate1, coordinate2, coordinate1, coordinate2);
        // maze = mazeRec(maze, coordinate1, coordinate2);
        System.out.println("WE GOT OUT");
        return maze;
    }

//    public static Cell[][] MazeRecursion(Cell[][] maze, int coordinate1, int coordinate2){
//        Random rand = new Random();
//        System.out.println("CurrentLocation" + coordinate1 + "," + coordinate2);
//        int newcoordinate1 = coordinate1; //affects the row
//        int newcoordinate2 = coordinate2; //affects the column
//        int case0, case1,case2,case3;
//        case0 = case1 = case2 = case3 = 0;
//        int randomwalk = -1; //just so it wont yell at me as random walk will only be used inside the if statement
//            int functional = 0; //makes sure the created path is correct and dousn't create a loop or etc
//            while(functional == 0) {
//                randomwalk = rand.nextInt(4);
//                if(case0 == 0 || case1 == 0 || case2 == 0 || case3 == 0) {
//                    System.out.println(case0+" "+case1+" "+ case2+" "+case3);
//                    switch (randomwalk) { //enhanced switch it dousn't need a break statement and wont go between cases
//                        case 0 -> { // both closed (right and down)
//                            //both need to change as it creates a path in both directions
//                            newcoordinate1 = coordinate1 - 1;
//                            newcoordinate2 = coordinate2 - 1;
//                            System.out.println("nc1: " + newcoordinate1 + " nc2: " + coordinate2 + " rw: " + randomwalk + " up ");
//                            System.out.print("nc1: " + coordinate1 + " nc2: " + newcoordinate2 + " rw: " + randomwalk + " left ");
//                            if (newcoordinate2 >= 0 && maze[coordinate1][newcoordinate2].getDir() == -1 && !maze[coordinate1][newcoordinate2].getloop()) { // ok this is fucked but ima explain, the new cell variable isloop is their to stop loops when both sides of the maze get recursed and end up connected
//                                //It takes the secondary cell information first to determine if we need to do loop checking shenanigans
//                                if (newcoordinate1 >= 0 && maze[newcoordinate1][coordinate2].getDir() == -1 && !maze[newcoordinate1][coordinate2].getloop()) { //Then it checks the first value to determine if we even go that way
//                                    maze[coordinate1][newcoordinate2].setISloop(true); //secondary direction is set as loop (left)
//                                    System.out.println(" both success"); //both walls can be removed without error
//                                    maze[coordinate1][coordinate2].setDir(randomwalk); //set up current cell direction
//                                    maze = MazeRecursion(maze, newcoordinate1, coordinate2); //recurse recurse
//                                    functional = 1; //break out when done
//                                    maze[coordinate1][newcoordinate2].setISloop(false); //remove loop condition so the secondary one can fire
//                                    maze = MazeRecursion(maze, coordinate1, newcoordinate2); //secondary recurse goes
//                                } else { //if only you can move left
//                                    System.out.println(" left sucess");
//                                    maze[coordinate1][coordinate2].setDir(randomwalk);
//                                    maze = MazeRecursion(maze, coordinate1, newcoordinate2);
//                                    functional = 1;
//                                }
//                            } else if (newcoordinate1 >= 0 && maze[newcoordinate1][coordinate2].getDir() == -1 && !maze[newcoordinate1][coordinate2].getloop()) { //if only you can move up
//                                    System.out.println(" up sucess");
//                                    maze[coordinate1][coordinate2].setDir(randomwalk);
//                                    maze = MazeRecursion(maze, newcoordinate1, coordinate2);
//                                    functional = 1;
//                            } else { //cant move at all
//                                System.out.println(" Failed");
//                                newcoordinate1++; //resets values
//                                newcoordinate2++;
//                                case0++; //failed at this try
//                            }
//                        }
//                        case 1 -> { //Right only open
//                            newcoordinate2 = coordinate2 + 1;
//                            System.out.print("nc1: " + newcoordinate1 + " nc2: " + newcoordinate2 + " rw: " + randomwalk);
//                            if (newcoordinate2 < maze.length && maze[coordinate1][newcoordinate2].getDir() == -1 && !maze[coordinate1][newcoordinate2].getloop()) {
//                                System.out.println(" success");
//                                maze[coordinate1][coordinate2].setDir(randomwalk);
//                                functional = 1;
//                                maze = MazeRecursion(maze, coordinate1, newcoordinate2);
//                            } else {
//                                System.out.println(" Failed");
//                                newcoordinate2--;
//                                case1++; //failed at this try
//                            }
//                        }
//                        case 2 -> { //Down only open
//                            newcoordinate1 = coordinate1 + 1;
//                            System.out.print("nc1: " + newcoordinate1 + " nc2: " + newcoordinate2 + " rw: " + randomwalk);
//                            if (newcoordinate1 < maze[0].length && maze[newcoordinate1][coordinate2].getDir() == -1 && !maze[newcoordinate1][coordinate2].getloop()) {
//                                System.out.println(" success");
//                                maze[coordinate1][coordinate2].setDir(randomwalk);
//                                maze = MazeRecursion(maze, newcoordinate1, coordinate2);
//                                functional = 1;
//                            } else {
//                                System.out.println(" Failed");
//                                newcoordinate1--;
//                                case2++; //failed at this try
//                            }
//                        }
//                        case 3 -> { // Both down and right open
//                            newcoordinate1 = coordinate1 + 1;
//                            newcoordinate2 = coordinate2 + 1;
//                            System.out.println("nc1: " + newcoordinate1 + " nc2: " + coordinate2 + " rw: " + randomwalk + " down ");
//                            System.out.print("nc1: " + coordinate1 + " nc2: " + newcoordinate2 + " rw: " + randomwalk + " right ");
//                            if (newcoordinate2 < maze[0].length && maze[coordinate1][newcoordinate2].getDir() == -1 && !maze[coordinate1][newcoordinate2].getloop()) { // ok this is fucked but ima explain, the new cell variable isloop is their to stop loops when both sides of the maze get recursed and end up connected
//                                //It takes the secondary cell information first to determine if we need to do loop checking shenanigans
//                                if (newcoordinate1 < maze.length && maze[newcoordinate1][coordinate2].getDir() == -1 && !maze[newcoordinate1][coordinate2].getloop()) { //Then it checks the first value to determine if we even go that way
//                                    // ensures the cell that would create a loop cannot do it
//                                    maze[coordinate1][newcoordinate2].setISloop(true); //secondary direction is set as loop (right)
//                                    System.out.println(" both success"); //both walls can be removed without error
//                                    maze[coordinate1][coordinate2].setDir(randomwalk); //set up current cell direction
//                                    maze = MazeRecursion(maze, newcoordinate1, coordinate2); //recurse recurse
//                                    functional = 1; //break out when done
//                                    maze[coordinate1][newcoordinate2].setISloop(false); //remove loop condition so the secondary one can fire
//                                    maze = MazeRecursion(maze, coordinate1, newcoordinate2); //secondary recurse goes
//                                } else { //if only you can move right
//                                    System.out.println(" right sucess");
//                                    maze[coordinate1][coordinate2].setDir(randomwalk);
//                                    maze = MazeRecursion(maze, coordinate1, newcoordinate2);
//                                    functional = 1;
//                                }
//                            } else if (newcoordinate1 < maze.length && maze[newcoordinate1][coordinate2].getDir() == -1 && !maze[newcoordinate1][coordinate2].getloop()) { //if only you can move down
//                                System.out.println(" down sucess");
//                                maze[coordinate1][coordinate2].setDir(randomwalk);
//                                maze = MazeRecursion(maze, newcoordinate1, coordinate2);
//                                functional = 1;
//
//                            } else { //cant move at all
//                                System.out.println(" Failed");
//                                newcoordinate1--; //resets values
//                                newcoordinate2--;
//                                case3++; //failed at this try
//                            }
//                        }
//                    }
//                }
//                else{
//                    functional = 1;
//                    System.out.println("NO WHERE TO GO "+ maze[coordinate1][coordinate2].getDir());
//                    maze[coordinate1][coordinate2].setDir(-2);
//                    break;
//                }
//            }
//            //used to ensure the maze has no unreachable positions
//            if(coordinate1 - 1 >= 0 && maze[coordinate1 - 1][coordinate2].getDir() == -1 & !maze[coordinate1 - 1][coordinate2].getloop() ){
//                maze = MazeRecursion(maze,coordinate1 - 1, coordinate2);
//            }
//            if(coordinate1 + 1 < maze.length && maze[coordinate1 + 1][coordinate2].getDir() == -1 & !maze[coordinate1 + 1][coordinate2].getloop() ){
//            maze = MazeRecursion(maze,coordinate1 + 1, coordinate2);
//            }
//            if(coordinate2 - 1 >= 0 && maze[coordinate1][coordinate2 - 1].getDir() == -1 & !maze[coordinate1][coordinate2 - 1].getloop() ){
//               maze = MazeRecursion(maze,coordinate1, coordinate2 + 1);
//            }
//            if(coordinate2 + 1 < maze[0].length && maze[coordinate1][coordinate2 + 1].getDir() == -1 & !maze[coordinate1][coordinate2 + 1].getloop() ){
//               maze = MazeRecursion(maze,coordinate1, coordinate2 + 1);
//            }
//
//            // System.out.println(" oc1: " + coordinate1 + " oc2: " + coordinate2);
//            // System.out.println(" rw: " + randomwalk);
//
//            /* no idea what this does
//            if(newcoordinate1 - 1 > 0 && newcoordinate1 + 1 < maze.length){
//                if(maze[newcoordinate1 - 1][newcoordinate2].getDir() == -1){
//                    maze = MazeRecursion(maze,newcoordinate1 -1, newcoordinate2);
//                }
//                if(maze[newcoordinate1 + 1][newcoordinate2].getDir() == -1) {
//                    maze = MazeRecursion(maze, newcoordinate1 + 1, newcoordinate2);
//                }
//            }
//            if(newcoordinate2 - 1 > 0 && newcoordinate2 + 1 < maze.length){
//                if(maze[newcoordinate1][newcoordinate2 - 1].getDir() == -1){
//                    maze = MazeRecursion(maze,newcoordinate1, newcoordinate2 - 1);
//                }
//                if(maze[newcoordinate1][newcoordinate2 + 1].getDir() == -1) {
//                    maze = MazeRecursion(maze, newcoordinate1, newcoordinate2 + 1);
//                }
//            }
//            */
//        return maze;
//    }

    //Inputs
    // row -> the current row pos; column -> the current column pos | prex -> the row coord of the previous node prey -> the column coord of the previous node.
    public static Cell[][] mazeRec(Cell[][] maze, int row, int column, int preRow, int preCol) {
        int olRow = row;
        int olCol = column;
        Random rand = new Random();
        int dir;
        boolean moved = false;
        dir = rand.nextInt(4);
        switch(dir){
            //up
            case 0:
                //checking if the move is possible
                if ((row - 1) >= 0 && !maze[row- 1][column].isVisited()){
                    //Checking if it is an allowed move.
                    maze[row][column].setVisited(true);
                    maze[row][column].setUp(true);
                    maze[row][column].genDir();
                    row--;
                    maze[row][column].setDown(true);
                    maze[row][column].genDir();
                    moved = true;
                }
                break;
            //down
            case 1:
                //checking if the move is possible
                if ((row + 1) < maze.length && !maze[row + 1][column].isVisited()){
                    //Checking if it is an allowed move.
                    maze[row][column].setVisited(true);
                    maze[row][column].setDown(true);
                    maze[row][column].genDir();
                    row++;
                    maze[row][column].setUp(true);
                    maze[row][column].genDir();
                    moved = true;
                }
                break;
            //left
            case 2:
                //checking if the move is possible
                if ((column - 1) >= 0 && !maze[row][column - 1].isVisited()){
                    //Checking if it is an allowed move.
                    maze[row][column].setVisited(true);
                    maze[row][column].setLeft(true);
                    maze[row][column].genDir();
                    column--;
                    maze[row][column].setRight(true);
                    maze[row][column].genDir();
                    moved = true;
                }
                break;
            //right
            case 3:
                //checking if the move is possible
                if ((column + 1) < maze[0].length && !maze[row][column+1].isVisited()){
                    maze[row][column].setVisited(true);
                    maze[row][column].setRight(true);
                    maze[row][column].genDir();
                    column++;
                    maze[row][column].setLeft(true);
                    maze[row][column].genDir();
                    moved = true;
                }
                break;
        }
        // reoccur to the new node
        // way to break the recursion
        // this was my idea, it would only reoccur if would make a dif.
        if(moved){
            maze = mazeRec(maze, row, column, olRow, olCol );
        }
        else{
            //Check if its trapped
            //if its trapped go backwards
            //its its not trapped try again.
            if(stuck(maze, olRow, olCol, 0) && stuck(maze, olRow, olCol, 1) ){
                return maze;
            }
            else{
                maze = mazeRec(maze, preRow, preCol, preRow, preCol);
            }
        }
        return maze;
    }

    public static boolean stuck(Cell[][] maze, int row, int col, int ver){
        if(ver == 0){
            if((row + 1) < maze[0].length && maze[row + 1][col].getDir() == -1){
                return false;
            }
            return !((row - 1) >= 0 && maze[row - 1][col].getDir() == -1);
        }
        else{
            if ((col + 1) < maze.length && maze[row][col + 1].getDir() == -1){
                return false;
            }
            return !((col - 1) >= 0 && maze[row][col - 1].getDir() == -1);
        }
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
