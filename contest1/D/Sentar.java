package contest1.D;

import java.util.Scanner;

public class Sentar {
    public static void main(String[] args) {
        int sentados = 0;
        int cadeiras_livres = 0;
        Scanner scan = new Scanner(System.in);
        int estoque [] = new int[101];

        int num_tipos = scan.nextInt();
        int tipo;
        int qtd;
        // estoque[i] vai ter a qtd de cadeiras do tipo i
        for (int i=0; i<num_tipos; i++) {
            tipo = scan.nextInt();
            qtd = scan.nextInt();
            estoque[tipo] = qtd;
            cadeiras_livres += qtd;
        }

        int num_habitantes = scan.nextInt();

        int qtd_opcoes;
        int opcao;
        int escolhido;
        for (int j=0; j<num_habitantes; j++) {
            escolhido = 0;
            qtd_opcoes = scan.nextInt();
            for (int k=0; k<qtd_opcoes; k++) {
                opcao = scan.nextInt();
                if (escolhido == 1)
                    continue;
                else if (estoque[opcao] != 0) {
                    estoque[opcao]--;
                    cadeiras_livres--;
                    sentados++;
                    escolhido=1;
                }
            }
        }

        System.out.println(num_habitantes-sentados);
        System.out.println(cadeiras_livres);
        
    }
}
