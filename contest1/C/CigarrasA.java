package contest1.C;
import java.util.Scanner;

// O(S*N)
public class CigarrasA {
     
    private static int contains(int[] array, int size, int elem) {
        for (int i=0; i<size; i++) 
            if (elem == array[i]) return i;
        
        return -1;
    }


    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int path[] = new int[30];
        int i=0;

        int current = scan.nextInt();

        while (current != 0) {
            int index = contains(path, i, current);
            i = index == -1 ? i : index;
            path[i++] = current;
            current = scan.nextInt();
        }

        for (int j=0; j<i; j++) 
            System.out.println(path[j]);
    }
}