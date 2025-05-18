
import java.util.LinkedList;
import java.util.Scanner;


class Edge {
    public int enode;
    // public int capacidade;
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
        if (!isEdge(a, b)) {
            nodes[a].adj.add(new Edge(b, 1));
            nodes[b].adj.add(new Edge(a, 1));
        }
    }

    // checks whether {a,b} is an edge
    public boolean isEdge(int a,int b) {
        for (Edge adj : nodes[a].adj)
            if (adj.enode == b) return true;
	    return false;
    }


    public boolean bfs(int origem, int destino) {  
        // System.out.println("teste");  
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
                    // if (w == 14) System.out.println("A");
                    q.add(w);
                    nodes[w].visited  = true;
                    pais[w] = u;
                    if (w == destino)
                        return true;
                }	    

            }
        }
        // System.out.println("false");
        return false;
    }

    public int fluxoMaximo(int origem, int destino) {
        int fluxoMax = nodes[origem].fluxo;
        // System.out.println(fluxoMax);

        while (bfs(origem, destino)) {
            // System.out.println("Sim");
            // System.out.println("----------");
            int curr = destino;

            // System.out.println("MIN: " + min);
            curr = destino;
            while (curr != origem) {
                // System.out.println(curr);
                nodes[curr].atualizarEdge(pais[curr], 1);
                nodes[pais[curr]].atualizarEdge(curr, -1);
                curr = pais[curr];
            }
            
            fluxoMax += 1;
        }
        return fluxoMax;
    }
}

public class Corridas {
    public static void main(String[] args) {
        // colocar todos os caminhos com capacidade 1 e achar o fluxo maximo
        Scanner scan = new Scanner(System.in);

        int qtd_nos = scan.nextInt();
        int qtd_arestas = scan.nextInt();

        Graph parque = new Graph(qtd_nos);
        for (int i=0; i<qtd_arestas; i++) {
            parque.addEdge(scan.nextInt(), scan.nextInt());
        }
        // System.out.println(parque.nodes[9].getWeight(14));
        // System.out.println(parque.nodes[1].fluxo);
        System.out.println(parque.fluxoMaximo(1, qtd_nos));

    }
}
