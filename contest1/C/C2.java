package contest1.C;
import java.util.Scanner;

public class C2 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int[] path = new int[10000];

        int curr = scan.nextInt();
        path[0] = curr;

        while (curr != 0) {
            path[curr] = scan.nextInt();
            curr = path[curr];
        }

        do {
            System.out.println(path[curr]);
            curr = path[curr];
        } while (path[curr]!=0);
    }
}
