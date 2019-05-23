import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int t1 = 0;
        int t2 = 0;
        HashMap hashMap = new HashMap();
        int[] a = new int[n + m];
        int[] b = new int[m];
        for (int i = 0; i < n; i++) {
            t1 = sc.nextInt();
            t2 = sc.nextInt();
            a[i] = t1;
            hashMap.put(t1, t2);
        }
        for (int i = 0; i < m; i++) {
            t1 = sc.nextInt();
            a[n + i] = t1;
            b[i] = t1;
            if (!hashMap.containsKey(t1)) {
                hashMap.put(t1, 0);
            }
        }
        Arrays.sort(a);
        int max = 0;
        for (int i = 0; i < n + m; i++) {
            max = Math.max(max, (int)hashMap.get(a[i]));
            hashMap.put(a[i], max);
        }
        for (int i = 0; i < m; i++) {
            System.out.println(hashMap.get(b[i]));
        }
        }
    }