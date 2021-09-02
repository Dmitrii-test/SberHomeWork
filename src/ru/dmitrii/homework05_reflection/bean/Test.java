package ru.dmitrii.homework05_reflection.bean;

public class Test {
    public static void main(String[] args) {
        A a = new A();
        B b = new B();
        a.setA(10);
        BeanUtils.assign(b, a);
        System.out.println("Обьект b поле а = "+b.getA());
    }
}

class A {
    int a;
    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }
}

class B {
    int a;

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }
}
