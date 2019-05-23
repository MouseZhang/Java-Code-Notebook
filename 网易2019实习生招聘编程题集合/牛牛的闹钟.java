import java.util.*;
 
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] clock = new int[n];
        for (int i = 0; i < n; i++) {
            clock[i] = sc.nextInt() * 60 + sc.nextInt();
        }
        int min = sc.nextInt();
        int earlyTime = sc.nextInt() * 60 + sc.nextInt() - min;
        Arrays.sort(clock);
        for (int i = clock.length - 1; i >= 0; i--) {
            if (clock[i] <= earlyTime) {
                System.out.println(clock[i] / 60 + " " + clock[i] % 60);
                break;
            }
        }
    }
}