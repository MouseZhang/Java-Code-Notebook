import java.util.Scanner;
 
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();
        if (k == 0) {
            System.out.println((long)n * n);
        }
        else {
            long count = 0;
            for (int y = k + 1; y <= n; y++) {
                int d = y - k;
                for (int x = k; x <= n; x+=y) {
                    count += Math.min(n - x + 1, d);
                }
            }
            System.out.println(count);
        }
    }
}