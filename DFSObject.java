public class DFSObject {
    String Maze = "";
    int location = -1;
    int preLocation = -1;
    int steps = 0;
    String Path = "";
    int endPosition = -1;
    int numRows = 0;
    int numColumns = 0;
    int stepsForPath = 0;

    boolean PathSolved = false;


    public String getMaze() {
        return Maze;
    }

    public void setMaze(String maze) {
        Maze = maze;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public int getPreLocation() {
        return preLocation;
    }

    public void setPreLocation(int preLocation) {
        this.preLocation = preLocation;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }

    public int getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(int endPosition) {
        this.endPosition = endPosition;
    }

    public int getNumRows() {
        return numRows;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    public int getNumColumns() {
        return numColumns;
    }

    public void setNumColumns(int numColumns) {
        this.numColumns = numColumns;
    }

    public boolean isPathSolved() {
        return PathSolved;
    }

    public void setPathSolved(boolean pathSolved) {
        PathSolved = pathSolved;
    }

    public int getStepsForPath() {
        return stepsForPath;
    }

    public void setStepsForPath(int stepsForPath) {
        this.stepsForPath = stepsForPath;
    }
}
