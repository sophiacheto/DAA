package contest1.B;

import java.util.Scanner;

class Pair {
    int first;
    int second;

    Pair(int first, int second) {
        this.first = first;
        this.second = second;
    }
}


public class Rotas {
    public static Pair findStats(Scanner scan, int qtd_locais, int tam_grupo, int origem, int destino) {
        int problemas=0;
        int assentos=10000;
        
        int curr_local=0;
        int curr_problemas=0;
        int curr_assentos=0;
    
        // System.out.println("entrou");
        // for (int j=0; j<qtd_locais; j++)
        int j=0;
        while (j<qtd_locais) {
            // System.out.println("entrou");
            j++;
            curr_local = scan.nextInt();

            // caso em que a origem não existe na rota
            if (j==qtd_locais)
                break;
            problemas += curr_problemas;
            curr_problemas = scan.nextInt();
            curr_assentos = scan.nextInt();
             
            // System.out.println(curr_local + " " + curr_problemas + " " + curr_assentos);
            if (curr_local == origem) {
                assentos = curr_assentos;
                break;
            }
        }

        while (j<qtd_locais) 
        // while (true) {
        {
            // caso em que a rota não comporta a qtd de pessoas do grupo
            if (curr_assentos < tam_grupo) {
                // System.out.println("COMIDA2 (nao cabe): " + scan.nextLine());
                scan.nextLine();
                return new Pair(0,0);
            }

            // mantem a menor qtd de assentos no trajeto
            if (curr_assentos < assentos)
                assentos = curr_assentos;
            
            problemas += curr_problemas;
            curr_local = scan.nextInt();

            // chegou ao destino
            if (curr_local == destino) {
                if (j!=qtd_locais-1)
                // System.out.println("aa");
                scan.nextLine();
                return new Pair(assentos, problemas);
            }
            // if (j==qtd_locais-1) {
            //     System.out.println("COMIDA3: " + scan.nextLine());
            //     break;
            // }

            // 
            if (j==qtd_locais-1) {
                break;
            }

            curr_problemas = scan.nextInt();
            curr_assentos = scan.nextInt();
            j++;
        // return assentos, problemas
        }

        // if (j==qtd_locais) {
        //     System.out.println(curr_local);
        //     System.out.println("j==qtd_locais");
        //     if (curr_local == destino) 
        //         return new Pair(assentos, problemas);
        // }
        // else
        //     System.out.println("suposta nova linha aqui " + scan.nextLine());

        // System.out.println("curr local: " + curr_local);

    return new Pair(0, 0);
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int num_rotas = scan.nextInt();
        int tam_grupo = scan.nextInt();
        int origem = scan.nextInt();
        int destino = scan.nextInt();

        int rota_escolhida = 0;
        int problemas = 100;
        int assentos = 0;
        int qtd_locais;

        int curr_problemas;
        int curr_assentos;
        Pair curr_simulacao;
    
        // System.out.println("AAAAAAA " + num_rotas);
        for (int i=0; i<num_rotas; i++) {
            qtd_locais = scan.nextInt();
            // System.out.println("\nQTD LOCAIS: " + qtd_locais);
            curr_simulacao = findStats(scan, qtd_locais, tam_grupo, origem, destino);
            curr_assentos = curr_simulacao.first;
            curr_problemas = curr_simulacao.second;

            // System.out.println(curr_assentos + " " + curr_problemas);
            if (curr_assentos == 0)  // simulacao não é válida
                continue;
            if (curr_problemas < problemas) {
                rota_escolhida = i+1;
                assentos = curr_assentos;
                problemas = curr_problemas;
                continue;
            }
            if (curr_problemas == problemas && curr_assentos > assentos) {
                rota_escolhida = i+1;
                assentos = curr_assentos;
                problemas = curr_problemas;
            }
        }
        if (assentos == 0)
            System.out.println("Impossivel");
        else 
            System.out.println("Rota = " + rota_escolhida + " Probs = " + problemas + " Lugares = " + assentos);
    }
}

