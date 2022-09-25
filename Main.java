import java.io.*;
import java.nio.charset.StandardCharsets;
//import java.util.Scanner;
import java.util.Random;
public class Main {
    public static void main(String[] args) throws IOException {
        int mazeSize = Integer.parseInt(args[0]);
        int mazeSize2 = Integer.parseInt(args[1]);
        String OutName = args[2];
        //makes a 2d array of the maze sizes
        Cell[][] maze = makeMaze(mazeSize,mazeSize2);
        //assigns a random start and finish position then creates the maze
        maze = MazePath(maze);
        //writes the maze information to a txt file named what was inputted
        PrintMaze(maze, OutName);
        //prints the maze in visual form to the console
        PrinttextMaze(maze);
    }
    //This creates a completely blank maze
    public static Cell[][] makeMaze(int size1, int size2){
        Cell[][] maze = new Cell[size1][size2]; //cell is a created object type that holds the information a maze cell would need
        int i = 0;
        int j = 0;
        for(i = 0; i < size1; i++){
            for(j = 0; j < size2; j++){
                maze[i][j] = new Cell();
            }
        }
        return maze;
    }

    //print the list to a file.
    // file format -> n,m:start:finish:maze
    public static void PrintMaze(Cell[][] maze, String outputName) throws IOException {

        String totalpath = Print(maze);
        PrintWriter writer = new PrintWriter(outputName, StandardCharsets.UTF_8);
        writer.println(totalpath);
        writer.close();
        //print to a file
    }

    //used to find the start and the finish node.
    public static String Print(Cell[][] maze){
        String val = ""; //stores all the openness of the maze
        String start = "";
        String finish = "";
        int tempval; //stores the converted integer value of the 2d array maze[][] position
        int i, j;
        //runs through the maze and collects the start, finish and openness of all cells
        for(i = 0; i < maze.length; i++){
            for(j = 0; j < maze[0].length; j++){
                    //a single cell has this attribute set to true
                    if(maze[i][j].getStart()) {
                        //since dfs works from 1 not zero
                        //rows and columns are one below what we need so for it were actually grabbing the first position of our row and plusing that by what colum its on so the column needs to be plus 1
                        tempval = (i * maze[0].length + j ) + 1;
                        start = "" + tempval ;  //saving to start
                    }
                //a single cell has this attribute set to true
                    if(maze[i][j].getFinish()) {
                        System.out.println("Finishing cell" + i + " "+j);
                        //rows and columns are one below what we need so for it were actually grabbing the first position of our row and plusing that by what colum its on so the column needs to be plus 1
                        tempval = (i * maze[0].length + j ) + 1;
                        finish = ""+ tempval; //saving to finish
                    }
                    val += maze[i][j].getDir(); //sores the openness of the cell and adds it to the string
                }
            }
        //returns the required values in the proper format
        return maze.length + "," + maze[0].length + ":" + start + ":" + finish + ":" + val;
    }

    public static Cell[][] MazePath(Cell[][] maze){
        Random rand = new Random();
        // generating the start. this ensures the start will be on an edge.
        // this also ensures the start and end are diff.
        int coordinate2 = 0;
        int coordinate1 = rand.nextInt(maze.length);
        if(coordinate1 == 0){ //if both coordinates are 0 then cord 2 changes
            coordinate2 = rand.nextInt(maze[0].length);
        }
        maze[coordinate1][coordinate2].setFinish(true);

        boolean diffStart = false;
        //ensures start and finish values will not be the same
        while(!diffStart) {
            coordinate2 = rand.nextInt(maze.length);
            coordinate1 = 0;
            //random position
            if(coordinate2 == 0){
                coordinate1 = rand.nextInt(maze[0].length);
            }
            //if the coordinates chosen for the start position are not equal to the coordinates chosen for the end position it is set as the start and the while loop is broken
            if(!maze[coordinate1][coordinate2].isFinish){
                maze[coordinate1][coordinate2].setStart(true);
                diffStart = true;
            }
        }
        // == What you see above you is so dumb but it works == //

        // Generating the openness
        maze = mazeRec(maze, coordinate1, coordinate2);
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
    public static Cell[][] mazeRec(Cell[][] maze, int row, int column) {
        Random rand = new Random();
        int dir; //what direction to travel
        boolean moved = false; //to ensure the program is continuing to look for new paths when necessary
        int stuckInitially = 0; //to determine if the maze can generate a path from the current location
        while(!stuck(maze, row, column, 0) || !stuck(maze, row, column, 1)) {
            stuckInitially = 1; // not initially stuck and won't qualify for being the finish
            dir = rand.nextInt(4);
            switch (dir) {
                //up
                case 0:
                    //checking if the move is possible(out of bounds of the maze or the nodes been visited)
                    if ((row - 1) >= 0 && !maze[row - 1][column].isVisited()) {
                        //Checking if it is an allowed move.
                        maze[row][column].setVisited(true); //sets current node as visited
                        maze[row][column].setUp(true); //sets this nodes direction to up
                        maze[row][column].genDir(); //takes all the true direction values (left,right,up,down) then generates the appropriate openness
                        row--; //goes to the node below and sets the appropriate directional values and generates an openness (openness will and does change after every new interaction with that node)
                        maze[row][column].setDown(true);
                        maze[row][column].genDir();
                        maze = mazeRec(maze, row, column);// recurse with the new values
                        row++; //if the program hits a dead end this current instance of this function will hold its actual location again
                    }
                    break;
                //down
                case 1:
                    //checking if the move is possible(out of bounds of the maze or the nodes been visited)
                    if ((row + 1) < maze.length && !maze[row + 1][column].isVisited()) {
                        //does same as case 0 but we're going up this time
                        maze[row][column].setVisited(true);
                        maze[row][column].setDown(true);
                        maze[row][column].genDir();
                        row++;
                        maze[row][column].setUp(true);
                        maze[row][column].genDir();
                        maze = mazeRec(maze, row, column);
                        row--;
                    }
                    break;
                //left
                case 2:
                    //checking if the move is possible(out of bounds of the maze or the nodes been visited)
                    if ((column - 1) >= 0 && !maze[row][column - 1].isVisited()) {
                        //System.out.println("moved");
                        //does same as case 0 but we're going left this time
                        maze[row][column].setVisited(true);
                        maze[row][column].setLeft(true);
                        maze[row][column].genDir();
                        column--;
                        maze[row][column].setRight(true);
                        maze[row][column].genDir();
                        maze = mazeRec(maze, row, column);
                        column++;
                    }
                    break;
                //right
                case 3:
                    //checking if the move is possible(out of bounds of the maze or the nodes been visited)
                    if ((column + 1) < maze[0].length && !maze[row][column + 1].isVisited()) {
                        //does same as case 0 but we're going right this time
                        maze[row][column].setVisited(true);
                        maze[row][column].setRight(true);
                        maze[row][column].genDir();
                        column++;
                        maze[row][column].setLeft(true);
                        maze[row][column].genDir();
                        maze = mazeRec(maze, row, column);
                        column--;
                    }
                    break;
            }
        }

        maze[row][column].setVisited(true); // makes a node that is trapped be found as visited then never tried to be entered again
        // reoccur to the new node
        // way to break the recursion
        // this was my idea, it would only reoccur if would make a dif.
        return maze;
    }

    // if it is not stuck it will return false.
    // if it is stuck it will return true.
    //stuck is if no new paths can be made from the node
    //ver is to test either left,right or up,down
    public static boolean stuck(Cell[][] maze, int row, int col, int ver){
        if(ver == 0){
            //a cell is assigned a direction of -1 when first initialised, so we can determine if that cell already part of a path and would create a loop if connected to
            if((row + 1) < maze.length && maze[row + 1][col].getDir() == -1){
                return false;
            }
            return !((row - 1) >= 0 && maze[row - 1][col].getDir() == -1);
        }
        else{
            if ((col + 1) < maze[0].length && maze[row][col + 1].getDir() == -1){
                return false;
            }
            return !((col - 1) >= 0 && maze[row][col - 1].getDir() == -1);
        }
    }

    public static void PrinttextMaze(Cell [][] Maze){
        //constructing the walls
        //this will print two types of lines, onlines and between lines.
        //Onlines is where the S,F and * when solving will be placed as well as the walls for left and right travel
        //the between lines are where the up and down will be walled
        String onLine = "-"; //top and bottom need one more character than 3 per node to line up perfectly
        String betweenLine = "|"; //a left wall will always be in place at the start of a new line
        boolean start = false;
        boolean finish = false;
        String[] returnHolder; //used to return 2 values from a function
        //will create the top wall
        for(int i = 0 ; i < Maze[1].length; i++){
            onLine += "---";
        }
        System.out.println(onLine);
        //goes line by line and collects the required characters into the online and between line then when that line if finished they are both printed, repeated till the maze is fully generated
        for(int i = 0; i < Maze.length; i++){
            onLine = "|";//left wall
            betweenLine = "|";
            for(int j = 0; j < Maze[1].length; j++){
                //finding the start and end position for S,F placement
                if(Maze[i][j].getStart()) {
                    start = true;
                }
                if(Maze[i][j].getFinish()) {
                    finish = true;
                }
                //get.dir returns the openess of the cell, online and betweenline will give the string they have constructed
                returnHolder = PrintWalls(Maze[i][j].getDir(),onLine,betweenLine,start,finish);
                //values are given to the lines
                onLine = returnHolder[0];
                betweenLine = returnHolder[1];
                //makes sure the program wont assume every cell after start or finish is also the start or finish
                start = false;
                finish = false;
            }
            //Prints the online walls
            System.out.println(onLine);
            //Prints the between lines if the last line is not the bottom of the maze
            if(i + 1 != Maze.length){
                System.out.println(betweenLine);
            }
        }
        //makes it line up with the top perfectly
        onLine = "-";
        for(int i = 0 ; i < Maze[1].length; i++){
            onLine += "---";
        }
        //prints the bottom wall
        System.out.println(onLine);
    }
//used to save redundant code and will create a line of text with the required characters based on the input values (openness,current online string, current betweenline string and if this cell is a start of finish
    public static String[] PrintWalls(int direction, String onLine, String betweenLine, boolean start, boolean finish){
        String[] Lines = new String[]{onLine,betweenLine}; //storing both values for return
        //[0] is online
        //[1] is betweenLines
        switch(direction){
            case 0:{
                //will create one wall below and to the right
                if(start){
                    Lines[0] += "S |";
                }
                else if(finish){
                    Lines[0] += "F |";
                }
                else{
                    Lines[0] += "  |";
                }
                Lines[1] += "--";
                break;
            }
            case 1:{
                //will create one wall below
                if(start){
                    Lines[0] += "S  ";
                }
                else if(finish){
                    Lines[0] += "F  ";
                }
                else{
                    Lines[0] += "   ";
                }
                Lines[1] += "--";
                break;
            }
            case 2:{
                //will create one wall to the right
                if(start){
                    Lines[0] += "S |";
                }
                else if(finish){
                    Lines[0] += "F |";
                }
                else{
                    Lines[0] += "  |";
                }
                Lines[1] += "  ";
                break;
            }
            default:{
                //case 3 has right and down open so it wont need to create walls
                //but needs to add the appropriate amount of whitespace
                if(start){
                    Lines[0] += "S  ";
                }
                else if(finish){
                    Lines[0] += "F  ";
                }
                else{
                    Lines[0] += "   ";
                }
                Lines[1] += "  ";
            }
        }
        //a between line always has a | between cell walls
        Lines[1] += "|";
        return Lines;
    }
}

