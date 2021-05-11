public class Coordinate {
    private int x;
    private int y;

    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }

    // GETTER
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    // SETTER
    public void set(int x, int y){
        this.x = x;
        this.y = y;
    }
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }

    // FUNCTION
    public Double getEuclideanDistance(Coordinate n){
        return Math.sqrt(Math.pow(this.x - n.getX(), 2) + Math.pow(this.y - n.getY(), 2));
    }
    @Override
    public String toString(){
        return "(" + this.x + ", " + this.y + ")";
    }
}
