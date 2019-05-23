# Programming Problem

# 第一题：[牛牛找工作](https://github.com/MouseZhang/ProgrammingProblem/blob/master/网易2019实习生招聘编程题集合/牛牛找工作.java)

## 题目：
> 为了找到自己满意的工作，牛牛收集了每种工作的难度和报酬。牛牛选工作的标准是在难度不超过自身能力值的情况下，牛牛选择报酬最高的工作。在牛牛选定了自己的工作后，牛牛的小伙伴们来找牛牛帮忙选工作，牛牛依然使用自己的标准来帮助小伙伴们。牛牛的小伙伴太多了，于是他只好把这个任务交给了你。

## 输入描述：
> 每个输入包含一个测试用例。
> 每个测试用例的第一行包含两个正整数，分别表示工作的数量N (N\le100000)和小伙伴的数量M (M\le100000)。
> 接下来的N行每行包含两个正整数，分别表示该项工作的难度D_i (D_i\le1000000000)和报酬P_i(P_i\le1000000000)。
> 接下来的一行包含M个正整数，分别表示M个小伙伴的能力值A_i (A_i\le1000000000)。
> 保证不存在两项工作的报酬相同。


## 输出描述：
> 对于每个小伙伴，在单独的一行输出一个正整数表示他能得到的最高报酬。一个工作可以被多个人选择。

## 样例：
<pre>
<b>in:</b>
3 3 
1 100 
10 1000 
1000000000 1001 
9 10 1000000000

<b>out:</b>
100 
1000 
1001
</pre>

---

# 第二题：[被3整除](https://github.com/MouseZhang/ProgrammingProblem/blob/master/网易2019实习生招聘编程题集合/被3整除.java)

## 题目：
> 小Q得到一个神奇的数列: 1, 12, 123,...12345678910,1234567891011...。
> 并且小Q对于能否被3整除这个性质很感兴趣。
> 小Q现在希望你能帮他计算一下从数列的第l个到第r个(包含端点)有多少个数可以被3整除。

## 输入描述：
> 输入包括两个整数l和r (1 <= l <= r <= 10^9), 表示要求解的区间两端。


## 输出描述：
> 输出一个整数, 表示区间内能被3整除的数字个数。

## 样例：
<pre>
<b>in:</b>
2 5

<b>out:</b>
3
</pre>

---

# 第三题：[安置路灯](https://github.com/MouseZhang/ProgrammingProblem/blob/master/网易2019实习生招聘编程题集合/安置路灯.java)

## 题目：
> 小Q正在给一条长度为n的道路设计路灯安置方案。
> 为了让问题更简单,小Q把道路视为n个方格,需要照亮的地方用'.'表示, 不需要照亮的障碍物格子用'X'表示。
> 小Q现在要在道路上设置一些路灯, 对于安置在pos位置的路灯, 这盏路灯可以照亮pos - 1, pos, pos + 1这三个位置。
> 小Q希望能安置尽量少的路灯照亮所有'.'区域, 希望你能帮他计算一下最少需要多少盏路灯。



## 输入描述：
> 输入的第一行包含一个正整数t (1 \le t \le 1000), 表示测试用例数
> 接下来每两行一个测试数据, 第一行一个正整数n (1 \le n \le 1000),表示道路的长度。
> 第二行一个字符串s表示道路的构造,只包含'.'和'X'。


## 输出描述：
> 对于每个测试用例, 输出一个正整数表示最少需要多少盏路灯。

## 样例：
<pre>
<b>in:</b>
2
3
.X.
11
...XX....XX

<b>out:</b>
1
3
</pre>

---

# 第四题：[数对](https://github.com/MouseZhang/ProgrammingProblem/blob/master/网易2019实习生招聘编程题集合/数对.java)

## 题目：
> 牛牛以前在老师那里得到了一个正整数数对(x, y), 牛牛忘记他们具体是多少了。
> 但是牛牛记得老师告诉过他x和y均不大于n, 并且x除以y的余数大于等于k。
> 牛牛希望你能帮他计算一共有多少个可能的数对。




## 输入描述：
> 输入包括两个正整数n,k (1 \le n \le 10^5, 0 \le k \le n - 1)。

## 输出描述：
> 对于每个测试用例, 输出一个正整数表示可能的数对数量。

## 样例：
<pre>
<b>in:</b>
5 2

<b>out:</b>
7
</pre>

---

# 第五题：[矩形重叠](https://github.com/MouseZhang/ProgrammingProblem/blob/master/网易2019实习生招聘编程题集合/矩阵重叠.java)

## 题目：
> 平面内有n个矩形, 第i个矩形的左下角坐标为(x1[i], y1[i]), 右上角坐标为(x2[i], y2[i])。
> 如果两个或者多个矩形有公共区域则认为它们是相互重叠的(**不考虑边界和角落**)。
> 请你计算出平面内重叠矩形数量最多的地方,有多少个矩形相互重叠。


## 输入描述：
> 输入包括五行。
> 第一行包括一个整数n (2 \le n \le 50), 表示矩形的个数。
> 第二行包括n个整数x_1[i] (-10^9 \le x_1[i] \le 10^9),表示左下角的横坐标。
> 第三行包括n个整数y_1[i] (-10^9 \le y_1[i] \le 10^9),表示左下角的纵坐标。
> 第四行包括n个整数x_2[i] (-10^9 \le x_2[i] \le 10^9),表示右上角的横坐标。
> 第五行包括n个整数y_2[i] (-10^9 \le y_2[i] \le 10^9),表示右上角的纵坐标。


## 输出描述：
> 输出一个正整数, 表示最多的地方有多少个矩形相互重叠,如果矩形都不互相重叠,输出1。

## 样例：
<pre>
<b>in:</b>
2
0 90
0 90
100 200
100 200

<b>out:</b>
2
</pre>

---

# 第六题：[迷路的牛牛](https://github.com/MouseZhang/ProgrammingProblem/blob/master/网易2019实习生招聘编程题集合/迷路的牛牛.java)

## 题目：
> 牛牛去犇犇老师家补课，出门的时候面向北方，但是现在他迷路了。虽然他手里有一张地图，但是他需要知道自己面向哪个方向，请你帮帮他。 


## 输入描述：
> 每个输入包含一个测试用例。
> 每个测试用例的第一行包含一个正整数，表示转方向的次数N (N\le1000)。
> 接下来的一行包含一个长度为N的字符串，由L和R组成，L表示向左转，R表示向右转。



## 输出描述：
> 输出牛牛最后面向的方向，N表示北，S表示南，E表示东，W表示西。

## 样例：
<pre>
<b>in:</b>
3
LRR

<b>out:</b>
E
</pre>

---

# 第七题：[牛牛的闹钟](https://github.com/MouseZhang/ProgrammingProblem/blob/master/网易2019实习生招聘编程题集合/牛牛的闹钟.java)

## 题目：
> 牛牛总是睡过头，所以他定了很多闹钟，只有在闹钟响的时候他才会醒过来并且决定起不起床。从他起床算起他需要X分钟到达教室，上课时间为当天的A时B分，请问他最晚可以什么时间起床 


## 输入描述：
> 每个输入包含一个测试用例。
> 每个测试用例的第一行包含一个正整数，表示闹钟的数量N (N\le100)。
> 接下来的N行每行包含两个整数，表示这个闹钟响起的时间为H_i (0\le A<24)时M_i (0\le B<60)分。
> 接下来的一行包含一个整数，表示从起床算起他需要X (0\le X\le100)分钟到达教室。
> 接下来的一行包含两个整数，表示上课时间为A (0\le A<24)时B (0\le B<60)分。
> 数据保证至少有一个闹钟可以让牛牛及时到达教室。


## 输出描述：
> 输出两个整数表示牛牛最晚起床时间。

## 样例：
<pre>
<b>in:</b>
3 
5 0 
6 0 
7 0 
59 
6 59

<b>out:</b>
6 0
</pre>

---

# 第八题：[牛牛的背包问题](https://github.com/MouseZhang/ProgrammingProblem/blob/master/网易2019实习生招聘编程题集合/牛牛的背包问题.java)

## 题目：
> 牛牛准备参加学校组织的春游, 出发前牛牛准备往背包里装入一些零食, 牛牛的背包容量为w。
> 牛牛家里一共有n袋零食, 第i袋零食体积为v[i]。
> 牛牛想知道在总体积不超过背包容量的情况下,他一共有多少种零食放法(总体积为0也算一种放法)。


## 输入描述：
> 输入包括两行
> 第一行为两个正整数n和w (1 \le n \le 30, 1 \le w \le 2 * 10^9),表示零食的数量和背包的容量。
> 第二行n个正整数v[i] (0 \le v[i] \le 10^9),表示每袋零食的体积。


## 输出描述：
> 输出一个正整数, 表示牛牛一共有多少种零食放法。

## 样例：
<pre>
<b>in:</b>
3 10
1 2 4

<b>out:</b>
8
</pre>

---

# Java代码实现

网易的编程题主要考察二分、数学与优化程序的方法。

## 牛牛找工作

```Java
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
```

## 被3整除
```Java
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
```

## 安置路灯
```Java
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
```

## 迷路的牛牛
```Java
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
```

## 数对
```Java
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
```
## 矩形重叠
```Java
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
```

## 牛牛的闹钟
```Java
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
```

## 牛牛的背包问题
```Java
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
```
