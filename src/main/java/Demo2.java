import java.io.*;
import java.util.*;

public class Demo2 {
    public static void main(String[] args) throws IOException {
        withArraysAPI();
    }

    // 方法一: 调用API Arrays.sort
    public static void withArraysAPI() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        String[] ss = new String[n];
        for (int i = 0; i < n; i++) {
            ss[i] = br.readLine();
        }
        br.close();

        Arrays.sort(ss);

        for(int i = 0; i < n; i++) {
            System.out.println(ss[i]);
        }
    }

    // 方法二: 使用PriorityQueue
    public static void withPriorityQueue() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        PriorityQueue<String> pq = new PriorityQueue<>();
        String s = "";
        while ((s = br.readLine()) != null) {
            pq.offer(s);
        }
        br.close();

        while (!pq.isEmpty()) {
            System.out.println(pq.poll());
        }
    }

    // 方法三: 使用list并自己实现Comparator, 比较能体现算法的思路
    public static void withComparator() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        List<String> list = new ArrayList<>();
        String s = "";
        while ((s = br.readLine()) != null) {
            list.add(s);
        }
        br.close();

        list.sort(new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                int i = 0;
                while (i < s1.length() && i < s2.length()) {
                    if (s1.charAt(i) != s2.charAt(i)) {
                        return (s1.charAt(i) > s2.charAt(i))? 1: -1;
                    }
                    i++;
                }
                if (s1.length() == s2.length()) {
                    return 0;
                } else {
                    return (s1.length() > s2.length())? 1: -1;
                }
            }
        });

        for(int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }
}