public class JavaDynamicBind {
    public static void main(String[] args) {
        /**
         *java动态绑定机制：
         * 将一个子类对象地址，交给一个父类的引用：
         * 1.如果调用的是方法，则jvm机会将该方法和和对象的内存地址绑定
         * 2.如果调用的是属性，则没有动态绑定机制，在哪里调用，就返回对应值
         **/
        A a = new B();
        System.out.println(a.sum()); //输出20+10=30
        System.out.println(a.sum1()); //输出10+10=20
    }
}

class A {
    public int i = 10;

    public int sum() {
        return getI() + 10;
    }

    public int sum1() {
        return i + 10;
    }

    public int getI() {
        return i;
    }
}

class B extends A {
    public int i = 20;

//    public int sum() {
//        return i + 20;
//    }

    public int getI() {
        return i;
    }

//    public int sum1() {
//        return i + 10;
//    }
}
