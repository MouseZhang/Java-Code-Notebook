import java.util.Scanner;
  
public class Main {
    public static void main(String[] args) {
                Scanner sc = new Scanner(System.in); 
                int n = sc.nextInt(); 
                int[] x1 = new int[n]; 
                int[] y1 = new int[n]; 
                int[] x2 = new int[n]; 
                int[] y2 = new int[n]; 
                for(int i = 0; i < n; i++) 
                { 
                    x1[i] = sc.nextInt(); 
                } 
                for(int i = 0; i < n; i++) 
                { 
                    y1[i] = sc.nextInt(); 
                } 
                for(int i = 0; i < n; i++) 
                { 
                    x2[i] = sc.nextInt(); 
                } 
                for(int i = 0; i < n; i++) 
                { 
                    y2[i] = sc.nextInt(); 
                } 
                int result = 1;
                for (int i = 0; i < n; i++) {
                    int ans = solve(x1, y1, x2, y2, 0, x1[i], y1[i], x2[i], y2[i]);
                    result = Math.max(result, ans);
                }
        System.out.println(result);
    }
  
    private static int solve(int[] x1, int[] y1, int[] x2, int[] y2, int k,
            int xx1, int yy1, int xx2, int yy2) {
        if(x1.length == k){
            return 0;
        } else {
            if(x1[k] >= xx2 || x2[k] <= xx1 || y1[k] >= yy2 || y2[k] <= yy1)
            return solve(x1, y1, x2, y2, k+1, xx1, yy1, xx2, yy2);
            else {
                int maxx1 = Math.max(xx1, x1[k]);
                int maxy1 = Math.max(yy1, y1[k]);
                int minx2 = Math.min(xx2, x2[k]);
                int miny2 = Math.min(yy2, y2[k]);
                return Math.max(solve(x1, y1, x2, y2, k + 1, xx1, yy1, xx2, yy2), solve(x1, y1, x2, y2, k + 1, maxx1, maxy1, minx2, miny2) + 1);
            }
        }
    }
}