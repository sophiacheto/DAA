package contest1.B;

import java.util.Scanner;

public class B {

    static int[] analisar(int[] rota, int tam, int num_elem, int origem, int destino) {
        int[] resultado = {0,0};
        // int total_problemas = resultado[0]; 
        // int total_assentos = resultado[1];
        int i=0;
        
        // procura se passa pela origem
        while (i<tam-1) {
            int curr_inicio = rota[i++];
            resultado[0] += rota[i++];
            int curr_assentos = rota[i++];
            // int curr_fim = rota[i+3];
            if (curr_inicio == origem) {
                resultado[1] = curr_assentos;
                break;
            }
        }

        // nao tenho assentos suficientes 
        // ou já cheguei ao destino 
        if (resultado[1] < num_elem
            || rota[i++] == destino) 
            return resultado;
        
        // só entra se achar a origem
        while (i<tam) {
            int curr_problemas = rota[i++];
            int curr_assentos = rota[i++];
            int possivel_fim = rota[i++];

            resultado[0] += curr_problemas;
            
            if (curr_assentos < resultado[1]) {
                resultado[1] = curr_assentos;
                if (curr_assentos < num_elem)
                    return resultado;
            }

            if (possivel_fim==destino)
                return resultado;
        }

        resultado[1] = 0;
        return resultado;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int num_rotas = scan.nextInt();
        int num_elem = scan.nextInt();
        int origem = scan.nextInt();
        int destino = scan.nextInt();

        int rota_escolhida=0;
        int lugares_escolhida = 0;
        int problemas_escolhida = 100;

        for (int i=1; i<=num_rotas; i++) {
            int num_paradas = scan.nextInt();
            int[] rota = new int[num_paradas*3-2];
            for (int j=0; j<num_paradas*3-2; j++) {
                rota[j] = scan.nextInt();
            }
            int[] result = analisar(rota, num_paradas*3-2, num_elem, origem, destino);
            int curr_problemas = result[0];
            int curr_lugares = result[1];
            
            // eliminar rotas impossíveis
            if (curr_lugares < num_elem) continue;

            if (curr_problemas < problemas_escolhida ||
                curr_problemas == problemas_escolhida && curr_lugares > lugares_escolhida) 
            {
                rota_escolhida = i;
                problemas_escolhida = curr_problemas;
                lugares_escolhida = curr_lugares;
            }
            // System.out.println(Arrays.toString(rota));
        }

        if (problemas_escolhida == 100)
            System.out.println("Impossivel");
        else
            System.out.println("Rota = " + rota_escolhida + " Probs = " + problemas_escolhida + " Lugares = " + lugares_escolhida);
    }
}
