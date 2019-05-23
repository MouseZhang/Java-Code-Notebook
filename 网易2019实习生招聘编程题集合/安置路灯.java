import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();
        while(n --> 0) {
            int len = s.nextInt();
            String line = s.next();
            int count = 0;
            for(int i = 0; i < len; i++) {
                if(line.charAt(i) == 'X')
                    continue;
                else
                    count++;
                i += 2;
            }
            System.out.println(count);
        }
    }
}