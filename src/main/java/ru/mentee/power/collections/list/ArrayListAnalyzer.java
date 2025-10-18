package ru.mentee.power.collections.list;

import java.util.*;

public class ArrayListAnalyzer {

    /**
     * Фильтрует список строк, оставляя только те, которые начинаются с указанного префикса
     *
     * @param strings список строк для фильтрации
     * @param prefix  префикс для фильтрации
     * @return новый список, содержащий только строки с указанным префиксом
     * @throws IllegalArgumentException если strings или prefix равны null
     */
    public static List<String> filterByPrefix(List<String> strings, String prefix) {
        if (strings == null || prefix == null)
            throw new IllegalArgumentException("strings and prefix cannot be null");


        if (strings instanceof LinkedList) {
            List<String> result = new LinkedList<>();

            Iterator<String> iterator = strings.iterator();
            while (iterator.hasNext()) {
                String current = iterator.next();
                if (current.startsWith(prefix)) {
                    result.add(current);
                }
            }

            return result;
        } else {
            List<String> result = new ArrayList<>(strings.size() / 2);

            for (int i = 0; i < strings.size(); i++) {
                if (strings.get(i).startsWith(prefix)) {
                    result.add(strings.get(i));
                }
            }

            return result;
        }
    }

    /**
     * Находит максимальный элемент в списке
     *
     * @param numbers список чисел
     * @return максимальное число из списка
     * @throws IllegalArgumentException если список пуст или равен null
     */
    public static Integer findMax(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty())
            throw new IllegalArgumentException("numbers cannot be null");

        int max = Integer.MIN_VALUE;
        if (numbers instanceof LinkedList) {
            Iterator<Integer> iterator = numbers.iterator();
            while (iterator.hasNext()) {
                Integer current = iterator.next();
                if (current > max)
                    max = current;
            }
        } else {
            for (Integer number : numbers) {
                if (number > max)
                    max = number;
            }
        }
        return max;
    }

    /**
     * Разбивает список на части указанного размера
     *
     * @param list     исходный список
     * @param partSize размер каждой части
     * @return список списков, где каждый внутренний список имеет размер не более partSize
     * @throws IllegalArgumentException если list равен null или partSize <= 0
     */
    public static <T> List<List<T>> partition(List<T> list, int partSize) {
        if (list == null || partSize <= 0)
            throw new IllegalArgumentException("list cannot be null or part size <= 0");

        List<List<T>> result = new ArrayList<>();
        int currentPartIndex = 0;
        List<T> currentPart = new ArrayList<>();
        if (list instanceof LinkedList) {
            Iterator<T> iterator = list.iterator();
            while (iterator.hasNext()) {
                T next = iterator.next();
                currentPart.add(next);
                currentPartIndex++;
                if (currentPartIndex == partSize) {
                    result.add(currentPart);
                    currentPart = new ArrayList<>();
                    currentPartIndex = 0;
                }
            }
            if (!currentPart.isEmpty()) {
                result.add(currentPart);
            }
        } else {
            for (T element : list) {
                currentPart.add(element);
                currentPartIndex++;
                if (currentPartIndex == partSize) {
                    result.add(currentPart);
                    currentPart = new ArrayList<>();
                    currentPartIndex = 0;
                }
            }
            if (!currentPart.isEmpty()) {
                result.add(currentPart);
            }
        }
        return result;
    }

    /**
     * Проверяет, является ли список палиндромом
     * (читается одинаково в обоих направлениях)
     *
     * @param list список для проверки
     * @return true, если список является палиндромом, иначе false
     * @throws IllegalArgumentException если list равен null
     */
    public static <T> boolean isPalindrome(List<T> list) {
        if (list == null) {
            throw new IllegalArgumentException("list cannot be null");
        }
        if (list.size() % 2 != 0) {
            return false;
        }

        if (list instanceof LinkedList) {
            LinkedList linkedList = (LinkedList) list;

            Iterator<T> iterator = linkedList.iterator();
            Iterator<T> descendingIterator = linkedList.descendingIterator();
            while (iterator.hasNext() && descendingIterator.hasNext()) {
                if (!iterator.next().equals(descendingIterator.next())) {
                    return false;
                }
            }
            return true;
        } else {
            for (int i = 0; i < list.size() / 2; i++) {
                if (!list.get(i).equals(list.get(list.size() - 1 - i))) {
                    return false;
                }
            }
            return true;
        }
    }
}