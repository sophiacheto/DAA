package contest3.D;
// package folha5.D;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.TreeMap;


class Edge {
    public int enode;
    public int duracao;
    public int inicio;
    // public int fim;

    Edge(int endv, int v) {
        enode = endv;
        duracao = v;
    }
}

// Class that represents a node
class Node {
    public LinkedList<Edge> adj; // The list of outgoing edges (to adjacent nodes)
    public boolean visited;         // Has the node been visited in a graph traversal?
    public int instante;
    public int grau;              

    Node() {
        adj = new LinkedList<Edge>();
        instante = -1;
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
    // public void find(int atv) {
    //     LinkedList<Integer> q = new LinkedList<Integer>();   // queue
    //     Map<Integer, Integer> events = new TreeMap<>();

    //     // for (int i=1; i<=n; i++) nodes[i].visited = false;
    //     // int qtd = 0;

    //     // if (nodes[v].visited) return;

    //     q.add(1);
    //     // events.put(0, nodes[1].adj.size());
    //     nodes[1].visited = true;
    //     nodes[1].instante = 0;
	    
    //     while (q.size() > 0) {
    //         int u = q.removeFirst();
    //         for (Edge w : nodes[u].adj) {
    //             if (nodes[w.enode].instante < nodes[u].instante + w.duracao)
    //                 nodes[w.enode].instante = nodes[u].instante + w.duracao;
                
    //             if (!nodes[w.enode].visited)  
    //                 q.add(w.enode);

    //             nodes[w.enode].visited  = true;
    //         }	    
    //     }

    //     // System.out.println(qtd);
    //     System.out.print(nodes[atv].instante + " ");
    // } 

    public void find() {
        Queue<Integer> s = new LinkedList<>();
        for (int i=1; i<=n; i++) {
            nodes[i].grau = 0;
            nodes[i].instante = 0;
            s.add(i);
        }

        for (int i=1; i<=n; i++)
            for (Edge filho : nodes[i].adj) {
                nodes[filho.enode].grau += 1;
                nodes[filho.enode].instante = -1;
                s.remove(filho.enode);
            }
        
        TreeMap<Integer, Integer> eventos = new TreeMap<>();
        while (!s.isEmpty()) {
            // if (s.size() > 1) {
            //     System.out.println("DAG with more than one topological order");
            //     return;
            // }
            int curr = s.remove();
            eventos.put(nodes[curr].instante, eventos.getOrDefault(nodes[curr].instante,0)+nodes[curr].adj.size());

            for (Edge ed : nodes[curr].adj) {
                Node no = nodes[ed.enode];
                ed.inicio=nodes[curr].instante;
                eventos.put(ed.inicio+ed.duracao, eventos.getOrDefault(ed.inicio+ed.duracao,0)-1);
                if (no.instante < nodes[curr].instante + ed.duracao)
                    no.instante = nodes[curr].instante + ed.duracao;
            
                if (--no.grau == 0)
                    s.add(ed.enode);
            }
        }

        System.out.print(nodes[n].instante);

        int max_atvs = 0;
        int instante = 0;
        int curr = 0;
        // System.out.println(eventos);

        while (!eventos.isEmpty()) {
            Map.Entry<Integer, Integer> retirado = eventos.pollFirstEntry();
            curr += retirado.getValue();
            if (curr > max_atvs) {
                max_atvs = curr;
                instante = retirado.getKey();
            }
        }

        System.out.println(" " + max_atvs + " " + instante);
    }
}



public class Sobrecarga {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int qtd_nos = scan.nextInt();
        int qtd_tarefas = scan.nextInt();
        
        Graph tarefas = new Graph(qtd_nos);

        for(int i=0; i<qtd_tarefas; i++)
            tarefas.addEdge(scan.nextInt(), scan.nextInt(), scan.nextInt());

        tarefas.find();
        // tarefas.picos();  
    }
}
