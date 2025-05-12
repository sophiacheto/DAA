// package contest5.D;

import java.util.LinkedList;
import java.util.Scanner;


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
    // public int peso;
    public int limite;
    public int inicio;
    // public int fim;

    Edge(int endv, int limit) {
        enode = endv;
        limite = limit;
    }

    // public void atualizar_limites(int lim) {
    //     limite = lim;
    // }
}

// Class that represents a node
class Node {
    public LinkedList<Edge> adj; // The list of outgoing edges (to adjacent nodes)
    public boolean visited;         // Has the node been visited in a graph traversal?
    public int pai; // public int peso;
    // public int assentos;
    // public int grau;              

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

    // public void new_node(int i) {
    //     if (nodes[i] == null)
    //         nodes[i] = new Node();
    // }
    
    // adds edge from a to b and another from b to a
    public void addEdge(int a, int b, int limit) {
        // new_node(b);
        // new_node(a);
        if (!isEdge(a, b)) {
            nodes[a].adj.add(new Edge(b,  limit));
            nodes[b].adj.add(new Edge(a, limit));
            // nodes[b].adj.add(a);
        }
    }
 
    // public void addLimitation(int a, int b, int limite) {
    //     for (Edge adj : nodes[a].adj)
    //         if (adj.enode == b)
    //             adj.atualizar_limites(limite);
    //     for (Edge adj : nodes[b].adj)
    //         if (adj.enode == a)
    //             adj.atualizar_limites(limite);
    // }

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
 
    public int find(int origem, int destino, int max) {
        int[] capacidades = new int[n+1];

        for (int i=1; i<=n; i++) 
            capacidades[i] = -1;
         
        capacidades[origem] = max;
        HeapMax heap = new HeapMax(capacidades, n);

        
        while (!heap.isEmpty()) {
            int curr = heap.extractMax();

            // System.out.println("PAI: " + curr + " --------");

            // já percorri todos os conexos e o nó seguinte é inalcançável
            if (capacidades[curr]==-1) break;
            nodes[curr].visited = true;

            for (Edge e : nodes[curr].adj) {
                int filho = e.enode;

                if (nodes[filho].visited) continue;

                // if (e.altura == -1 && e.largura == -1 && capacidades[filho] < capacidades[curr]) {
                //     capacidades[filho] = capacidades[curr];
                //     heap.increaseKey(filho, capacidades[curr]);
                //     continue;
                // }

                // System.out.println("No " + filho + ": (" + e.limite + ")");

                int possivel_nova_capacidade = min(capacidades[curr], e.limite);
                if (capacidades[filho] < possivel_nova_capacidade) {
                    capacidades[filho] = possivel_nova_capacidade;
                    heap.increaseKey(filho, possivel_nova_capacidade);
                }
            }   
        }  

        // int qtd=0;
        // for (int i=1; i<=n; i++)  {
        //     // nao alcançaveis
        //     if (capacidades[i] == -1)
        //         capacidades[i] = 0;
            
        //     // capacidade menor que o mínimo 
        //     if (capacidades[i] <= min)
        //         capacidades[i] = 0;

        //     // não printar origem, nem sem limitações, nem limitações dentro do esperado
        //     if (i!=origem && capacidades[i] < max) {
        //         System.out.println("No " + i + ": " + capacidades[i]);
        //         qtd++;
        //     }
        // }
        if (capacidades[destino] == -1)
            System.out.println(0);
        else if (capacidades[destino] >= max)
            System.out.println(max);
        else 
            System.out.println(capacidades[destino]);
        return 0;
    }

    public int min(int a, int b) {
        if (a<b)
            return a;
        return b;
    }
}

public class Encomenda {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int largura_min = scan.nextInt();
        int largura_max = scan.nextInt();
        int comprimento_min = scan.nextInt();
        int comprimento_max = scan.nextInt();
        int altura_min = scan.nextInt();

        int origem = scan.nextInt();
        int destino = scan.nextInt();

        Graph grafo = new Graph(1000);
        while (true) {
            int a = scan.nextInt();
            if (a == -1) break;
            int b = scan.nextInt();
            int largura = scan.nextInt();
            int comprimento = scan.nextInt();
            int altura = scan.nextInt();

            if (largura < largura_min || comprimento < comprimento_min || altura < altura_min) continue;

            grafo.addEdge(a, b, comprimento);
        }

        grafo.find(origem, destino, comprimento_max);


    }   
}
