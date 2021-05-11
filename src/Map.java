import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Map {
    private List<List<Node>> map;
    private int size;

    public Map(int size){
        this.size = size;
        this.map = new ArrayList<>();
        for (int i = 0; i < size; i++){
            List<Node> temp = new ArrayList<>();
            for (int j = 0; j < size; j++){
                temp.add(new Node(new Coordinate(i, j)));
            }
            this.map.add(temp);
        }
        putObstacleFromTxt();
    }

    // GETTER
    public int getSize(){
        return size;
    }
    public Node getNode(Coordinate c){
        return this.map.get(c.getX()).get(c.getY());
    }
    public int getTotalCheckedNode(){
        int count = 0;
        for (List<Node> list : map) {
            for (Node node : list) {
                if (node.isChecked()){
                    count += 1;
                }
            }
        }
        return count;
    }
    public int getTotalVisitedNode(){
        int count = 0;
        for (List<Node> list : map) {
            for (Node node : list) {
                if (node.isVisited()){
                    count += 1;
                }
            }
        }
        return count;
    }

    // FUNCTION
    public Boolean isCoordinateValid(Coordinate c){
        return (c.getX() >= 0) && (c.getY() >= 0) && (c.getX() < this.size) && (c.getY() < this.size);
    }
    public void putObstacleFromTxt(){
        int i = 0;
        try {
            File file = new File("map.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String row = scanner.nextLine();
                for (int j = 0; j < (this.size*2)-1; j = j+2){
                    if (row.charAt(j) == 'X'){
                        this.map.get(i).get(j/2).setObstacle(true);
                    }
                }
                i = i + 1;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public List<Node> getAdjacentNodes(Node n){
        List<Node> adjNode = new ArrayList<>();
        Coordinate temp;
        for (int i = -1; i <= 1; i++){
            for (int j = -1; j <= 1; j++){
                temp = new Coordinate(n.getX()+i, n.getY()+j);
                if (this.isCoordinateValid(temp) && (i != 0 || j != 0)){
                    if (!this.getNode(temp).isVisited() && !this.getNode(temp).isObstacle()){
                        adjNode.add(this.getNode(temp));
                    }
                }
            }
        }
        return adjNode;
    }
    public void printHeuristic(Node startNode, Node endNode){
        int i = 0;
        String format;
        System.out.println(MyColor.ANSI_YELLOW + "     0     1     2     3     4     5     6     7     8     9" + MyColor.ANSI_RESET);
        for (List<Node> list : map) {
            System.out.print(MyColor.ANSI_YELLOW + i + " " + MyColor.ANSI_RESET);
            i++;
            for (Node node : list) {
                Double temp = node.getHeuristicDistance();
                if (startNode == node){
                    format = new DecimalFormat("#0.00").format(temp);
                    format = MyColor.ANSI_GREEN + format + MyColor.ANSI_RESET;
                } else if (endNode == node){
                    format = new DecimalFormat("#0.00").format(temp);
                    format = MyColor.ANSI_RED + format + MyColor.ANSI_RESET;
                } else {
                    format = new DecimalFormat("#0.00").format(temp);
                }
                if (temp < 10){
                    System.out.print(" ");
                    System.out.print(format);
                } else {
                    System.out.print(format);
                }
                System.out.print(" ");
            }
            System.out.println("");
        }
    }
    public void printMap(){
        int i = 0;
        String format;
        System.out.println(MyColor.ANSI_YELLOW + "     0      1      2      3      4      5      6      7      8      9" + MyColor.ANSI_RESET);
        for (List<Node> list : map) {
            System.out.print(MyColor.ANSI_YELLOW + i + " " + MyColor.ANSI_RESET);
            i++;
            for (Node node : list) {
                format = node.toString();
                if (node.isObstacle()){
                    format = MyColor.ANSI_PURPLE + format + MyColor.ANSI_RESET;
                }
                System.out.print(format);
                System.out.print(" ");
            }
            System.out.println("");
        }
    }
    public void printMapLegend(){
        System.out.println(MyColor.ANSI_YELLOW + "LEGEND:" + MyColor.ANSI_RESET);
        System.out.println(MyColor.ANSI_YELLOW + "-> " + MyColor.ANSI_RESET + "Walkable (White)");
        System.out.println(MyColor.ANSI_YELLOW + "-> " + MyColor.ANSI_RESET + MyColor.ANSI_PURPLE + "Obstacle (Purple)" + MyColor.ANSI_RESET);
    }
    public void printEstimated(Node startNode, Node endNode, List<Node> routeNodes){
        System.out.println(MyColor.ANSI_YELLOW + "Computation Result Map:" + MyColor.ANSI_RESET);
        int i = 0;
        String format;
        System.out.println(MyColor.ANSI_YELLOW + "     0     1     2     3     4     5     6     7     8     9" + MyColor.ANSI_RESET);
        for (List<Node> list : map) {
            System.out.print(MyColor.ANSI_YELLOW + i + " " + MyColor.ANSI_RESET);
            i++;
            for (Node node : list) {
                Double temp = node.getEstimatedDistance();
                if (temp == null){
                    temp = 0D;
                }
                if (startNode == node){
                    format = new DecimalFormat("#0.00").format(temp);
                    format = MyColor.ANSI_GREEN + format + MyColor.ANSI_RESET;
                } else if (endNode == node){
                    format = new DecimalFormat("#0.00").format(temp);
                    format = MyColor.ANSI_RED + format + MyColor.ANSI_RESET;
                } else if (routeNodes.contains(node)){
                    format = new DecimalFormat("#0.00").format(temp);
                    format = MyColor.ANSI_CYAN + format + MyColor.ANSI_RESET;
                } else {
                    if (node.isObstacle()){
                        format = new DecimalFormat("#0.00").format(temp);
                        format = MyColor.ANSI_PURPLE + format + MyColor.ANSI_RESET;
                    } else if (node.isVisited()){
                        format = new DecimalFormat("#0.00").format(temp);
                        format = MyColor.ANSI_BLUE + format + MyColor.ANSI_RESET;
                    } else if (node.isChecked()){
                        format = new DecimalFormat("#0.00").format(temp);
                    } else {
                        format = new DecimalFormat("#0.00").format(temp);
                        format = MyColor.ANSI_BLACK + format + MyColor.ANSI_RESET;
                    }
                }
                if (temp < 10){
                    System.out.print(" ");
                    System.out.print(format);
                } else {
                    System.out.print(format);
                }
                System.out.print(" ");
            }
            System.out.println("");
        }
    }
}
