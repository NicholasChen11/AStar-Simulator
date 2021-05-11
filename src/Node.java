public class Node implements Comparable<Node>{
    private Coordinate coordinate;
    private Boolean visited;
    private Boolean obstacle;
    private Double travelledDistance;
    private Double heuristicDistance;
    private Double estimatedDistance;
    private Node prevNode;

    // CONSTRUCTOR
    public Node(Coordinate c){
        this.coordinate = c;
        this.visited = false;
        this.obstacle = false;
    }

    // GETTER
    public Coordinate getCoordinate(){
        return this.coordinate;
    }
    public int getX(){
        return this.coordinate.getX();
    }
    public int getY(){
        return this.coordinate.getY();
    }
    public Boolean isVisited(){
        return this.visited;
    }
    public Boolean isObstacle(){
        return this.obstacle;
    }
    public Boolean isChecked(){
        return this.estimatedDistance != null;
    }
    public Double getTravelledDistance() {
        return travelledDistance;
    }
    public Double getHeuristicDistance(){
        return heuristicDistance;
    }
    public Double getEstimatedDistance() {
        return estimatedDistance;
    }
    public Node getPrevNode(){
        return this.prevNode;
    }

    // SETTER
    public void setCoordinate(Coordinate c){
        this.coordinate = c;
    }
    public void setVisited(Boolean v){
        this.visited = v;
    }
    public void setObstacle(Boolean v){
        this.obstacle = v;
    }
    public void setTravelledDistance(Double travelledDistance) {
        this.travelledDistance = travelledDistance;
    }
    public void setHeuristicDistance(Double heuristicDistance) {
        this.heuristicDistance = heuristicDistance;
    }
    public void setEstimatedDistance(Double estimatedDistance, Node prevNode) {
        if (this.estimatedDistance == null || this.estimatedDistance > estimatedDistance){
            this.estimatedDistance = estimatedDistance;
            this.prevNode = prevNode;
        }
    }
    public void setPrevNode(Node n){
        this.prevNode = n;
    }

    // FUNCTION
    public Double getEuclideanDistance(Node n){
        return this.coordinate.getEuclideanDistance(n.getCoordinate());
    }

    @Override
    public int compareTo(Node n) {
        if (n.getEstimatedDistance() > this.getEstimatedDistance()){
            return -1;
        } else if (n.getEstimatedDistance() < this.getEstimatedDistance()){
            return 1;
        } else {
            return 0;
        }
    }
    @Override
    public String toString(){
        return this.coordinate.toString();
    }
}
