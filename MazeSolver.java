import java.util.Scanner;
import java.io.File;
import java.awt.Point;

class Node{
    Node up, lft, dwn, rit;
    Point coord;
    boolean visited;
    Node (Point indx){
        this.coord = indx;
        this.visited = false;
    }
}

class SingleLinkedListNode {
    Node data;
    SingleLinkedListNode next;
}

class Queue{
    private class SingleLinkedList{
        SingleLinkedListNode head;
        SingleLinkedListNode tail;
        int size=0;
        
        //add to the rear of the list ,where : rear --> head of list
        public void addToRear (Node element){
            SingleLinkedListNode temp = new SingleLinkedListNode ();
            temp.data = element;
            if (size==0) {
                head = temp;
                tail = temp;
                size++;
            }else {
                temp.next = head;
                head = temp;
                size++;
            }
        }
        //remove from front , where : front --> tail of list
        public Node removeFromFront (){
            Node nodeRemoved = new Node(null);
            if (size == 0) {
                throw new NullPointerException("Error");
            }else{
                SingleLinkedListNode temp = head;
                nodeRemoved = tail.data;
                for (int i=0; i<size-2; i++) temp = temp.next; //moving pointer to preTail node
                tail = temp;
                tail.next = null;
                size--;
            }
            return nodeRemoved;
        }
        //getting front of the queue --> tail of list
        public Node getFront (){
            return tail.data;
        }
        //get the size of the list
        public int size(){
            return size;
        } 
    }
    int size;
    SingleLinkedList list = new SingleLinkedList();
    public void enque(Node item){
        list.addToRear(item);
    }
    public Node deque(){
        return list.removeFromFront();
    }
    public boolean isEmpty(){
        if (list.size()==0) return true;
        else return false;
    }
    public Node peek(){
        return list.getFront();
    }
    public int size(){
        return list.size();
    }
}

class Stack {
    private class SingleLinkedList {
        SingleLinkedListNode head;
        SingleLinkedListNode tail;
        int size=0;
        
       //add to the beginnig of the list 
        public void add(Node element){
            SingleLinkedListNode temp = new SingleLinkedListNode();
            temp.data = element;
            if (size==0) {
                head = temp;
                tail = temp;
                size++;
            }
            else {
                temp.next = head;
                head = temp;
                size++;
            }
        }
        //getting head
        public Node getHead (){
            Node temp = head.data;
            return temp;
        }
        //remove form the beginning
        public void remove (){
            SingleLinkedListNode temp = head;
            head = head.next;
            temp.next = null;
            size--;
        }
        //get the size of the list
        public int size(){
            return size;
        } 
    }
    
    Stack (Node element){
        this.push(element);
    }

    SingleLinkedList stack = new SingleLinkedList();
    public Node pop(){
        Node temp = new Node(null);
        if (stack.size() == 0){
            System.out.println("Error");
            return null;
        }
        else {
            temp = stack.getHead();
            stack.remove();
        }
        return temp;
    }

    public Node peek(){
        Node temp = new Node(null);
        if (stack.size() == 0){
            System.out.println("Error");
            return null;
        }
        else {
            temp = stack.getHead();
        }
        return temp;
    }

    public void push(Node element){
        stack.add(element);
    }

    public boolean isEmpty(){
        if (stack.size() == 0) return true;
        else return false;
    }

    public int size(){
        return stack.size();
    }

}

class MapStartAndGoal{
    Node start, goal;
    MapStartAndGoal(Node start, Node goal){
        this.start = start;
        this.goal = goal;
    }
}

public class MazeSolver {
    Node start, goal, temp, newNode = new Node(null);

    public String[] mapArrayReader(){
        File maze = new File("Maze.txt");   
        Scanner sc; 
        //reading maze map
        try{
            sc = new Scanner(maze);
        }catch(Exception e){
            System.out.println("error reading file");
            return null;
        }
        //reading maze size 
        String read;
        try{
            read = sc.nextLine();
        }catch(Exception e){
            System.out.println("Error: File is empty!");
            return null;
        }
        //parsing size into int varaiables m & n
        int m, n;
        try {
            m = Integer.parseInt(read.substring(0, 1));
            n = Integer.parseInt(read.substring(2, 3));
        }catch(Exception e){
            System.out.println("Error in maze dimenstions!");
            return null;
        }

        //initializing map array
        String[] mapArray = new String[m];
        //reading maze map
        for (int i=0; i<m; i++){
            try{
                mapArray[i] = sc.nextLine();
                //checking for the number of columns 
                if (mapArray[i].length()!=n){
                    System.out.println("Error Reading map!");
                    return null;
                }
            }catch(Exception e){
                System.out.println("Error: Reading map!");
                return null;
            }
        }
        return mapArray;
    }
    public void setNodePointers(String move, Node nNode){
        if(temp==null) return;
        else{
            switch(move){
                case "up":
                    nNode.dwn=temp;
                    temp.up=nNode;
                    break;
                case "left":
                    nNode.rit=temp;
                    temp.lft=nNode;
                    break;
                case "down":
                    nNode.up=temp;
                    temp.dwn=nNode;
                    break;
                case "right":
                    nNode.lft=temp;
                    temp.rit=nNode;
                    break;
            }
        }
    }
    public void nodeGoBack (Node node, String move){
        if (node!=null){

            switch(move){
                case "up":
                    temp = temp.dwn;
                    break;
                case "left":
                    temp = temp.rit;
                    break;
                case "down":
                    temp = temp.up;
                    break;
                case "right":
                    temp = temp.lft;
                    break;
            }
        }
    }

    boolean checkNode = false;
    public Node searchForNode (Node newNode, Node search){
        if (search == null) return search;
        else if (search.coord == newNode.coord){
            checkNode = true;
            return search;
        }else {
            if (!checkNode) searchForNode(newNode, search.up); //searching up
            if (!checkNode) searchForNode(newNode, search.lft); //searching left
            if (!checkNode) searchForNode(newNode, search.dwn); //searching down
            if (!checkNode) searchForNode(newNode, search.rit); // searching right
            return search;
        }
    }
    
    public MapStartAndGoal mapCreator(String [] maze, int r, int c, String move){
        Node checkNodePres;
        if (r>=maze.length || c>=maze[0].length() || r<0 || c<0) return null;
        else {
            if (maze[r].charAt(c)!='#'){
                newNode = new Node(new Point(r, c));
                checkNodePres = searchForNode(newNode, temp);
                if (checkNodePres != temp){ //there is node found with the same data as newNode
                    if (move == "right" && checkNodePres.lft == null){
                        setNodePointers(move, checkNodePres); //setting directions in map
                    }
                    else if (move == "up" && checkNodePres.dwn == null){
                        setNodePointers(move, checkNodePres); //setting directions in map
                    }
                    else if (move == "left" && checkNodePres.rit == null){
                        setNodePointers(move, checkNodePres); //setting directions in map
                    }
                    else if (move == "down" && checkNodePres.up == null){
                        setNodePointers(move, checkNodePres); //setting directions in map
                    }
                    return null;
                }else{ //if this data is a new data
                    setNodePointers(move, newNode); //setting directions in map
                    temp = newNode;
                }
                
                if (maze[r].charAt(c)=='S'){ //check if it is the start node
                    if (start==null){
                        start = temp;
                    }else start.coord = new Point(-1, -1);
                }
                else if (maze[r].charAt(c)=='E'){ //check if it is the goal node
                    if (goal==null){
                        goal = temp;
                    }else goal.coord = new Point(-1, -1);
                }
            }
            else{
                return null;
            }

            //searching up
            mapCreator(maze, r-1, c, "up");
            //searching left
            mapCreator(maze, r, c-1, "left");
            //searching down
            mapCreator(maze, r+1, c, "down");
            //searching right
            mapCreator(maze, r, c+1, "right");
            nodeGoBack(temp, move);

        }
        MapStartAndGoal startAndGoalNodes = new MapStartAndGoal(start, goal);
        return startAndGoalNodes;
    } 
    
    public Point[] traceDFSRoute (Stack stack){
        Stack reversedRoute = new Stack(stack.pop()); //puting goal in reversedRoute
        while (!stack.isEmpty()){
            reversedRoute.push(stack.pop());
        }
        Point[] route = new Point[reversedRoute.size()];
        for (int i=0; i<route.length; i++) route[i]=reversedRoute.pop().coord; //getting route by reversing reversedRoute elements by poping them
        return route;
    }

    public Point[] DFS (){
        Stack stack = new Stack(start);
        start.visited = true; //mark start node as visited

        // System.out.println("("+(int)stack.peek().coord.getX() + " , " + (int)stack.peek().coord.getY() + ")");
        while (!stack.isEmpty()){
            if(stack.peek() == goal) return traceDFSRoute(stack); //goal found
            else if (stack.peek().rit != null && stack.peek().rit.visited == false){ //check if current node has unvisited right child
                stack.push(stack.peek().rit);
                stack.peek().visited = true; //mark node as visited

            }
            else if (stack.peek().up != null && stack.peek().up.visited == false){ //check if current node has unvisited up child
                stack.push(stack.peek().up);
                stack.peek().visited = true; //mark node as visited

            }
            else if (stack.peek().lft != null && stack.peek().lft.visited == false){ //check if current node has unvisited left child
                stack.push(stack.peek().lft);
                stack.peek().visited = true; //mark node as visited

            }
            else if (stack.peek().dwn != null && stack.peek().dwn.visited == false){ //check if current node has unvisited down child
                stack.push(stack.peek().dwn);
                stack.peek().visited = true; //mark node as visited

            }
            else stack.pop();
        }
        return null;
    }

    public Queue reverseQ (Queue q){
        Node[] temp = new Node [q.size()];
        for (int i=0; i<q.size(); i++){ //emptying q in an array
            temp[i] = q.deque();
        }

        // for (int i=0; i<temp.length; i++) System.out.println(temp[i].coord + " ");

        for (int i=temp.length-1; i>=0; i--){ //refilling q with its elements inversly
            q.enque(temp[i]);
        }
        return q;
    }

    // public static void function(){
    //     Queue q = new Queue();
    //     Node n = new Node(new Point(0, 1));
    //     q.enque(n);
    //     System.out.println(q.peek().coord.getY());
    // }
    
    public Point[] traceBFSRoute(Queue routeQ){
        routeQ = reverseQ(routeQ); //reversing queue
        Node temp;
        boolean check = true;
        for (int i=0; i<routeQ.size()-1; i++){
            check = true;
            temp = routeQ.deque();
            while(check){
                if (temp == routeQ.peek().rit) { routeQ.enque(temp); i++; check = false;} //check if routeQ.peek() is parent for temp node
                else if (temp == routeQ.peek().up) {routeQ.enque(temp); i++; check = false;}//..
                else if (temp == routeQ.peek().lft) {routeQ.enque(temp); i++; check = false;}//..
                else if (temp == routeQ.peek().dwn) {routeQ.enque(temp); i++; check = false;}//..
                else {
                    routeQ.deque();
                }
            }
        }
        routeQ.enque(routeQ.deque()); //putting the last element (start node)
        Point[] route = new Point[routeQ.size()];
        for (int i=routeQ.size()-1; i>=0; i--){
            route[i] = routeQ.deque().coord;
        }        
        return route;
    }
    
    public Point[] BFS (){
        Queue searchQ = new Queue();
        Queue routeQ = new Queue();
        Node temp;
        boolean check;
        searchQ.enque(start); //add start to searching queue
        //routeQ.enque(start); //add start to route queue
        start.visited = true; //mark start node as visited
        while (!searchQ.isEmpty()){
            temp = searchQ.deque();
            
            check = false;
            if(temp == goal){
                routeQ.enque(temp);
                return traceBFSRoute(routeQ);
            }
            else{
                if (temp.rit != null && temp.rit.visited == false){ //check if current node has right child
                    searchQ.enque(temp.rit); //add this child to searching queue
                    temp.rit.visited = true; //mark as visited
                    check = true;
                    // System.out.println(temp.rit.coord);
                }
                if (temp.up != null && temp.up.visited == false){ //check if current node has up child
                    searchQ.enque(temp.up); //add this child to searching queue
                    temp.up.visited = true; //mark as visited
                    check = true;
                    // System.out.println(temp.up.coord);
                }
                if (temp.lft != null && temp.lft.visited == false){ //check if current node has left child
                    searchQ.enque(temp.lft); //add this child to searching queue
                    temp.lft.visited = true; //mark as visited
                    check = true;
                    // System.out.println(temp.lft.coord);
                }
                if (temp.dwn != null && temp.dwn.visited == false){ //check if current node has down child
                    searchQ.enque(temp.dwn); //add this child to searching queue
                    temp.dwn.visited = true; //mark as visited
                    check = true;
                    // System.out.println(temp.dwn.coord);
                }
                if (check) routeQ.enque(temp); //if any child is found from current node (temp) it will be added to routeQ
            }
        }
        return null;
    }
    public static void printRoute (Point[] route){
        for (int i=0; i<route.length; i++){
            System.out.print("(" + (int)route[i].getX() + " , " + (int)route[i].getY() + ")");
            if (i<route.length-1) System.out.print(", ");
        }
    }


    public static void main(String[] args){
        MazeSolver program = new MazeSolver();
        String[] mazeArray = program.mapArrayReader();
        if (mazeArray == null) return;
        int r=0; int c=0;
        MapStartAndGoal startAndGoalNodes = new MapStartAndGoal(null, null);
        while (r<mazeArray.length && mazeArray[r].charAt(c)=='#'){
            if(c==mazeArray[0].length()-1){c=0; r++;}
            else c++;
        }
        if (r==mazeArray.length){
            System.out.println("All the maze is walls:)");
            return;
        }else{ 
            startAndGoalNodes = program.mapCreator(mazeArray, r, c, "no_move");
        }
        if (startAndGoalNodes.start==null || startAndGoalNodes.start.coord.getX()==-1){
            System.out.println("Error: No/more than one Start is found!");
            return;
        }
        else if (startAndGoalNodes.goal==null || startAndGoalNodes.goal.coord.getX()==-1){
            System.out.println("Error: No/more than one Goal is found!");
            return;
        }
        Point[] route = program.BFS();
        if (route != null) printRoute(route);
        else System.out.println("Pass not found!");
    }
}
