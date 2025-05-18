// package contest6.C;

import java.util.Scanner;
import java.util.LinkedList;


class Edge {
    public int enode;
    public int weight;

    Edge(int endv, int v) {
        enode = endv;
        weight = v;
    }
}


// Class that represents a node
class Node {
    public LinkedList<Edge> adj; // The list of outgoing edges (to adjacent nodes)
    public int fluxo;
    public boolean visited;         // Has the node been visited in a graph traversal?

    Node() {
        adj = new LinkedList<Edge>();
        fluxo = 0;
    }

    void atualizarEdge(int b, int val) {
        fluxo += val;
        for (Edge adj : this.adj)
            if (adj.enode == b)
                adj.weight += val;
    }

    int getWeight(int b) {
        for (Edge adj : this.adj) 
            if (adj.enode == b)
                return adj.weight;
        
        return 0;
    }
}

// Class that represents a graph
class Graph {
    int n;           // Number of nodes of the graph
    Node nodes[];    // Array that will contain the nodes
    int pais[];

    // constructs a graph with n nodes and 0 edges
    Graph(int n) {
        this.n = n;
        nodes  = new Node[n+1]; // +1 if the labels start at 1 instead of 0
        pais = new int[n+1];
        for (int i=1; i<=n; i++)
            nodes[i] = new Node();
    }
    
    // adds edge from a to b and another from b to a
    public void addEdge(int a, int b) {
        if (!isEdge(a, b)) 
            nodes[a].adj.add(new Edge(b, 1));
    }

    // checks whether {a,b} is an edge
    public boolean isEdge(int a,int b) {
        for (Edge adj : nodes[a].adj)
            if (adj.enode == b) return true;
	    return false;
    }


    public boolean bfs(int origem, int destino) {  
        for (int i=1; i<=n; i++) nodes[i].visited = false;
        for (int i=1; i<=n; i++) pais[i] = 0;
        LinkedList<Integer> q = new LinkedList<Integer>();   // queue


        q.add(origem);
        nodes[origem].visited = true;
	    
        while (q.size() > 0) {
            int u = q.removeFirst();
            // System.out.println("pai: " + u + "-----------------");
            for (Edge e : nodes[u].adj) {
                int w = e.enode;
                // System.out.println("filho: " + w);
                if (!nodes[w].visited && e.weight > 0) {
                    q.add(w);
                    nodes[w].visited  = true;
                    pais[w] = u;
                    if (w == destino)
                        return true;
                }	    

            }
        }
        return false;
    }

    public int fluxoMaximo(int origem, int destino) {

        while (bfs(origem, destino)) {
            int curr = destino;

            curr = destino;
            while (curr != origem) {
                nodes[curr].atualizarEdge(pais[curr], 1);
                nodes[pais[curr]].atualizarEdge(curr, -1);
                curr = pais[curr];
            }
        }

        return nodes[destino].fluxo;
    }
}

public class Livros {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int qtd_casos = scan.nextInt();

        for (int i=0; i<qtd_casos; i++) {
            int num_pessoas = scan.nextInt();
            int qtd_interesses = scan.nextInt();

            Graph rede = new Graph(num_pessoas*2 + 2);

            int s = num_pessoas*2+1;
            int t = s+1;
            for (int j=0; j<qtd_interesses; j++) {
                int pessoa = scan.nextInt() + 1;
                int livro = scan.nextInt() + 1 + num_pessoas;

                rede.addEdge(s, pessoa);
                rede.addEdge(pessoa, livro);
                rede.addEdge(livro, t);
            }

            if (rede.fluxoMaximo(s, t) == num_pessoas)
                System.out.println("YES");
            else
                System.out.println("NO");
        }

    }
}
