package ru.mentee.power.collections.list;

import java.util.*;

/**
 * Класс для анализа и обработки связных списков (LinkedList)
 */
public class LinkedListAnalyzer {

    /**
     * Объединяет два списка, удаляя дубликаты
     *
     * @param list1 первый список
     * @param list2 второй список
     * @return новый список, содержащий все уникальные элементы из обоих списков
     * @throws IllegalArgumentException если list1 или list2 равны null
     */
    public static <T> List<T> mergeLists(List<T> list1, List<T> list2) {
        if(list1 == null || list2 == null) {
            throw new IllegalArgumentException("Lists cannot be null");
        }

        Set<T> mergedSet = new HashSet<>();
        Iterator<T> iterator1 = list1.iterator();
        Iterator<T> iterator2 = list2.iterator();
        while (iterator1.hasNext() || iterator2.hasNext()) {
            if (iterator1.hasNext()) {
                mergedSet.add(iterator1.next());
            }
            if (iterator2.hasNext()) {
                mergedSet.add(iterator2.next());
            }
        }

        return new ArrayList<>(mergedSet);
    }

    /**
     * Переворачивает список (изменяет порядок элементов на обратный)
     *
     * @param list список для обращения
     * @return тот же список с обратным порядком элементов
     * @throws IllegalArgumentException если list равен null
     */
    public static <T> List<T> reverse(List<T> list) {
        if(list == null) {
            throw new IllegalArgumentException("List cannot be null");
        }

        LinkedList<T> reversed = new LinkedList<>();

        for (T element : list) {
            reversed.addFirst(element);
        }

        list.clear();
        list.addAll(reversed);
        return list;
    }

    /**
     * Удаляет из списка все элементы, которые встречаются более одного раза,
     * оставляя только их первое вхождение
     *
     * @param list список для обработки
     * @return список с удаленными дубликатами
     * @throws IllegalArgumentException если list равен null
     */
    public static <T> List<T> removeDuplicates(List<T> list) {
        if(list == null) {
            throw new IllegalArgumentException("List cannot be null");
        }

        List<T> newList = new LinkedList<>();
        Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()) {
            T element = iterator.next();
            if (!newList.contains(element)) {
                newList.add(element);
            }
        }
        return newList;
    }

    /**
     * Реализует циклический сдвиг элементов списка на указанное количество позиций
     *
     * @param list список для сдвига
     * @param positions количество позиций для сдвига (положительное - вправо, отрицательное - влево)
     * @return тот же список с циклически сдвинутыми элементами
     * @throws IllegalArgumentException если list равен null
     */
    public static <T> List<T> rotate(List<T> list, int positions) {
        if (list == null)
            throw new IllegalArgumentException("list cannot be null");

        int size = list.size();
        if (size == 0 || positions == 0)
            return list;

        int shift = positions % size;
        if (shift < 0)
            shift += size;

        LinkedList<T> linkedList = (LinkedList<T>) list;

        if (shift > 0) {
            for (int i = 0; i < shift; i++) {
                T last = linkedList.removeLast();
                linkedList.addFirst(last);
            }
        }
        return linkedList;
    }
}