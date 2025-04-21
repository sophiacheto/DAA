// package folha6.C;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.TreeSet;
// Class that represents an edge
class Edge {
    int enode;     // endnode
    int weight; // weight on edge
    
    Edge(int t, int w) {
        enode = t;
        weight = w;
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

    // Algorithm by Dijkstra
    int dijkstra(int origem, int destino) {
	
        // Initialize all nodes as not visited and with "infinite" distance
        for (int i=1; i<=n; i++) {
            nodes[i].distance = Integer.MAX_VALUE;
            nodes[i].visited  = false;
        }
	
        // Inicialize the priority queue with source node s
        nodes[origem].distance = 0;
        TreeSet<NodeQ> q = new TreeSet<>();
        q.add(new NodeQ(0, origem)); // Creates a pair (dist=0, no=s)

        while (!q.isEmpty()) {
      
            // Remove node with minimum distance (the first in the set which is a BST)
            NodeQ nq = q.pollFirst();
            int  u = nq.node;
            nodes[u].visited = true;
            if (u == destino) break;
            // System.out.println(u + " [dist=" + nodes[u].distance + "]");
	        
            // Update distances of adjacent nodes ("Relax edges")
            for (Edge e : nodes[u].adj) {
                int v = e.enode;
                int cost = e.weight;
                if (!nodes[v].visited && nodes[u].distance + cost < nodes[v].distance) {
                    q.remove(new NodeQ(nodes[v].distance, v)); // Remove from the set
                    nodes[v].distance = nodes[u].distance + cost;
                    q.add(new NodeQ(nodes[v].distance, v));    // Insert a new node with a shorter distance
                }
            }
        }

        return nodes[destino].distance;
    }
};

public class Cortes {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int min = scan.nextInt();
        int max = scan.nextInt();
        int origem = scan.nextInt();
        int destino = scan.nextInt();

        int qtd_nos = scan.nextInt();
        int qtd_arestas = scan.nextInt();

        Graph grafo = new Graph(qtd_nos);
        for (int i=0; i<qtd_arestas; i++) {
            int a = scan.nextInt();
            int b = scan.nextInt();
            int temp = scan.nextInt();
            int custo = scan.nextInt();
            if (temp >= min && temp <= max)
                grafo.addLink(a, b, custo);
        }

        int custo_min = grafo.dijkstra(origem, destino);

        int qtd_cenarios = scan.nextInt();
        for (int j=0; j<qtd_cenarios; j++) 
            if (scan.nextInt() >= custo_min)
                System.out.println("Sim");
            else 
                System.out.println("Nao");
    }
}
