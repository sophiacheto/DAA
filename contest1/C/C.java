package contest1.C;
import java.util.*;

public class C {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        List<Integer> rota = new ArrayList<>();
        int[] indices = new int[10000];

        int curr = scan.nextInt();
        while (curr != 0) {
            if (indices[curr] <= 0) {
                indices[curr]++;
                rota.add(curr);
            }
            else {
                while (rota.get(rota.size()-1) != curr) {
                    indices[rota.get(rota.size()-1)]--;
                    rota.remove(rota.size()-1);
                }
            }
            curr = scan.nextInt();
        }

        for (int num : rota)
            System.out.println(num);



    }
}
