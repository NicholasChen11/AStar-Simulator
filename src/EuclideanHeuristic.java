public class EuclideanHeuristic implements IHeuristicFunction{

    @Override
    public Double evaluateHeuristicDistance(Node currNode, Node endNode) {
        return currNode.getEuclideanDistance(endNode);
    }
    
}
