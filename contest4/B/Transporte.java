package contest4.B;
// package folha6.B;

import java.util.LinkedList;
import java.util.Queue;
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
        distance = -1;
        visited = false;
    } 
};


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


    void run(int n_origem, int n_destino, int min, int max) {
        Node origem = nodes[n_origem];
        origem.distance = 0;

        Queue<Integer> q = new LinkedList<>();
        q.add(n_origem);

        while (!q.isEmpty()) {
            int curr = q.remove();
            if (curr == n_destino) break;
            for (Edge e : nodes[curr].adj) {
                if (nodes[e.enode].visited) continue;
                if (e.temperature > max || e.temperature < min) continue;
                nodes[e.enode].distance = nodes[curr].distance + 1;
                q.add(e.enode);
                nodes[e.enode].visited = true;
            }
        }
    }
};


public class Transporte {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int temp_min = scan.nextInt();
        int temp_max = scan.nextInt();
        int origem = scan.nextInt();
        int destino = scan.nextInt();

        int qtd_nos = scan.nextInt();
        int qtd_arestas = scan.nextInt();

        Graph grafo = new Graph(qtd_nos);
        for (int i=0; i<qtd_arestas; i++) {
            grafo.addLink(scan.nextInt(), scan.nextInt(), scan.nextInt());
            scan.nextInt();
        }

        grafo.run(origem, destino, temp_min, temp_max);
        if (grafo.nodes[destino].distance == -1)
            System.out.println("Nao");
        else
            System.out.println("Sim " + grafo.nodes[destino].distance);
    }
}
