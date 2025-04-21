package contest1.E;

import java.util.Scanner;

class Hora {
    int hora;
    int minuto;

    Hora(int hora, int minuto) {
        // System.out.println("Hora: "+ hora +" Minuto: " + minuto);
        this.hora = hora;
        this.minuto = minuto;
    }

    public void somar(int minutos) {
        this.hora += (this.minuto + minutos)/60;
        this.minuto = (this.minuto + minutos)%60;
        // return this;
    }

    public boolean menor(Hora hora) {
        if (this.hora < hora.hora)
            return true;
        else if (this.hora > hora.hora)
            return false;
        else
            return this.minuto < hora.minuto;
    }
    
    public Hora maxima(Hora hora) {
        if (this.menor(hora))
            return new Hora(hora.hora, hora.minuto);
        else
            return this;
    }

    // @Override
    // public String toString() {
    //     String str = hora + ":" + minuto;
    //     return str;
    // }
}


public class Barbearia {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int clientes_perdidos = 0;

        int qtd_clientes = scan.nextInt();
        Hora [] clientes = new Hora[qtd_clientes];

        if (qtd_clientes > 0) {
            clientes[0] = new Hora(scan.nextInt(), scan.nextInt());
            clientes[0].somar(scan.nextInt());
        }
               
        Hora chegada;
        int tempo_atendimento;
        int i=1;
        for (int j=1; j<qtd_clientes; j++) {
            chegada = new Hora(scan.nextInt(), scan.nextInt());
            // System.out.println(chegada);
            tempo_atendimento = scan.nextInt();

            // Fora do horário de atendimento
            if (chegada.hora < 9 || chegada.hora > 19 || (chegada.hora > 12  && chegada.hora < 15) || ((chegada.hora == 12 || chegada.hora == 19) && chegada.minuto != 0))
                // System.out.println("Perdido pq ta fechado: " + chegada);
                clientes_perdidos++;

            // se cheguei antes do "mais antigo" terminar de ser atendido, vou embora
            else if (i>=4 && chegada.menor(clientes[i-4]))
                // System.out.println("Perdido pq ta cheio: " + chegada);
                clientes_perdidos++;
            else {
            // se posso ser atendido, meu atendimento irá começar quando o último (mais recente) terminar de ser atendido
            clientes[i] = chegada.maxima(clientes[i-1]);
            clientes[i].somar(tempo_atendimento);
            i++;
            }
        }

        System.out.println("Perdeu = " + clientes_perdidos);    
    }
    
}
