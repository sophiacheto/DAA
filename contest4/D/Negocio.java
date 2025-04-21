// package contest4.D;

import java.util.Scanner;
import java.util.TreeSet;
import java.util.LinkedList;
import java.util.Queue;

// Class that represents an edge
class Edge {
    int enode;     // endnode
    int distance; // distance on edge
    
    Edge(int t, int w) {
        enode = t;
        distance = w;
    }
}

// Class that represents a node of the graph
class Node {
    public LinkedList<Edge> adj; // The list of outgoing edges (to adjacent nodes)
    public boolean visited;      // Has the node been visited in a graph traversal?
    public int distance;         //  Distance from a source node (or to keep some value)?
    
    Node() {
        adj = new LinkedList<>();
        distance = Integer.MAX_VALUE;
        visited = false;
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


    void Dijkstra(int origem) {
        // Initialize all nodes as not visited and with "infinite" distance
        TreeSet<NodeQ> q = new TreeSet<>();
        for (int i=1; i<=n; i++) {
            nodes[i].distance = Integer.MAX_VALUE;
            nodes[i].visited  = false;
            q.add(new NodeQ(nodes[i].distance, i));
        }
    
        // Inicialize the priority queue with source node s
        q.remove(new NodeQ(Integer.MAX_VALUE, origem));
        nodes[origem].distance = 0;
        int u = origem;
        nodes[origem].visited = true;
        System.out.print(origem);
        
        do {
            // Update distances of adjacent nodes ("Relax edges")
            for (Edge e : nodes[u].adj) {
                int v = e.enode;
                int cost = e.distance;
                if (!nodes[v].visited && nodes[u].distance + cost < nodes[v].distance) {
                    q.remove(new NodeQ(nodes[v].distance, v)); // Remove from the set
                    nodes[v].distance = nodes[u].distance + cost;
                    q.add(new NodeQ(nodes[v].distance, v));    // Insert a new node with a shorter distance
                }
            }
            NodeQ nq = q.pollFirst();
            u = nq.node;
            System.out.print(" " + u);
            nodes[u].visited = true;
        } while (!q.isEmpty());

        System.out.println();
    }
};


public class Negocio {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int qtd_regioes = scan.nextInt();
        int destino = scan.nextInt();

        Graph grafo = new Graph(qtd_regioes);
        while (true) {
            int a = scan.nextInt();
            if (a == -1) break;
            grafo.addLink(a, scan.nextInt(), scan.nextInt());
        }

        grafo.Dijkstra(destino);
    }
}
