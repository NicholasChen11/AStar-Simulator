import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class AStarPathAlgoritm {
    private static IHeuristicFunction heuristicFunction;
    private static List<Node> candidateNodes;
    private static List<Node> routeNodes;
    private static Map map;
    private static Node startNode;
    private static Node endNode;

    public static Double useAStar(){
        setHeuristicToMap();
        candidateNodes = new ArrayList<>();
        Node currNode = startNode;
        currNode.setVisited(true);
        currNode.setTravelledDistance(0D);
        currNode.setEstimatedDistance(currNode.getHeuristicDistance(), null);
        candidateNodes.add(currNode);
        
        while (!candidateNodes.isEmpty()) {
            candidateNodes.remove(0);
            if (currNode == endNode){
                routeNodes = getRoute();
                map.printEstimated(startNode, endNode, routeNodes);
                return currNode.getTravelledDistance();
            }
            List<Node> temp = map.getAdjacentNodes(currNode);
            for (Node node : temp) {
                node.setEstimatedDistance(node.getHeuristicDistance() + currNode.getEuclideanDistance(node) + currNode.getTravelledDistance(), currNode);
                if (!candidateNodes.contains(node)){
                    candidateNodes.add(node);
                }
            }
            Collections.sort(candidateNodes);
            candidateNodes.get(0).setTravelledDistance(candidateNodes.get(0).getPrevNode().getEuclideanDistance(candidateNodes.get(0)) + candidateNodes.get(0).getPrevNode().getTravelledDistance());
            currNode = candidateNodes.get(0);
            currNode.setVisited(true);
        }
        return null;
    }

    // SETTER
    public static void setStartNode(Coordinate c){
        startNode = map.getNode(c);
    }
    public static void setEndNode(Coordinate c){
        endNode = map.getNode(c);
    }
    public static void setHeuristicFunction(IHeuristicFunction func){
        heuristicFunction = func;
    }

    // FUNCTION
    public static void setHeuristicToMap(){
        for (int i = 0; i < map.getSize(); i++){
            for (int j = 0; j < map.getSize(); j++){
                map.getNode(new Coordinate(i, j)).setHeuristicDistance(getHeuristicValue(map.getNode(new Coordinate(i, j)), endNode));
            }
        }
        System.out.println(MyColor.ANSI_YELLOW + "Heuristic Map:" + MyColor.ANSI_RESET);
        map.printHeuristic(startNode, endNode);
        System.out.println("");
    }
    public static Double getHeuristicValue(Node currNode, Node endNode){
        return heuristicFunction.evaluateHeuristicDistance(currNode, endNode);
    }
    public static Double getTotalValue(Node currNode, Node nextNode, Node endNode, Double travelledDistance){
        return map.getNode(nextNode.getCoordinate()).getHeuristicDistance() + currNode.getEuclideanDistance(nextNode) + travelledDistance;
    }
    public static void readInput(){
        Scanner scanner = new Scanner(System.in);
        map = new Map(10);

        // Start Node Input
        System.out.println("==============================");
        System.out.println("   ASTAR ALGORITM SIMULATOR   ");
        System.out.println("==============================");
        System.out.println();
        map.printMap();
        map.printMapLegend();
        System.out.println();
        System.out.print("Enter start node X coordinate: ");
        int startX = scanner.nextInt();
        System.out.print("Enter start node Y coordinate: ");
        int startY = scanner.nextInt();
        setStartNode(new Coordinate(startX, startY));

        // End Node Input
        System.out.print("Enter end node X coordinate: ");
        int endX = scanner.nextInt();
        System.out.print("Enter end node Y coordinate: ");
        int endY = scanner.nextInt();
        setEndNode(new Coordinate(endX, endY));
        System.out.println("");

        // Heuristic Input
        System.out.println(MyColor.ANSI_YELLOW + "List of ALgoritm:" + MyColor.ANSI_RESET);
        System.out.println("1. UCS");
        System.out.println("2. Manhattan");
        System.out.println("3. Euclidian");
        System.out.println("4. Chebysev");
        int heuristicNumber = 0;
        while (heuristicNumber == 0){
            System.out.print("Enter heuristic number to use: ");
            heuristicNumber = scanner.nextInt();
            if (heuristicNumber == 1){
                setHeuristicFunction(new UCSHeuristic());
            } else if (heuristicNumber == 2){
                setHeuristicFunction(new ManhattanHeuristic());
            } else if (heuristicNumber == 3){
                setHeuristicFunction(new EuclideanHeuristic());
            } else if (heuristicNumber == 4){
                setHeuristicFunction(new ChebysevHeuristic());
            } else {
                heuristicNumber = 0;
                System.out.println("Wrong Number. Try again!");
            }
        }
        System.out.println("");
        scanner.close();
    }
    public static void printSpec(){
        System.out.println("Total node entering Simpul Hidup = " + map.getTotalCheckedNode());
        System.out.println("Total visited node = " + map.getTotalVisitedNode());
        System.out.println("Total Search Cost = " + (map.getTotalCheckedNode()+map.getTotalVisitedNode()));
        printLegend();
    }
    public static void printLegend(){
        System.out.println(MyColor.ANSI_YELLOW + "LEGEND:" + MyColor.ANSI_RESET);
        System.out.println(MyColor.ANSI_YELLOW + "-> " + MyColor.ANSI_RESET + "Entered Simpul Hidup (White)");
        System.out.println(MyColor.ANSI_YELLOW + "-> " + MyColor.ANSI_RESET + MyColor.ANSI_BLUE + "Visited (Blue)" + MyColor.ANSI_RESET);
        System.out.println(MyColor.ANSI_YELLOW + "-> " + MyColor.ANSI_RESET + MyColor.ANSI_CYAN + "Route Result (Cyan)" + MyColor.ANSI_RESET);
        System.out.println(MyColor.ANSI_YELLOW + "-> " + MyColor.ANSI_RESET + MyColor.ANSI_GREEN + "Start Node (Green)" + MyColor.ANSI_RESET);
        System.out.println(MyColor.ANSI_YELLOW + "-> " + MyColor.ANSI_RESET + MyColor.ANSI_RED + "End Node (Red)" + MyColor.ANSI_RESET);
        System.out.println(MyColor.ANSI_YELLOW + "-> " + MyColor.ANSI_RESET + MyColor.ANSI_PURPLE + "Obstacle (Purple)" + MyColor.ANSI_RESET);
        System.out.println(MyColor.ANSI_YELLOW + "-> " + MyColor.ANSI_RESET + "Never Checked (Black)");
    }
    public static List<Node> getRoute(){
        List<Node> temp = new ArrayList<>();
        Node currNode = endNode;
        while (currNode != startNode){
            if (currNode != endNode){
                temp.add(currNode);
            }
            currNode = currNode.getPrevNode();
        }
        return temp;
    }
}
