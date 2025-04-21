package contest2.B;

import java.util.LinkedList;
import java.util.Scanner;


class Node {
    public LinkedList<Integer> adj; // The list of outgoing edges (to adjacent nodes)
    public boolean visited;         // Has the node been visited in a graph traversal?
    public int aboboras;       
    // public int distance;         // Distance from a source node (or to keep some value)?


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
        if (! isEdge(a, b)) {
            nodes[a].adj.add(b);
            nodes[b].adj.add(a);
        }
    }

    // checks whether {a,b} is an edge
    public boolean isEdge(int a,int b) {
	    return nodes[a].adj.contains(b);
    }

    public int max_aboboras(int v) {
        LinkedList<Integer> q = new LinkedList<Integer>();   // queue
        for (int i=1; i<=n; i++) 
            nodes[i].visited = false;

        q.add(v);
        nodes[v].visited = true;
	// System.out.println(v);
        int max_aboboras = nodes[v].aboboras;
        int max_node = v;
	    
        while (q.size() > 0) {
            int u = q.removeFirst();
            for (int w : nodes[u].adj)
                if (!nodes[w].visited) {
                    q.add(w);
                    nodes[w].visited  = true;
		    // System.out.println(w);
                if (nodes[w].aboboras == 0)
                    continue;
                if (nodes[w].aboboras > max_aboboras || (nodes[w].aboboras == max_aboboras && w<max_node)) {
                    max_aboboras = nodes[w].aboboras;
                    max_node = w;
                }
            }	    
        }
        return max_node;
    }
}

public class Halloween {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int qtd_supermercados = scan.nextInt();
        Graph rede = new Graph(qtd_supermercados);

        int i;
        for (i=1; i<=qtd_supermercados; i++) 
            rede.nodes[i].aboboras = scan.nextInt();

        int qtd_ramos = scan.nextInt();

        for (i=0; i<qtd_ramos; i++) {
            rede.addEdge(scan.nextInt(), scan.nextInt());
        }

        int qtd_casos = scan.nextInt();
        int mercado;
        for (i=0; i<qtd_casos; i++) {
            mercado = scan.nextInt();
            if (rede.nodes[mercado].aboboras != 0) 
                System.out.println(mercado);
            
            else {
                int escolha = rede.max_aboboras(mercado);
                if (escolha == mercado)
                    System.out.println("Impossivel");
                else 
                    System.out.println(escolha);
            }
        }
    }
}
