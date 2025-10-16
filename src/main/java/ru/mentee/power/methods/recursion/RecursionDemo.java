package ru.mentee.power.methods.recursion;

import java.util.Arrays;

public class RecursionDemo {
    public static void main(String[] args) {
        System.out.println("=== Факториал ===");
        System.out.println("5! = " + RecursionExercises.factorial(5));

        System.out.println("\n=== Числа Фибоначчи ===");
        System.out.println("Наивная реализация:");
        for (int i = 0; i <= 10; i++) {
            System.out.print(RecursionExercises.fibonacci(i) + " ");
        }

        System.out.println("\nОптимизированная реализация:");
        for (int i = 0; i <= 10; i++) {
            System.out.print(RecursionExercises.fibonacciOptimized(i) + " ");
        }

        System.out.println("\n\n=== Проверка палиндрома ===");
        String[] testStrings = {"level", "Step on no pets", "hello", "А роза упала на лапу Азора"};
        for (String s : testStrings) {
            boolean result = RecursionExercises.isPalindrome(s);
            System.out.println("\"" + s + "\" → " + (result ? "палиндром" : "не палиндром"));
        }

        System.out.println("\n=== Сумма цифр ===");
        int number = 12345;
        System.out.println("Сумма цифр числа " + number + " = " + RecursionExercises.sumOfDigits(number));

        System.out.println("\n=== Возведение в степень ===");
        System.out.println("2^10 = " + RecursionExercises.power(2, 10));
        System.out.println("2^-3 = " + RecursionExercises.power(2, -3));

        System.out.println("\n=== Наибольший общий делитель ===");
        System.out.println("НОД(48, 36) = " + RecursionExercises.gcd(48, 36));

        System.out.println("\n=== Обращение массива ===");
        int[] array = {1, 2, 3, 4, 5};
        System.out.println("Исходный массив: " + Arrays.toString(array));
        RecursionExercises.reverseArray(array, 0, array.length - 1);
        System.out.println("Обращенный массив: " + Arrays.toString(array));
    }
}
