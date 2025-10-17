package ru.mentee.power.collections.base;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

public class CollectionAnalyzer {
    /**
     * Находит все строки в коллекции, длина которых больше заданной.
     *
     * @param strings Коллекция строк
     * @param minLength Минимальная длина
     * @return Список строк, длина которых больше minLength
     */
    public static List<String> findLongStrings(Collection<String> strings, int minLength) {
        if(strings==null) {
            return new ArrayList<>();
        }

        List<String> result = new ArrayList<>();
        for (String string : strings) {
            if (string != null && string.length() > minLength) {
                result.add(string);
            }
        }

        return result;
    }

    /**
     * Подсчитывает количество элементов в коллекции, удовлетворяющих условию:
     * сумма цифр числа больше заданного значения.
     *
     * @param numbers Коллекция целых чисел
     * @param threshold Пороговое значение для суммы цифр
     * @return Количество чисел, сумма цифр которых больше threshold
     */
    public static int countNumbersWithDigitSumGreaterThan(Collection<Integer> numbers, int threshold) {
        if(numbers==null) {
            return 0;
        }

        int count = 0;
        for (Integer number : numbers) {
            if(number != null){
                if(calculateDigitSum(number) > threshold) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Вспомогательный метод для подсчета суммы цифр числа.
     *
     * @param number Целое число
     * @return Сумма цифр числа
     */
    static int calculateDigitSum(int number) {
        int sumDigits = 0;
        String numberString = String.valueOf(Math.abs(number));
        for (char c : numberString.toCharArray()) {
            int digit = Character.getNumericValue(c);
            sumDigits+=digit;
        }
        return sumDigits;
    }
}
