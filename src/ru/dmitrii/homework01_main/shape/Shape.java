package ru.dmitrii.homework01_main.shape;

public abstract class Shape {
    // абстрактный метод вычесления площади фигуры
    abstract double area ();

    public static void main(String[] args) {
        Triangle triangle = new Triangle(10, 15);
        Square square = new Square(5);
        Rect rect = new Rect(5, 10);
        Circle circle = new Circle(8);
        Shape[] array = {triangle, square, rect, circle};
        for (Shape shape: array) {
            System.out.println("Площадь фигуры " + shape.getClass().getSimpleName() + " равна: " + shape.area());
        }
    }
}

// Класс Треугольник наследуется от Фигуры
class Triangle extends Shape {
    private final int a, h;
    public Triangle(int a, int h) {
        this.a = a;
        this.h = h;
    }
    @Override
    double area() {
        return (double)(a*h)/2;
    }
}

// Класс Квадрат наследуется от Фигуры
class Square extends Shape {
    private final int a;
    public Square(int a) {
        this.a = a;
    }

    @Override
    double area() {
        return Math.pow(a,2);
    }
}

// Класс Прямоугольник наследуется от Фигуры
class Rect extends Shape {
    private final int a,b;
    public Rect(int a, int b) {
        this.a = a;
        this.b = b;
    }
    @Override
    double area() {
        return a*b;
    }
}

// Класс Круг наследуется от Фигуры
class Circle extends Shape {
    private final int radius;
    public Circle(int radius) {
        this.radius = radius;
    }
    @Override
    double area() {
        return (int) (Math.PI* Math.pow(radius,2));
    }
}
