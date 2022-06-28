import java.util.HashMap;

public class HashMapV1 {
    public static void main(String[] args) {

        /**
         * 极限情况，就是向HashMap中增加的数据都会在一个链表上
         * 这就要保证数据在底层数组的同一个位置
         * 极限情况下，放置11条数据会变为红黑二叉树
         * 如果大量的数据都放置在一个链表上，需要考虑是不是集合的容量不够，进行扩容
         * HashMap底层计算元素的存储位置：hash(key.hashCode) & (length-1)
         * 如果能够保证容量为2的N次幂，那么减去1后，二进制的每一位都是1
         * 这样去和key的hash值进行&位运算时，得到的位置索引index才可能均匀的分布。
         *   0000 0000 0011 1010  --hash值，大小不确定性
         * & 0000 0000 0001 1111  --数组容量假设为2的N次幂，那么减去1后，二进制的每一位都是1，数值确定性
         * ----------------------
         *   0000 0000 0001 1010  --&位运算时，得到的位置索引index会均匀的分布
         *
         **/

        HashMap<User, User> map = new HashMap<>();

        for (int i = 0; i < 20; i++) {
            map.put(new User(), new User());
            System.out.println(i);
        }
    }
}

class User {
    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
