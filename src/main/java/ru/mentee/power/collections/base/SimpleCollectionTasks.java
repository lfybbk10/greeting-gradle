package ru.mentee.power.collections.base;

import java.util.List;

/**
 * Класс с простыми задачами по работе с коллекциями.
 */
public class SimpleCollectionTasks {

    /**
     * Подсчитывает количество строк в списке, начинающихся с заданной буквы
     * (без учета регистра).
     *
     * @param strings Список строк.
     * @param startChar Начальная буква.
     * @return Количество строк, начинающихся с startChar.
     *         Возвращает 0, если список равен null или пуст.
     */
    public static int countStringsStartingWith(List<String> strings, char startChar) {
        if(strings==null)
            return 0;

        int count = 0;
        for (String string : strings) {
            if (string != null && string.startsWith(String.valueOf(startChar))) {
                count++;
            }
        }
        return count;
    }
}