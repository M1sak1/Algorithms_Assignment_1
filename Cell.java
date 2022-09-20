public class Cell {
    // by default the maze will be completely oepn.
    int dir = -1;
    boolean isStart = false;
    boolean isFinish = false;
    boolean Isloop = false;
    public void setDir(int newDir){
        dir = newDir;
    }
    public void setStart(boolean newStart){
        isStart = newStart;
    }
    public void setISloop(boolean loop){
        Isloop = loop;
    }
    public void setFinish(boolean newFinish) { isFinish = newFinish;}
    public boolean getStart(){
        return isStart;
    }
    public boolean getFinish(){
        return isFinish;
    }
    public boolean getloop() {return Isloop; }
    public int getDir(){
        return dir;
    }
}
