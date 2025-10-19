package ru.mentee.power.methods.recursion;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс с рекурсивными методами для решения различных задач
 */
public class RecursionExercises {

    /**
     * Вычисляет факториал числа n
     *
     * @param n Положительное целое число
     * @return Факториал числа n
     * @throws IllegalArgumentException если n < 0
     */
    public static long factorial(int n) {
        if (n < 0) throw new IllegalArgumentException("n must be non-negative");
        if (n == 0 || n == 1) return 1;
        return n * factorial(n - 1);
    }

    /**
     * Вычисляет n-ое число в последовательности Фибоначчи
     *
     * @param n Позиция в последовательности Фибоначчи (0-based)
     * @return Число Фибоначчи на позиции n
     * @throws IllegalArgumentException если n < 0
     */
    public static long fibonacci(int n) {
        if (n < 0) throw new IllegalArgumentException("n must be non-negative");
        if (n == 0) return 0;
        if (n == 1) return 1;
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    /**
     * Оптимизированный метод для вычисления n-ого числа Фибоначчи
     * (использует мемоизацию)
     */
    public static long fibonacciOptimized(int n) {
        if (n < 0) throw new IllegalArgumentException("n must be non-negative");
        return fibonacciMemo(n, new HashMap<>());
    }

    private static long fibonacciMemo(int n, Map<Integer, Long> memo) {
        if (memo.containsKey(n)) return memo.get(n);
        long result;
        if (n == 0) result = 0;
        else if (n == 1) result = 1;
        else result = fibonacciMemo(n - 1, memo) + fibonacciMemo(n - 2, memo);
        memo.put(n, result);
        return result;
    }

    /**
     * Проверяет, является ли строка палиндромом
     *
     * @param str Исходная строка
     * @return true, если строка является палиндромом, иначе false
     */
    public static boolean isPalindrome(String str) {
        if (str == null) return false;
        str = str.replaceAll("\\s+", "").toLowerCase(); // игнор пробелы и регистр
        return isPalindromeHelper(str, 0, str.length() - 1);
    }

    private static boolean isPalindromeHelper(String s, int left, int right) {
        if (left >= right) return true;
        if (s.charAt(left) != s.charAt(right)) return false;
        return isPalindromeHelper(s, left + 1, right - 1);
    }

    /**
     * Вычисляет сумму цифр числа
     */
    public static int sumOfDigits(int n) {
        n = Math.abs(n);
        if (n < 10) return n;
        return (n % 10) + sumOfDigits(n / 10);
    }

    /**
     * Возводит число в степень
     */
    public static double power(double base, int exponent) {
        if (exponent == 0) return 1;
        if (exponent < 0) return 1 / power(base, -exponent);
        return base * power(base, exponent - 1);
    }

    /**
     * Находит наибольший общий делитель двух чисел (алгоритм Евклида)
     */
    public static int gcd(int a, int b) {
        if (b == 0) return Math.abs(a);
        return gcd(b, a % b);
    }

    /**
     * Обращает порядок элементов в массиве (рекурсивно)
     */
    public static void reverseArray(int[] array, int start, int end) {
        if (array == null || start >= end) return;
        int temp = array[start];
        array[start] = array[end];
        array[end] = temp;
        reverseArray(array, start + 1, end - 1);
    }
}
