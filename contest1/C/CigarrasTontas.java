package contest1.C;
import java.util.ArrayList;
import java.util.Scanner;

public class CigarrasTontas {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        ArrayList<Integer> locais = new ArrayList<>();
        int atual = scan.nextInt();

        while (atual != 0) {
            if (!locais.contains(atual)) 
                locais.add(atual);
            
            else 
                while (locais.get(locais.size() -1) != atual)
                    locais.remove(locais.size() - 1);
            
            atual = scan.nextInt();
        }

        for (int local : locais) 
            System.out.println(local);
        
        scan.close();
    }
}


