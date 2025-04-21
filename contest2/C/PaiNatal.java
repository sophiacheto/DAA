import java.util.LinkedList;
import java.util.Scanner;

class Node {
    public LinkedList<Integer> adj; // The list of outgoing edges (to adjacent nodes)
    public boolean visited;         // Has the node been visited in a graph traversal?
    public int paisnatais; 
    public int distance;            // Distance from a source node (or to keep some value)?


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

    public void showEdges() {
        for (int i=1; i<=n; i++) {
            Node node = this.nodes[i];
            System.out.print(i + " || ");
            for (int adj : node.adj) 
                System.out.print(adj + " ");
            System.out.println();
        }
    }
    
    // adds edge from a to b and another from b to a
    public void addEdge(int a, int b) {
        if (! isEdge(a, b)) {
            nodes[a].adj.add(b);
            nodes[b].adj.add(a);
        }
    }

    // checks whether {a,b} is an edge
    public boolean isEdge(int a,int b) {
	    return nodes[a].adj.contains(b);
    }

    public int bfs(int v, int maxima) {
        int qtd=0;
        LinkedList<Integer> q = new LinkedList<Integer>();   // queue
        for (int i=1; i<=n; i++) nodes[i].visited = false;

        q.add(v);
        nodes[v].visited = true;
        nodes[v].distance = 0;
	    // System.out.println(v);
	    
        while (q.size() > 0) {
            int u = q.removeFirst();
            if (nodes[u].paisnatais != 0) {
                // System.out.println("NO: " + u);
                qtd++;
            }
            for (int w : nodes[u].adj)
                if (!nodes[w].visited) {
                    nodes[w].distance=nodes[u].distance+1;
                    if (nodes[w].distance <= maxima) 
                        q.add(w);
                    nodes[w].visited  = true;
		    // System.out.println(w);
                }	    
        }

        return qtd;
    }

}

public class PaiNatal {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int qtd_lojas = scan.nextInt();
        Graph lojas = new Graph(qtd_lojas);
        int i;

        for (i=1; i<=qtd_lojas; i++) 
            lojas.nodes[i].paisnatais=scan.nextInt();

        int qtd_ramos = scan.nextInt();
        for (i=0; i<qtd_ramos; i++) 
            lojas.addEdge(scan.nextInt(), scan.nextInt());
        
        int loja_atual = scan.nextInt();
        int dist_maxima = scan.nextInt();

        // lojas.showEdges();
        // System.out.println();
        if (lojas.nodes[loja_atual].paisnatais != 0)
            System.out.println("Que sorte");
        else {
            System.out.println(lojas.bfs(loja_atual, dist_maxima));
        }
        
    }
}
