package contest2.D;

import java.util.LinkedList;
import java.util.Scanner;
// Class that represents a node
class Node {
    public LinkedList<Integer> adj; // The list of outgoing edges (to adjacent nodes)
    public boolean visited;         // Has the node been visited in a graph traversal?
    public int group;              

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
    
    // adds edge from a to b and another from b to a
    public void addEdge(int a, int b) {
        if (!isEdge(a, b)) {
            nodes[a].adj.add(b);
            nodes[b].adj.add(a);
        }
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

    public void preprocess() {
        for (int i=n; i>0; i--)
            bfs(i);
    }
	


    // --------------------------------------------------------------
    // Breadth-First Search (BFS) from node v: example implementation
    // --------------------------------------------------------------
    public void bfs(int v) {
        if (nodes[v].visited) return;
        
        LinkedList<Integer> q = new LinkedList<Integer>();   // queue
        // for (int i=1; i<=n; i++) nodes[i].visited = false;


        q.add(v);
        nodes[v].visited = true;
	// System.out.println(v);
	    
        while (q.size() > 0) {
            int u = q.removeFirst();
            this.nodes[u].group = v;
            for (int w : nodes[u].adj)
                if (!nodes[w].visited) {
                    q.add(w);
                    nodes[w].visited  = true;
		    // System.out.println(w);
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
}

public class Ilhas {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int qtd_nos = scan.nextInt();
        int qtd_ramos = scan.nextInt();

        Graph ilhas = new Graph(qtd_nos);

        int i;
        for (i=0; i<qtd_ramos; i++) 
            ilhas.addEdge(scan.nextInt(), scan.nextInt());
        
        int qtd_questoes = scan.nextInt();
        ilhas.preprocess();
        
        int curr;
        for (i=0; i<qtd_questoes; i++) {
            curr = scan.nextInt();
            System.out.println("No " + curr + ": " + ilhas.nodes[curr].group);
        }
    }
}
