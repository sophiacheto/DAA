package contest3.B;
// package folha5.B;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

// Class that represents a node
class Node {
    public LinkedList<Integer> adj; // The list of outgoing edges (to adjacent nodes)
    public boolean visited;         // Has the node been visited in a graph traversal?
    public int label_ordem;              
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
    
    // adds edge from a to b and another from b to a
    public void addEdge(int a, int b) {
        if (!isEdge(a, b)) {
            nodes[a].adj.add(b);
            // nodes[b].adj.add(a);
        }
    }

    // checks whether {a,b} is an edge
    public boolean isEdge(int a,int b) {
        for (int adj : nodes[a].adj)
            if (adj == b) return true;
	    return false;
    }

    // sets all nodes as not visited
    public void clearVisited() {
	for(int i=1; i<=n; i++)
	    nodes[i].visited = false;
    }


    public boolean isTopologicalOrder(int[] order) { 
        int i=0; 
        for (int node : order) 
            nodes[node].label_ordem = i++;
        
        for(int j=1; j<=n; j++) {
            Node curr = nodes[j];
            for (int filho : curr.adj) 
            //    System.out.println("(" + j + ", " + filho + ")"); 
                if (curr.label_ordem > nodes[filho].label_ordem) return false;  
        }

        return true;        
    }

}


public class IsTopSort {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        
        int nodes = scan.nextInt();
        int edges = scan.nextInt();

        Graph grafo = new Graph(nodes);
        for (int i=0; i<edges; i++) 
            grafo.addEdge(scan.nextInt(), scan.nextInt());
        
        int queries = scan.nextInt();
        for (int q=0; q<queries; q++) {
            int[] ordem = new int[nodes];
            for (int j=0; j<nodes; j++)
                ordem[j] = scan.nextInt();
            // System.out.println(Arrays.toString(ordem));
            System.out.println(grafo.isTopologicalOrder(ordem));
        }
    }
}
