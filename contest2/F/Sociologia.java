package contest2.F;

import java.util.LinkedList;
import java.util.Scanner;
// Class that represents a node
class Node {
    public LinkedList<Integer> adj; // The list of outgoing edges (to adjacent nodes)
    public boolean visited;         // Has the node been visited in a graph traversal?
    public int group;              

    Node() {
        adj = new LinkedList<Integer>();
    }
}

// Class that represents a graph
class Graph {
    int n;           // Number of nodes of the graph
    Node nodes[];    // Array that will contain the nodes
    int qtd_grupos;
    int fora;
    // constructs a graph with n nodes and 0 edges
    Graph(int n) {
        this.n = n;
        this.qtd_grupos=0;
        this.fora=0;
        nodes  = new Node[n+1]; // +1 if the labels start at 1 instead of 0
        for (int i=1; i<=n; i++)
            nodes[i] = new Node();
    }
    
    // adds edge from a to b and another from b to a
    public void addEdge(int a, int b) {
        if (!isEdge(a, b)) {
            nodes[a].adj.add(b);
            // nodes[b].adj.add(a);
        }
    }

    // checks whether {a,b} is an edge
    public boolean isEdge(int a,int b) {
	return nodes[a].adj.contains(b);
    }

    // sets all nodes as not visited
    public void clearVisited() {
	for(int i=1; i<=n; i++)
	    nodes[i].visited = false;
    }

    public void preprocess() {
        for (int i=n; i>0; i--)
            bfs(i);
    }

    // --------------------------------------------------------------
    // Breadth-First Search (BFS) from node v: example implementation
    // --------------------------------------------------------------
    public void bfs(int v) {
        int tam_grupo = 0;
        LinkedList<Integer> q = new LinkedList<Integer>();   // queue
        // for (int i=1; i<=n; i++) nodes[i].visited = false;

        if (nodes[v].visited) return;

        q.add(v);
        nodes[v].visited = true;
	// System.out.println(v);
	    
        while (q.size() > 0) {
            int u = q.removeFirst();
            this.nodes[u].group = v;
            tam_grupo++;
            for (int w : nodes[u].adj)
                if (!nodes[w].visited) {
                    q.add(w);
                    nodes[w].visited  = true;
		    // System.out.println(w);
                }	    
        }

        if (tam_grupo >= 4) this.qtd_grupos++;
        else this.fora += tam_grupo;
    }

    // --------------------------------------------------------------
    // Depth-First Search (DFS) from node v: example implementation
    // --------------------------------------------------------------
    public void dfs(int v) {
        nodes[v].visited = true;
	System.out.println(v);
	    
	for (int w : nodes[v].adj)
	    if (!nodes[w].visited) 
		dfs(w);
    }
}

//  TERMINAR
public class Sociologia {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int qtd_cenarios = scan.nextInt();
        int qtd_alunos;
        int curr_aluno;
        int qtd_amigos;
        for (int i=1; i<=qtd_cenarios; i++) {
            qtd_alunos = scan.nextInt();
            Graph alunos = new Graph(qtd_alunos);
            for (int j=1; j<=qtd_alunos; j++) {
                curr_aluno = scan.nextInt();
                qtd_amigos = scan.nextInt();
                for (int k=1; k<=qtd_amigos; k++) 
                    alunos.addEdge(curr_aluno, scan.nextInt());  
                    // System.out.println(curr_aluno + " --> " + scan.nextInt());
            }
            alunos.preprocess();
            System.out.println("Caso #" + i);
            System.out.println(alunos.qtd_grupos + " " + alunos.fora);
        }
    }
}
