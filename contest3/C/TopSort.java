package contest3.C;
// package folha5.C;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

enum Color {
    WHITE, GRAY, BLACK
}
// Access to the constants using:   Color.WHITE  Color.GRAY  Color.BLACK 


// Class that represents a node
class Node {
    public LinkedList<Integer> adj; // The list of outgoing edges (to adjacent nodes)
    public boolean visited;         // Has the node been visited in a graph traversal?
    //public int distance;         // Distance from a source node (or to keep some value)?
    public int label;  // some useful value?
    public Color color; 
    public int grau; 

    Node() {
        adj = new LinkedList<Integer>();
    }
}

// Class that represents a graph
class Graph {
    int n;           // Number of nodes of the graph
    Node nodes[];    // Array that will contain the nodes

   

    // constructs a graph with n nodes and 0 edges
    Graph(int n) {
        this.n = n;
        nodes  = new Node[n+1]; // +1 if the labels start at 1 instead of 0
        for (int i=1; i<=n; i++)
            nodes[i] = new Node();
    }
    
    // Directed graph - adds edge from a to b
    public void addEdge(int a, int b) {
        nodes[a].adj.add(b);
    }

    // checks whether {a,b} is an edge
    public boolean isEdge(int a,int b) {
	return nodes[a].adj.contains(b);
    }

    // sets all nodes as not visited
    public void clearVisited() {
	for(int i=1; i<=n; i++)
	    nodes[i].visited = false;
    }
	
    // returns the number of vertices
    public int numVertices(){
	return n;
    }

    // --------------------------------------------------------------
    // Breadth-First Search (BFS) from node v: example implementation
    // --------------------------------------------------------------
    public void bfs(int v) {
        LinkedList<Integer> q = new LinkedList<Integer>();   // queue
        for (int i=1; i<=n; i++) nodes[i].visited = false;

        q.add(v);
        nodes[v].visited = true;
	System.out.println(v);
	    
        while (q.size() > 0) {
            int u = q.removeFirst();
            for (int w : nodes[u].adj)
                if (!nodes[w].visited) {
                    q.add(w);
                    nodes[w].visited  = true;
		    System.out.println(w);
                }	    
        }
    }

    // --------------------------------------------------------------
    // Depth-First Search (DFS) from node v: example implementation
    // --------------------------------------------------------------
    public void dfs(int v) {
        nodes[v].visited = true;
	System.out.println(v);
	    
	for (int w : nodes[v].adj)
	    if (!nodes[w].visited) 
		dfs(w);
    }


       public void topSort() {
        Queue<Integer> s = new LinkedList<>();
        for (int i=1; i<=n; i++) {
            nodes[i].grau = 0;
            s.add(i);
        }

        for (int i=1; i<=n; i++)
            for (int filho : nodes[i].adj) {
                nodes[filho].grau += 1;
                s.remove(filho);
            }
        
        Queue<Integer> q = new LinkedList<>();
        while (!s.isEmpty()) {
            if (s.size() > 1) {
                System.out.println("DAG with more than one topological order");
                return;
            }
            int curr = s.remove();
            q.add(curr);
            for (int no : nodes[curr].adj) {
                // Node n = nodes[e];
                // n.grau--;
                if (--nodes[no].grau == 0)
                    s.add(no);
            }
        }

        System.out.print("DAG with a single topological order:");
        for (int k=0; k<n; k++) 
            System.out.print(" " + q.remove());
        System.out.println();
        // return q;
    }


    // --------------------------------------------------------------
    // Read a undirected graph in the format:
    // nr_nodes nr_edges
    // src_1 dest_1
    // src_2 dest_2
    // ...
    // src_n dest_n
    public static Graph readGraph(Scanner scanner) {
        int n = scanner.nextInt();
        Graph g = new Graph(n);
        int nedges = scanner.nextInt();
        for (int i = 0; i < nedges; i++) {
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            g.addEdge(u, v);
        }
        return g;
    }
  
    //--------------------------------------------------------------
    //  Checks whether the graph contains a cycle
    //---------------------------------------------------------------

    boolean dfs_hasCycle(int v) {
        nodes[v].color = Color.GRAY;
        
        for (int curr : nodes[v].adj) {
            if (nodes[curr].color == Color.GRAY) 
                return true;
                // System.out.println("hascycle: " + v + " NO: " + curr);
            
            if (nodes[curr].color == Color.WHITE) 
                if (dfs_hasCycle(curr)) return true;
                // nodes[curr].color = Color.GRAY;  
        }
        
        nodes[v].color = Color.BLACK;
	return false;
    }

    public boolean hasCycle() {
	for(int v=1; v <= n; v++) 
	    nodes[v].color = Color.WHITE;
  
	for(int v=1; v<=n; v++)
	    if (nodes[v].color == Color.WHITE && dfs_hasCycle(v)) 
		return true;
	
	return false; 
    }

}



public class TopSort {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int qtd = scan.nextInt();

        for (int i=1; i<=qtd; i++) {
            Graph grafo = Graph.readGraph(scan);
            if (grafo.hasCycle())
                System.out.println("Graph #" + i + ": not a DAG (has cycle)");
            else {
                System.out.print("Graph #" + i + ": ");
                grafo.topSort();
            }
        }
    }
}
