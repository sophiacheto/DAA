package contest1.D;

import java.util.Scanner;

public class D {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int num_tipos = scan.nextInt();
        int[] cadeiras_livres = new int[101];
        
        for (int i=0; i<num_tipos; i++) 
            cadeiras_livres[scan.nextInt()] = scan.nextInt();

        int num_habitantes = scan.nextInt();
        int sentados = 0;

        for (int j=0; j<num_habitantes; j++) {
            int qtd_preferencias = scan.nextInt();
            int k;
            boolean escolhido = false;
            for (k=0; k<qtd_preferencias; k++) {
                int curr_opcao = scan.nextInt();
                if (!escolhido && cadeiras_livres[curr_opcao] != 0) {
                    cadeiras_livres[curr_opcao]--;
                    sentados++;
                    escolhido = true;
                }
            }
        }

        System.out.println();
        System.out.println(num_habitantes-sentados);
        
        int qtd_cadeiras_livres=0;
        for (int i=0; i<num_tipos; i++) 
            qtd_cadeiras_livres += cadeiras_livres[i];
        
        System.out.println(qtd_cadeiras_livres);
    }
}
