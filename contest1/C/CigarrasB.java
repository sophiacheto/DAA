package contest1.C;
import java.util.Scanner;

public class CigarrasB {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int path[] = new int[10001];

        int current = scan.nextInt();
        path[0] = current;

        while (current != 0) {
            path[current] = scan.nextInt();
            current = path[current];
        }

  
        int index = 0;
        while (path[index]!=0) {
            System.out.println(path[index]);
            index = path[index];
        }

    }
}
