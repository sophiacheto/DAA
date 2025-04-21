package contest5.A;
// package folha7.A;
// package folha7;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.TreeMap;


class Qnode {
	int vert;
	int vertkey;

	Qnode(int v, int key) {
		vert = v;
		vertkey = key;
	}
}

class HeapMax {
	private static int posinvalida = 0;
	int sizeMax, size;

	Qnode[] a;
	int[] pos_a;

	public HeapMax(int vec[], int n) {
		a = new Qnode[n + 1];
		pos_a = new int[n + 1];
		sizeMax = n;
		size = n;
		for (int i = 1; i <= n; i++) {
			a[i] = new Qnode(i, vec[i]);
			pos_a[i] = i;
		}

		for (int i = n / 2; i >= 1; i--)
			heapify(i);
	}

	boolean isEmpty() {
		if (size == 0)
			return true;
		return false;
	}

	public int extractMax() {
		int vertv = a[1].vert;
		swap(1, size);
		pos_a[vertv] = posinvalida; // assinala vertv como removido
		size--;
		heapify(1);
		return vertv;
	}

	public int[] extract() {
		int[] result = {a[1].vert, a[1].vertkey};
		swap(1, size);
		pos_a[result[0]] = posinvalida; // assinala vertv como removido
		size--;
		heapify(1);
		return result;
	}

	public void increaseKey(int vertv, int newkey) {

		int i = pos_a[vertv];
		a[i].vertkey = newkey;

		while (i > 1 && compare(i, parent(i)) > 0) {
			swap(i, parent(i));
			i = parent(i);
		}
	}

	void insert(int vertv, int key) {
		if (sizeMax == size)
			new Error("Heap is full\n");

		size++;
		a[size].vert = vertv;
		pos_a[vertv] = size; // supondo 1 <= vertv <= n
		increaseKey(vertv, key); // aumenta a chave e corrige posicao se necessario
	}

	void write_heap() {
		System.out.printf("Max size: %d\n", sizeMax);
		System.out.printf("Current size: %d\n", size);
		System.out.printf("(Vert,Key)\n---------\n");
		for (int i = 1; i <= size; i++)
			System.out.printf("(%d,%d)\n", a[i].vert, a[i].vertkey);

		System.out.printf("-------\n(Vert,PosVert)\n---------\n");

		for (int i = 1; i <= sizeMax; i++)
			if (pos_valida(pos_a[i]))
				System.out.printf("(%d,%d)\n", i, pos_a[i]);
	}

	private int parent(int i) {
		return i / 2;
	}

	private int left(int i) {
		return 2 * i;
	}

	private int right(int i) {
		return 2 * i + 1;
	}

	private int compare(int i, int j) {
		if (a[i].vertkey < a[j].vertkey)
			return -1;
		if (a[i].vertkey == a[j].vertkey)
			return 0;
		return 1;
	}

	private void heapify(int i) {
		int l, r, largest;

		l = left(i);
		if (l > size)
			l = i;

		r = right(i);
		if (r > size)
			r = i;

		largest = i;
		if (compare(l, largest) > 0)
			largest = l;
		if (compare(r, largest) > 0)
			largest = r;

		if (i != largest) {
			swap(i, largest);
			heapify(largest);
		}

	}

	private void swap(int i, int j) {
		Qnode aux;
		pos_a[a[i].vert] = j;
		pos_a[a[j].vert] = i;
		aux = a[i];
		a[i] = a[j];
		a[j] = aux;
	}

	private boolean pos_valida(int i) {
		return (i >= 1 && i <= size);
	}
}


class Edge {
    public int enode;
    public int assentos;
    public int inicio;
    // public int fim;

    Edge(int endv, int v) {
        enode = endv;
        assentos = v;
    }
}

// Class that represents a node
class Node {
    public LinkedList<Edge> adj; // The list of outgoing edges (to adjacent nodes)
    public boolean visited;         // Has the node been visited in a graph traversal?
    // public int assentos;
    // public int grau;              

    Node() {
        adj = new LinkedList<Edge>();
        // assentos = -1;
        // grau = 0;
        // instante = 0;
    }

    // public void atualizarAssentos(int nova_qtd, int max) {
    //     if (nova_qtd > max) 
    //         assentos = max;
    //     else 
    //         assentos = assentos > nova_qtd ? assentos : nova_qtd;
    // }
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
 
    // public void prepararGraus(int v) {
        
    // }

    public int[] find(int origem, int min, int max) {
        int[] capacidades = new int[n+1];

        for (int i=1; i<=n; i++) 
            capacidades[i] = 0;
         
        capacidades[origem] = max;
        HeapMax heap = new HeapMax(capacidades, n);

        
        while (!heap.isEmpty()) {
            int curr = heap.extractMax();
            // System.out.println(curr);

            if (capacidades[curr] == 0) break;
            nodes[curr].visited = true;

            for (Edge e : nodes[curr].adj) {
                int filho = e.enode;
                if (nodes[filho].visited) continue;

                int possivel_nova_capacidade = min(capacidades[curr], e.assentos);
                if (capacidades[filho] < possivel_nova_capacidade) {
                    capacidades[filho] = possivel_nova_capacidade;
                    heap.increaseKey(filho, possivel_nova_capacidade);
                }
            }   
        }  
        
        return capacidades;
    }

    private int min(int a, int b) {
        if (a<b)
            return a;
        return b;
    }
}


public class Viagens {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int num_min = scan.nextInt();
        int origem = scan.nextInt();
        int num_max = scan.nextInt();

        int qtd_nos = scan.nextInt();
        int qtd_lig = scan.nextInt();

        Graph rede = new Graph(qtd_nos);
        for (int i=0; i<qtd_lig; i++)
            rede.addEdge(scan.nextInt(), scan.nextInt(), scan.nextInt());

        int[] capacidades = rede.find(origem, num_min, num_max);

        boolean impossivel = true;
        for (int i=1; i<=qtd_nos; i++) 
            if (i!=origem && capacidades[i] >= num_min) {
                impossivel = false;
                System.out.println("Destino " + i + ": " + capacidades[i]);
            }
        
        if (impossivel)
            System.out.println("Impossivel");
        
    }
}
