import java.util.Scanner;
 
public class Main {
    public static void main(String[] args) {
        int num = 0;
        Scanner sc = new Scanner(System.in);
        int l = sc.nextInt();
        int r = sc.nextInt();
        for (int i = l; i <= r; i++) {
            if (i % 3 == 0 || i % 3 == 2) {
                num++;
            }
        }
        System.out.println(num);
    }
}