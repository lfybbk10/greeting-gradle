package ru.mentee.power.collections.base;

import java.util.List;
import java.util.ArrayList;

public class NumberFilter {
    /**
     * Фильтрует список чисел, оставляя только четные.
     *
     * @param numbers Список целых чисел
     * @return Новый список, содержащий только четные числа из исходного списка
     */
    public static List<Integer> filterEvenNumbers(List<Integer> numbers) {
        if(numbers==null)
            return new ArrayList<>();

        List<Integer> result = new ArrayList<>(numbers);
        result.removeIf(n -> n == null || (n % 2 != 0));
        return result;
    }
}
