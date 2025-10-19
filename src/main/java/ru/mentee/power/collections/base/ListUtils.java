package ru.mentee.power.collections.base;

import java.util.List;
import java.util.ArrayList;

public class ListUtils {
    /**
     * Объединяет два списка строк в один, удаляя дубликаты.
     *
     * @param list1 Первый список строк
     * @param list2 Второй список строк
     * @return Новый список, содержащий все уникальные элементы из обоих списков
     */
    public static List<String> mergeLists(List<String> list1, List<String> list2) {
        if(list1 == null && list2 == null) {
            return List.of();
        }

        List<String> result = new ArrayList<>();

        if(list1 != null) {
            result.addAll(list1);
        }
        if(list2 != null) {
            result.removeAll(list2);
            result.addAll(list2);
        }

        result.removeIf((Object o) -> o == null);

        return result;
    }
}