import java.util.Arrays;
import java.util.Scanner;
  
public class Main {
    static int ans = 0;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int w = sc.nextInt();
        int[] v = new int[n];
        long arrSum = 0;
        for (int i = 0; i < n; i++) {
            v[i] = sc.nextInt();
            arrSum += v[i];
        }
        if (arrSum <= w) {
            System.out.println((int) Math.pow(2, n));//进一步缩短时间，提升程序效率
        } else {
            long sum = 0;//和可能非常大，要用long
            helper(v, w, 0, sum);
            System.out.println(ans + 1);//最后加上1，是全部都不放的情况
        }
    }
 
    public static void helper(int[] v, int w, int i, long sum) {
        if (i < v.length) {//约束递归出口
            if (sum > w) {
                return;
            }
            helper(v, w, i + 1, sum);//不放当前的零食
            if (sum + v[i] <= w) {//放当前的零食
                ans++;
                helper(v, w, i + 1, sum + v[i]);
            }
        }
    }
}