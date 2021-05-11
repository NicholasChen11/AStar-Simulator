public class ChebysevHeuristic implements IHeuristicFunction{

    @Override
    public Double evaluateHeuristicDistance(Node currNode, Node endNode) {
        int Xdiff = Math.abs(currNode.getX()-endNode.getX());
        int Ydiff = Math.abs(currNode.getY()-endNode.getY());
        if (Xdiff > Ydiff){
            return Double.valueOf(Xdiff);
        } else {
            return Double.valueOf(Ydiff);
        }
    }
    
}