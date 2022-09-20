public class Cell {
    // by default the maze will be completely oepn.
    int dir = -1;
    boolean left = false;
    boolean right = false;
    boolean up = false;
    boolean down = false;
    boolean isStart = false;
    boolean isFinish = false;
    boolean Isloop = false;

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    boolean visited = false;

    public boolean isLeft() {
        return left;
    }
    public void setLeft(boolean left) {
        this.left = left;
    }
    public boolean isRight() {
        return right;
    }
    public void setRight(boolean right) {
        this.right = right;
    }
    public boolean isUp() {
        return up;
    }
    public void setUp(boolean up) {
        this.up = up;
    }
    public boolean isDown() {
        return down;
    }
    public void setDown(boolean down) {
        this.down = down;
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
    public void genDir(){
        if(right){
            if(down){
                dir = 3;
            }
            else{
                dir = 1;
            }
        }
        else{
            if(down){
                dir = 2;
            }
            else{
                dir = 0;
            }
        }
    }
}
