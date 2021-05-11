public class ManhattanHeuristic implements IHeuristicFunction{

    @Override
    public Double evaluateHeuristicDistance(Node currNode, Node endNode) {
        return Double.valueOf(Math.abs(currNode.getX()-endNode.getX()) + Math.abs(currNode.getY()-endNode.getY()));
    }
    
}
