package contest2.A;

import java.util.LinkedList;
import java.util.Scanner;

class Node {
    public LinkedList<Integer> adj; // The list of outgoing edges (to adjacent nodes)
    public boolean visited;         // Has the node been visited in a graph traversal?
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
        if (! isEdge(a, b))
            nodes[a].adj.add(b);
    }

    // checks whether {a,b} is an edge
    public boolean isEdge(int a,int b) {
	    return nodes[a].adj.contains(b);
    }
}

public class Mapa {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int qtd_nos = scan.nextInt();
        int qtd_trajetos = scan.nextInt();

        Graph mapa = new Graph(qtd_nos);

        int a;
        int b;
        int nos_na_linha;
        for (int i=0; i<qtd_trajetos; i++) {
            nos_na_linha = scan.nextInt();
            a = scan.nextInt();
            for (int j=0; j<nos_na_linha-1; j++) {
                b = scan.nextInt();
                mapa.addEdge(a, b);
                // System.out.println(a + " --> " + b);
                a = b;
            }
            // System.out.println("----------");
        }

        for (int k=1; k<=qtd_nos; k++) 
            System.out.println(mapa.nodes[k].adj.size()); 
    }
}
