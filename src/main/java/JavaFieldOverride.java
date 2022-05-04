public class JavaFieldOverride {
    public static void main(String[] args) {
        /**
         *总结：
         * 1.对于同一个对象，用父类的引用去取值(字段)，会取到父类字段的值
         * 2.用子类的引用去取值(字段)，则取到子类字段的值
         **/
        Sub s1 = new Sub();
        Super s2 = new Sub();
        System.out.println(s1.s); //输出sub
        System.out.println(((Super)s1).s); //输出super --通过强转，可以访问到父类被隐藏的字段
        System.out.println(s2.s); //输出super --通过父类引用，访问隐藏的字段
    }
}

class Super {
    String s = "super";
}

class Sub extends Super {
    String s = "sub";
}