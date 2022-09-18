public class Cell {
    // by default the maze will be completely oepn.
    int dir = 3;
    boolean isStart = false;
    boolean isFinish = false;
    public void setDir(int newDir){
        dir = newDir;
    }
    public void setStart(boolean newStart){
        isStart = newStart;
    }
    public void setFinish(boolean newFinish){
        isFinish = newFinish;
    }
    public boolean getStart(){
        return isStart;
    }
    public boolean getFinish(){
        return isFinish;
    }
    public int getDir(){
        return dir;
    }
}
