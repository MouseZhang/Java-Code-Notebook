import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String location = "NESW";
        int n = sc.nextInt();
        int count = 0;
        sc.nextLine();
        String s = sc.next();
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == 'L') {
                count += 3;
            }
            else {
                count++;
            }
        }
        System.out.println(location.charAt(count % 4));
    }
}