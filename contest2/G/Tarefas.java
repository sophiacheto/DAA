package contest2.G;

import java.util.LinkedList;
import java.util.Scanner;

class Edge {
    public int enode;
    public int value;

    Edge(int endv, int v) {
        enode = endv;
        value = v;
    }
}

// Class that represents a node
class Node {
    public LinkedList<Edge> adj; // The list of outgoing edges (to adjacent nodes)
    public boolean visited;         // Has the node been visited in a graph traversal?
    // public int group;              

    Node() {
        adj = new LinkedList<Edge>();
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
    public void addEdge(int a, int b, int value) {
        if (!isEdge(a, b)) {
            nodes[a].adj.add(new Edge(b, value));
            // nodes[b].adj.add(a);
        }
    }

    // checks whether {a,b} is an edge
    public boolean isEdge(int a,int b) {
        for (Edge adj : nodes[a].adj)
            if (adj.enode == b) return true;
	    return false;
    }

    // sets all nodes as not visited
    public void clearVisited() {
	for(int i=1; i<=n; i++)
	    nodes[i].visited = false;
    }

    // --------------------------------------------------------------
    // Breadth-First Search (BFS) from node v: example implementation
    // --------------------------------------------------------------
    public void find(int v) {
        LinkedList<Integer> q = new LinkedList<Integer>();   // queue
        // for (int i=1; i<=n; i++) nodes[i].visited = false;
        int qtd = 0;
        int duracao = 0;

        if (nodes[v].visited) return;

        q.add(v);
        nodes[v].visited = true;
	// System.out.println(v);
	    
        while (q.size() > 0) {
            int u = q.removeFirst();
            // this.nodes[u].group = v;
            for (Edge w : nodes[u].adj)
            {
                // System.out.println(u + " --> " + w.enode);
                if (!nodes[w.enode].visited) 
                    q.add(w.enode);
                    qtd++;
                    duracao += w.value;
                    nodes[w.enode].visited  = true;
		    // System.out.println(w);
                }	    
        }

        System.out.println(qtd);
        System.out.println(duracao);
    } 
}


public class Tarefas {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int qtd_nos = scan.nextInt();
        int qtd_atvs = scan.nextInt();
        int atv = scan.nextInt();
        Graph planeamento = new Graph(qtd_nos);

        for (int i=1; i<=qtd_atvs; i++) 
            planeamento.addEdge(scan.nextInt(), scan.nextInt(), scan.nextInt());
        
        planeamento.find(atv);
    }
}
