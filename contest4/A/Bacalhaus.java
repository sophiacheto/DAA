// package folha6.A;

import java.util.LinkedList;
import java.util.Scanner;
import java.util.TreeSet;

// Class that represents an edge
class Edge {
    int enode;     // endnode
    int temperature; // temperature on edge
    
    Edge(int t, int w) {
        enode = t;
        temperature = w;
    }
}

// Class that represents a node of the graph
class Node {
    public LinkedList<Edge> adj; // The list of outgoing edges (to adjacent nodes)
    public boolean visited;      // Has the node been visited in a graph traversal?
    public int distance;         //  Distance from a source node (or to keep some value)?
    
    Node() {
        adj = new LinkedList<>();
    } 
};

// Class that represents a node for the priority queue
class NodeQ implements Comparable<NodeQ> {
    public int cost;
    public int node;

    NodeQ(int c, int n) {
        cost = c;
        node = n;
    }

    @Override
	public int compareTo(NodeQ nq) { 
        if (cost < nq.cost) return -1; 
        if (cost > nq.cost) return +1;
        if (node < nq.node) return -1; 
        if (node > nq.node) return +1;
        return 0; 
    } 
}

// Class that represents a weighted digraph 
class Graph {
    int n;          // Number of nodes
    Node[] nodes;   // Array of nodes 
    
    Graph(int n) {  // constructs a graph with n nodes and 0 edges
        this.n = n;
        nodes = new Node[n+1];   // +1 if the labels start at 1 instead of 0
        for (int i=1; i<=n; i++)
            nodes[i] = new Node();
    }
    
    void addLink(int a, int b, int c) {
        nodes[a].adj.add(new Edge(b, c));
        nodes[b].adj.add(new Edge(a, c));
    }


    void run(int qtd, int[] percurso) {
        int min=1000;
        int max=-1000;
        Node curr = nodes[percurso[0]];
        
        for (int i=1; i<qtd; i++) 
            for (Edge e : curr.adj) 
                if (e.enode == percurso[i]) {
                    curr = nodes[e.enode];
                    if (e.temperature < min) min = e.temperature;
                    if (e.temperature > max) max = e.temperature;
                }
        
        System.out.println(min + " " + max);  
    }
};


public class Bacalhaus {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        
        int qtd_nos = scan.nextInt();
        int qtd_arestas = scan.nextInt();

        Graph grafo = new Graph(qtd_nos);
        for (int i=0; i<qtd_arestas; i++) {
            grafo.addLink(scan.nextInt(), scan.nextInt(), scan.nextInt());
            scan.nextInt();
        }

        while (true) {
            int qtd = scan.nextInt();
            if (qtd == 0)
                break;

            int[] percurso = new int[qtd];
            for (int j=0; j<qtd; j++) 
                percurso[j] = scan.nextInt();
            grafo.run(qtd, percurso);
        }
    }
}
