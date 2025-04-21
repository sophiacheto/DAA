import java.util.Scanner;

public class A {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int qtd_pessoas = scan.nextInt();
        int[] corrente = new int[qtd_pessoas+1];

        int fora=0;
        int i;

        // passar todas as pessoas para o array
        for (i=1; i<=qtd_pessoas; i++) 
            corrente[i] = scan.nextInt();
        
        
        for (i=1; i<=qtd_pessoas; i++) {
            // ja está num grupo
            if (corrente[i] == 0) continue;
            int qtd=1;
            int curr = i;
            int max = 0;

            // percorrer e encontrar o maior valor do grupo do elemento
            while (corrente[curr] != i) {
                curr = corrente[curr];
                qtd++;
                if (curr > max) max = curr;
            }


            if (qtd<3) {
                fora+=qtd;
                for (int j=0; j<qtd; j++) {
                    int next = corrente[curr];
                    corrente[curr] = 0;
                    curr=next;
                }
            }
            else {
                System.out.print(qtd + " ");

                // percorrer os indices até chegar no máximo
                while (curr != max) 
                    curr = corrente[curr];
                

                while (corrente[curr] != max) {
                    System.out.print(curr + " ");
                    int next = corrente[curr];
                    corrente[curr] = 0;
                    curr = next;
                } 

                System.out.println(curr);
                corrente[curr] = 0;
            }
        }

        System.out.println(fora);
    }
}