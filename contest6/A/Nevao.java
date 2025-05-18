// package contest6.A;

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
    public void addEdge(int a, int b, int capacidade, int fluxo) {
        nodes[a].fluxo += fluxo;
        nodes[b].fluxo -= fluxo;

        if (!isEdge(a, b)) {
            nodes[a].adj.add(new Edge(b, capacidade-fluxo));
            nodes[b].adj.add(new Edge(a, fluxo));
        }
        else {
            nodes[b].atualizarEdge(a, fluxo);
            nodes[a].atualizarEdge(b, capacidade-fluxo);
        }
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
            for (Edge e : nodes[u].adj) {
                int w = e.enode;
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

    public int fluxoMaximo(int origem, int destino, int qtd_pessoas) {
        int fluxoMax = nodes[origem].fluxo;

        while (bfs(origem, destino)) {
            // System.out.println("----------");
            int min = qtd_pessoas+1;
            int curr = destino;
            while (curr != origem) {
                // System.out.println(curr);
                int weight_curr = nodes[pais[curr]].getWeight(curr);
                if (weight_curr < min) min = weight_curr;
                curr = pais[curr];
            }

            // System.out.println("MIN: " + min);
            curr = destino;
            while (curr != origem) {
                nodes[curr].atualizarEdge(pais[curr], min);
                nodes[pais[curr]].atualizarEdge(curr, -min);
                curr = pais[curr];
            }
            
            if (fluxoMax+min > qtd_pessoas) return qtd_pessoas;
            fluxoMax += min;
        }
        return fluxoMax;
    }
}


public class Nevao {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int qtd_pessoas = scan.nextInt();
        int origem = scan.nextInt();
        int destino = scan.nextInt();
        int qtd_veiculos = scan.nextInt();
        // int evacuados = 0;

        // System.out.println("Origem: " + origem);
        // System.out.println("Destino: " + destino);
        
        Graph rede_residual = new Graph(16001);
        for (int i=0; i<qtd_veiculos; i++) {
            int a = scan.nextInt();
            int b = scan.nextInt();
            int capacidade = scan.nextInt();
            int fluxo = scan.nextInt();

            // if (a==origem)
            //     evacuados += fluxo;
            // if (b==origem)
            //     evacuados -= fluxo;

            // checar inconsistência
            if (fluxo > capacidade || fluxo > qtd_pessoas) {
                System.out.println("Inconsistente.");
                return;
            }

            rede_residual.addEdge(a, b, capacidade, fluxo);
        }

        int evacuados = rede_residual.nodes[origem].fluxo;
        
        // checar inconsistencia
        if (evacuados > qtd_pessoas || evacuados != -(rede_residual.nodes[destino].fluxo)) {
            System.out.println("Inconsistente.");
            return;
        }

        for (int i=1; i<=16000; i++) {
            if (i==origem || i==destino)
                continue;
            
            if (rede_residual.nodes[i].fluxo != 0) {
                System.out.println("Inconsistente.");
                return;
            }
        }
        
        System.out.print("Consistente. ");
        
        // é consistente; agora checar situação do plano
        if (evacuados == qtd_pessoas) {
            System.out.println("Grupo evacuado!");
            return;
        }


        int fluxo_max = rede_residual.fluxoMaximo(origem, destino, qtd_pessoas);
        // encontrar fluxo máximo
        System.out.print("Incompleto: " + evacuados + "/" + fluxo_max + ". ");

        if (fluxo_max == qtd_pessoas)
            System.out.println("Resgate possivel!");
        else
            System.out.println("Alerta, resgate impossivel!");
    }
}
