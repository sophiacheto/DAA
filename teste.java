import java.util.Map;
import java.util.TreeMap;

public class teste {
    public static void main(String[] args) {
        // minuto = (minuto + minutos)%60;
        // hora += (.minuto + minutos)/60;

        Map<Integer, Integer> eventos = new TreeMap<>();
        eventos.put(11, 3);
        // System.out.println(eventos.get(11));
        eventos.put(12, eventos.getOrDefault(12,0)+1);
        System.out.println(eventos);
    }
    
}